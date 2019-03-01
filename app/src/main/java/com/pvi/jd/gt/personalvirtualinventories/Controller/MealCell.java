package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pvi.jd.gt.personalvirtualinventories.Model.ApiRequestQueue;
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
        this.selectionMap = new HashMap<>();
//        for(int i = 0; i < recipeList.size(); i++) {
//            this.selectionMap.put(recipeList.get(i), false);
//        }

    }

    @Override
    public int getCount() {
        return this.recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        if(recipeList != null && !recipeList.isEmpty() && position < recipeList.size()) {
            return recipeList.get(position);
        } else {
            return  null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //View gridCell;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meal_cell_layout, parent, false);
        }
        final NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.recipe_img_button);
        final TextView recipeTitle = (TextView) convertView.findViewById(R.id.recipe_select_title);
        recipeTitle.setSelected(true);
        //recipeTitle.setText(mealNames[position]);
        final Recipe recipe = (Recipe) getItem(position);
        recipeTitle.setText(recipe.getRecipeTitle());
        final FloatingActionButton addButton = (FloatingActionButton) convertView.findViewById(R.id.fab);

        //Todo: get image from url contained in Recipe object and set it

        String imgUrl = recipe.getImgURL();
        if(imgUrl.isEmpty()) {
            img.setImageResource(R.drawable.spagett);
        } else {
            ImageLoader imageLoader = ApiRequestQueue.getInstance(
                    this.mContext.getApplicationContext()).getImageLoader();
            imageLoader.get(imgUrl, ImageLoader.getImageListener(img,
                    R.drawable.spagett, R.drawable.spagett));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageUrl(imgUrl, imageLoader);
//                MutableLiveData<Bitmap> imageLiveData = getRecipeImage(recipeList.get(position).getImgURL());
//                imageLiveData.observeForever(new Observer<Bitmap>() {
//                    @Override
//                    public void onChanged(@Nullable Bitmap bitmap) {
//                        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
//
//                        img.setAdjustViewBounds(true);
//                        img.setImageBitmap(bitmap);
//                    }
//                });
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButton.setSelected(!addButton.isSelected());
                if(addButton.isSelected()) {
                    addButton.setImageResource(R.drawable.checkbtn);
                    selectionMap.put(recipe, new Boolean(true ));
                } else {
                    addButton.setImageResource(R.drawable.addbtn);
                    selectionMap.put(recipe, new Boolean(false));

                }
                //Toast.makeText(mContext, "Pizza Pizza", Toast.LENGTH_SHORT).show();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(mContext,
                        RecipeScreen.class);
                Bundle recipeBundle = new Bundle();

                recipeBundle.putString("IMG_SOURCE", recipe.getImgURL());
                recipeBundle.putString("RECIPE_NAME", recipe.getRecipeTitle());
                recipeBundle.putString("RECIPE_DETAILS", recipe.getDetails());
                recipeBundle.putStringArrayList("RECIPE_INGREDIENTS", recipe.getIngredients());
                recipeBundle.putString("RECIPE_INSTRUCTIONS", recipe.getRecipeSource());
                newIntent.putExtra("RECIPE_BUNDLE", recipeBundle);

                mContext.startActivity(newIntent);
            }
        });

        return convertView;

    }

    /**
     * @return list of meals selected for the meal plan
     */
    public ArrayList<String> getSelectedMeals() {
        ArrayList<String> selectedMeals = new ArrayList<>();
        for(int i = 0; i < recipeList.size(); i++) {
            if(selectionMap.containsKey(recipeList.get(i))
                    && selectionMap.get(recipeList.get(i)).booleanValue()) {
                selectedMeals.add(recipeList.get(i).getApiID());
            }
        }
        return selectedMeals;
    }

    /**
     * Sets recipe list of the MealPlanCell adapter
     * @param newRecipe list of recipes in the meal plan
     */
    public void addNewRecipe(Recipe newRecipe) {
        if(!recipeList.contains(newRecipe)) {
            recipeList.add(newRecipe);
        }
        notifyDataSetChanged();
    }


}

