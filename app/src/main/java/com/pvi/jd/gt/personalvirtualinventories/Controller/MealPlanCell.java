package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        return this.mealPicId.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridCell;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            gridCell = inflater.inflate(R.layout.meal_plan_row, null);
            final NetworkImageView img = (NetworkImageView) gridCell.findViewById(R.id.meal_image);
            TextView recipeTitle = (TextView) gridCell.findViewById(R.id.meal_name);
            final CheckBox checkbox = (CheckBox) gridCell.findViewById(R.id.meal_checkbox);
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
            //recipeTitle.setText(mealNames[position]);
            recipeTitle.setText(planRecipes.get(position).getRecipeTitle());

            String img_resource = planRecipes.get(position).getImgURL();
            if(img_resource.isEmpty()) {
                img.setDefaultImageResId(mealPicId[position]);
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
                    recipeBundle.putString("IMG_SOURCE", planRecipes.get(position).getImgURL());
                    recipeBundle.putString("RECIPE_NAME", planRecipes.get(position).getRecipeTitle());
                    recipeBundle.putString("RECIPE_DETAILS", planRecipes.get(position).getDetails());
                    recipeBundle.putStringArrayList("RECIPE_INGREDIENTS", planRecipes.get(position).getIngredients());
                    recipeBundle.putString("RECIPE_INSTRUCTIONS", planRecipes.get(position).getInstructions());
                    newIntent.putExtra("RECIPE_BUNDLE", recipeBundle);
                    mContext.startActivity(newIntent);
                }
            });
            return gridCell;
        } else {
            return convertView;
        }
    }
}
