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
            final ImageView img = (ImageView) gridCell.findViewById(R.id.meal_image);
            TextView recipeTitle = (TextView) gridCell.findViewById(R.id.meal_name);
            //recipeTitle.setText(mealNames[position]);
            recipeTitle.setText(planRecipes.get(position).getRecipeTitle());
            img.setImageResource(this.mealPicId[position]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newIntent = new Intent(mContext,
                            RecipeScreen.class);
                    Bundle recipeBundle = new Bundle();
                    recipeBundle.putString("RECIPE_NAME", planRecipes.get(position).getRecipeTitle());
                    recipeBundle.putStringArrayList("RECIPE_INGREDIENTS", planRecipes.get(position).getIngredients());
                    recipeBundle.putInt("IMG_SOURCE", mealPicId[position]);
                    newIntent.putExtra("RECIPE_BUNDLE", recipeBundle);
//                    newIntent.putExtra("RECIPE_NAME", mealNames[position]);
//                    newIntent.putExtra("RECIPE_INGREDIENTS", mealIngredients[position]);
//                    newIntent.putExtra("IMG_SOURCE", mealPicId[position]);
                    mContext.startActivity(newIntent);
                }
            });
            return gridCell;
        } else {
            return convertView;
        }
    }
}
