package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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

import java.util.List;


public class MealPlanCell extends BaseAdapter {
    private Context mContext;
    private final String[] mealNames;
    private final String[] mealIngredients;
    private final int[] mealPicId = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};
    private List<Meal> planMeals;
    private int countSelected = 0;
    private final MealPlanViewModel mpVM;
    //private List<MutableLiveData<Recipe>> planMeals;

    /**
     *
     * @param c current activity context
     * @param mealPlan current meal plan
     */
    public MealPlanCell(Context c, MealPlan mealPlan, MealPlanViewModel mpVM) {
        this.mContext = c;
        this.mpVM = mpVM;
        this.planMeals = mealPlan.getMealPlan();
        mealNames = new String[0];
        mealIngredients = new String[0];

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
        TextView recipeTitle = (TextView) convertView.findViewById(R.id.meal_name);
        recipeTitle.setSelected(true);
        final CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.meal_checkbox);
        if (planMeals.get(position).isCompleted()) {
            checkbox.setSelected(true);
        } else {
            checkbox.setSelected(false);
        }
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkbox.setSelected(!checkbox.isSelected());
                if(checkbox.isSelected()) {
                    img.setColorFilter(Color.argb(175,50,50,50));
                    countSelected++;
                    mpVM.changeMealCompletionStatus(planMeals.get(position).getRecipe(), true, mContext);
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
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                } else {
                    img.setColorFilter(null);
                    countSelected--;
                    mpVM.changeMealCompletionStatus(planMeals.get(position).getRecipe(), false, mContext);
                }
                //Toast.makeText(mContext, "Pizza Pizza", Toast.LENGTH_SHORT).show();
            }
        });



        //MutableLiveData<Recipe> currentRecipeData = planMeals.get(position);
//            currentRecipeData.observeForever( new Observer<Recipe>() {
//                @Override
//                public void onChanged(@Nullable Recipe recipe) {
//                    recipeTitle.setText(recipe.getRecipeTitle());
//                    String img_resource = recipe.getImgURL();
//                    if(img_resource.isEmpty()) {
//                        img.setDefaultImageResId(R.drawable.chickensalad);
//                    } else {
//                        ImageLoader imageLoader = ApiRequestQueue.getInstance(
//                                mContext.getApplicationContext()).getImageLoader();
//                        imageLoader.get(img_resource, ImageLoader.getImageListener(img,
//                                R.drawable.spagett, R.drawable.spagett));
//                        //imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        img.setImageUrl(img_resource, imageLoader);
//                        img.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent newIntent = new Intent(mContext,
//                                        RecipeScreen.class);
//                                Bundle recipeBundle = new Bundle();
//                                recipeBundle.putString("IMG_SOURCE", recipe.getImgURL());
//                                recipeBundle.putString("RECIPE_NAME", recipe.getRecipeTitle());
//                                recipeBundle.putString("RECIPE_DETAILS", recipe.getDetails());
//                                recipeBundle.putStringArrayList("RECIPE_INGREDIENTS", recipe.getIngredients());
//                                recipeBundle.putString("RECIPE_INSTRUCTIONS", recipe.getInstructions());
//                                newIntent.putExtra("RECIPE_BUNDLE", recipeBundle);
//                                mContext.startActivity(newIntent);
//                            }
//                        });
//                    }
//                }
//            });
        final Meal currMeal = (Meal) getItem(position);
        recipeTitle.setText(currMeal.getRecipe().getRecipeTitle());
        checkbox.setSelected(currMeal.isCompleted());

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
}
