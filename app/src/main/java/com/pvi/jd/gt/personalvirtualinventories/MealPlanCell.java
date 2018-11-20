package com.pvi.jd.gt.personalvirtualinventories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class MealPlanCell extends BaseAdapter {
    private Context mContext;
    private final String[] mealNames;
    private final String[] mealIngredients;
    private final int[] mealPicId;
    public MealPlanCell(Context c, String[] names, String[] ingredients, int[] imgIDs) {
        this.mContext = c;
        this.mealNames = names;
        this.mealPicId = imgIDs;
        this.mealIngredients = ingredients;
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
            gridCell = inflater.inflate(R.layout.meal_plan_layout, null);
            final ImageButton img = (ImageButton) gridCell.findViewById(R.id.meal_recipe_im);
            CheckBox recipeTitle = (CheckBox) gridCell.findViewById(R.id.meal_checkbox);
            recipeTitle.setText(mealNames[position]);
            img.setImageResource(this.mealPicId[position]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newIntent = new Intent(mContext,
                            com.pvi.jd.gt.personalvirtualinventories.RecipeScreen.class);
                    newIntent.putExtra("RECIPE_NAME", mealNames[position]);
                    newIntent.putExtra("RECIPE_INGREDIENTS", mealIngredients[position]);
                    newIntent.putExtra("IMG_SOURCE", mealPicId[position]);
                    mContext.startActivity(newIntent);
                }
            });
            return gridCell;
        } else {
            return convertView;
        }
    }
}
