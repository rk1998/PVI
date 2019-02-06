package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import com.pvi.jd.gt.personalvirtualinventories.R;

public class MealCell extends BaseAdapter {
    private Context mContext;
    private final String[] mealNames;
    private final String[] mealIngredients;
    private final int[] mealPicId;
    public MealCell(Context c, String[] names, String[] ingredients, int[] imgIDs) {
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
            gridCell = inflater.inflate(R.layout.meal_cell_layout, null);
            final ImageButton img = (ImageButton) gridCell.findViewById(R.id.recipe_img_button);
            TextView recipeTitle = (TextView) gridCell.findViewById(R.id.recipe_select_title);
            recipeTitle.setText(mealNames[position]);
            final FloatingActionButton addButton = (FloatingActionButton) gridCell.findViewById(R.id.fab);
            img.setImageResource(this.mealPicId[position]);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addButton.setSelected(!addButton.isSelected());
                    if(addButton.isSelected()) {
                        addButton.setImageResource(R.drawable.checkbtn);
                    } else {
                        addButton.setImageResource(R.drawable.addbtn);;
                    }
                    //Toast.makeText(mContext, "Pizza Pizza", Toast.LENGTH_SHORT).show();
                }
            });

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newIntent = new Intent(mContext,
                            RecipeScreen.class);
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

