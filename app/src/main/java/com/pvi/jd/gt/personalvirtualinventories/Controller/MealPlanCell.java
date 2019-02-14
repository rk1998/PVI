package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pvi.jd.gt.personalvirtualinventories.Model.ApiRequestQueue;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.List;


public class MealPlanCell extends BaseAdapter {
    private Context mContext;
    private final String[] mealNames;
    private final String[] mealIngredients;
    private final int[] mealPicId = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};
    private List<Recipe> planRecipes;
    //private List<MutableLiveData<Recipe>> planRecipes;
    /**
     * Old Constructor for dummy data
     * @param c
     * @param names
     * @param ingredients
     * @param imgIDs
     */
    public MealPlanCell(Context c, String[] names, String[] ingredients, int[] imgIDs) {
        this.mContext = c;
        this.mealNames = names;

        this.mealIngredients = ingredients;
    }

    /**
     *
     * @param c current activity context
     * @param currentRecipes recipes in current meal plan
     */
    public MealPlanCell(Context c, List<Recipe> currentRecipes) {
        this.mContext = c;
        this.planRecipes = currentRecipes;
        mealNames = new String[0];
        mealIngredients = new String[0];

    }

    @Override
    public int getCount() {
        return this.planRecipes.size();
    }

    @Override
    public Object getItem(int position) {
        if(planRecipes != null && position < planRecipes.size()) {
            return planRecipes.get(position);
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
        if(convertView == null && planRecipes != null && !planRecipes.isEmpty()) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meal_plan_row, parent, false);
        }
        final NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.meal_image);
        TextView recipeTitle = (TextView) convertView.findViewById(R.id.meal_name);
        recipeTitle.setSelected(true);
        final CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.meal_checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkbox.setSelected(!checkbox.isSelected());
                if(checkbox.isSelected()) {
                    img.setColorFilter(Color.argb(175,50,50,50));
                } else {
                    img.setColorFilter(null);
                }
                //Toast.makeText(mContext, "Pizza Pizza", Toast.LENGTH_SHORT).show();
            }
        });


        //MutableLiveData<Recipe> currentRecipeData = planRecipes.get(position);
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
        final Recipe currRecipe = (Recipe) getItem(position);
        recipeTitle.setText(currRecipe.getRecipeTitle());

        String img_resource = currRecipe.getImgURL();
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
                recipeBundle.putString("IMG_SOURCE", currRecipe.getImgURL());
                recipeBundle.putString("RECIPE_NAME", currRecipe.getRecipeTitle());
                recipeBundle.putString("RECIPE_DETAILS", currRecipe.getDetails());
                recipeBundle.putStringArrayList("RECIPE_INGREDIENTS", currRecipe.getIngredients());
                recipeBundle.putString("RECIPE_INSTRUCTIONS", currRecipe.getInstructions());
                newIntent.putExtra("RECIPE_BUNDLE", recipeBundle);
                mContext.startActivity(newIntent);
            }
        });
        return convertView;
    }

    /**
     * Sets recipe list of the MealPlanCell adapter
     * @param newRecipe list of recipes in the meal plan
     */
    public void addNewRecipe(Recipe newRecipe) {
        if(!planRecipes.contains(newRecipe)) {
            planRecipes.add(newRecipe);
        }
        notifyDataSetChanged();
    }
}
