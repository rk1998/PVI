package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.graphics.Color;
import android.app.AlertDialog;
import android.content.DialogInterface;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pvi.jd.gt.personalvirtualinventories.Model.ApiRequestQueue;
import com.pvi.jd.gt.personalvirtualinventories.Model.Meal;
import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlan;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.MealPlanViewModel;

import java.util.ArrayList;
import java.util.List;


public class MealPlanCell extends BaseAdapter {
    private Context mContext;
    private final String[] mealNames;
    private final String[] mealIngredients;
    private final int[] mealPicId = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};
    private List<Meal> planMeals;
    private int countSelected = 0;
    private final MealPlanViewModel mpVM;
    private boolean editMode = false;
    //private List<MutableLiveData<Recipe>> planMeals;

    /**
     *
     * @param c current activity context
     * @param mealPlan current meal plan
     */
    public MealPlanCell(Context c, MealPlan mealPlan, MealPlanViewModel mpVM, int numCompleted) {
        this.mContext = c;
        this.mpVM = mpVM;
        this.planMeals = mealPlan.getMealPlan();
        mealNames = new String[0];
        mealIngredients = new String[0];
        countSelected = numCompleted;
    }

    @Override
    public int getCount() {
        return this.planMeals.size();
    }

    @Override
    public Object getItem(int position) {
        if(planMeals != null && position < planMeals.size()) {
            return planMeals.get(position);
        } else {
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View gridCell;
        if(convertView == null && planMeals != null && !planMeals.isEmpty()) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meal_plan_row, parent, false);
        }
        final NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.meal_image);
        final TextView recipeTitle = (TextView) convertView.findViewById(R.id.meal_name);
        recipeTitle.setSelected(true);
        final CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.meal_checkbox);
        Meal meal = (Meal) getItem(position);
        if (meal.isCompleted()) {
            checkbox.setChecked(true);
            img.setColorFilter(Color.argb(175,50,50,50));
        } else {
            checkbox.setChecked(false);
            img.setColorFilter(null);
        }
        Log.d("NUM SELECTED: ", ""+countSelected);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkbox.toggle();
                Log.d("CHECKBOX STATUS", "" + checkbox.isSelected());
                //checkbox.setSelected(!checkbox.isSelected());
                if(checkbox.isChecked()) {
                    img.setColorFilter(Color.argb(175,50,50,50));
                    countSelected++;
                    Log.d("NUM SELECTED: ", ""+countSelected);
                    mpVM.changeMealCompletionStatus(planMeals.get(position).getRecipe(),
                            true, mContext);
                    Meal selectedMeal = planMeals.get(position);
                    planMeals.remove(position);
                    planMeals.add(selectedMeal);
                    notifyDataSetChanged();
                    if (countSelected == planMeals.size()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(true);
                        builder.setTitle("Would you like to create a new meal plan?");
                        builder.setMessage("This will delete your current meal plan.");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent newIntent = new Intent(mContext, MealSelection.class);
                                        mContext.startActivity(newIntent);
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                countSelected--;
                                img.setColorFilter(null);
                                checkbox.setChecked(false);
                                mpVM.changeMealCompletionStatus(planMeals.get(position).getRecipe(),
                                        false, mContext);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                } else {
                    img.setColorFilter(null);
                    countSelected--;
                    Log.d("NUM SELECTED: ", ""+countSelected);
                    mpVM.changeMealCompletionStatus(planMeals.get(position).getRecipe(), false, mContext);
                    Meal selectedMeal = planMeals.get(position);
                    planMeals.remove(position);
                    planMeals.add(planMeals.size() - countSelected, selectedMeal);
                    notifyDataSetChanged();
                }
            }
        });

        final Meal currMeal = (Meal) getItem(position);
        recipeTitle.setText(currMeal.getRecipe().getRecipeTitle());
        checkbox.setSelected(currMeal.isCompleted());

        ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setMessage("Remove " + recipeTitle.getText() + " from your meal plan?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeRecipe(currMeal);
                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        if (!editMode) {
            deleteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
        }

        img.setImageResource(R.color.gray);
        String img_resource = currMeal.getRecipe().getImgURL();
        if(img_resource.isEmpty()) {
            img.setDefaultImageResId(R.drawable.chickensalad);
        } else {
            ImageLoader imageLoader = ApiRequestQueue.getInstance(
                    this.mContext.getApplicationContext()).getImageLoader();
            imageLoader.get(img_resource, ImageLoader.getImageListener(img,
                    R.drawable.spagett, R.drawable.spagett));
            //imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageUrl(img_resource, imageLoader);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(mContext,
                        RecipeScreen.class);
                Bundle recipeBundle = new Bundle();
                recipeBundle.putString("IMG_SOURCE", currMeal.getRecipe().getImgURL());
                recipeBundle.putString("RECIPE_NAME", currMeal.getRecipe().getRecipeTitle());
                recipeBundle.putString("RECIPE_DETAILS", currMeal.getRecipe().getDetails());
                recipeBundle.putStringArrayList("RECIPE_INGREDIENTS", currMeal.getRecipe().getIngredients());
                recipeBundle.putString("RECIPE_INSTRUCTIONS", currMeal.getRecipe().getRecipeSource());
                newIntent.putExtra("RECIPE_BUNDLE", recipeBundle);
                mContext.startActivity(newIntent);
            }
        });
        return convertView;
    }

    /**
     * Sets recipe list of the MealPlanCell adapter
     * @param newMeal list of recipes in the meal plan
     */
    public void addNewRecipe(Meal newMeal) {
        if(!planMeals.contains(newMeal)) {
            planMeals.add(newMeal);
        }
        notifyDataSetChanged();
    }

    public void removeRecipe(Meal meal) {
        if (planMeals.contains(meal)) {
            planMeals.remove(meal);
            mpVM.removeMeal(meal, mContext);
        }
        notifyDataSetChanged();
        if (planMeals.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setCancelable(true);
            builder.setTitle("Your meal plan is empty.");
            builder.setMessage("Would you like to create a new meal plan?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent newIntent = new Intent(mContext, MealSelection.class);
                            mContext.startActivity(newIntent);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public boolean getEditMode(){
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        notifyDataSetChanged();
    }
}
