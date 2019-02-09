package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MealCell extends BaseAdapter {
    private Context mContext;
    private final String[] mealNames;
    private final String[] mealIngredients;
    //dummy recipe images
    private final int[] mealPicId = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};

    private final List<Recipe> recipeList;
    private HashMap<Recipe, Boolean> selectionMap;


    /**
     * Old constructor, used for dummy data
     * @param c
     * @param names
     * @param ingredients
     * @param imgIDs
     * @param recipes
     */
    public MealCell(Context c, String[] names, String[] ingredients, int[] imgIDs, List<Recipe> recipes) {
        this.mContext = c;
        this.mealNames = names;
        //this.mealPicId = imgIDs;
        this.mealIngredients = ingredients;
        this.recipeList = recipes;
        this.selectionMap = new HashMap<>();
        for(int i = 0; i < recipeList.size(); i++) {
            this.selectionMap.put(recipeList.get(i), false);
        }
    }

    /**
     *
     * @param c current activity context
     * @param recipes list of recipes to put into adapter
     */
    public MealCell(Context c, List<Recipe> recipes) {
        this.mContext = c;
        this.recipeList = recipes;
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
            gridCell = inflater.inflate(R.layout.meal_cell_layout, null);
            final ImageButton img = (ImageButton) gridCell.findViewById(R.id.recipe_img_button);
            final TextView recipeTitle = (TextView) gridCell.findViewById(R.id.recipe_select_title);
            //recipeTitle.setText(mealNames[position]);
            recipeTitle.setText(recipeList.get(position).getRecipeTitle());
            final FloatingActionButton addButton = (FloatingActionButton) gridCell.findViewById(R.id.fab);

            //Todo: get image from url contained in Recipe object and set it
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
            if(addButton.isSelected()) {
                selectionMap.put(recipeList.get(position), true);
            }
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newIntent = new Intent(mContext,
                            RecipeScreen.class);
                    Bundle recipeBundle = new Bundle();

                    recipeBundle.putString("RECIPE_NAME", recipeList.get(position).getRecipeTitle());
                    recipeBundle.putStringArrayList("RECIPE_INGREDIENTS", recipeList.get(position).getIngredients());
                    recipeBundle.putInt("IMG_SOURCE", mealPicId[position]);
                    newIntent.putExtra("RECIPE_BUNDLE", recipeBundle);

                    mContext.startActivity(newIntent);
                }
            });

            return gridCell;
        } else {
            return convertView;
        }
    }

    /**
     * @return list of meals selected for the meal plan
     */
    public ArrayList<String> getSelectedMeals() {
        ArrayList<String> selectedMeals = new ArrayList<>();
        for(int i = 0; i < recipeList.size(); i++) {
            if(selectionMap.get(recipeList.get(i))) {
                selectedMeals.add(recipeList.get(i).getApiID());
            }
        }
        return selectedMeals;
    }
}

