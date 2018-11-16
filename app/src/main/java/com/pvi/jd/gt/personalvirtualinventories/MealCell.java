package com.pvi.jd.gt.personalvirtualinventories;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
            Button detailsButton = (Button) gridCell.findViewById(R.id.details_button);
            img.setImageResource(this.mealPicId[position]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    img.setSelected(!img.isSelected());
                    if(img.isSelected()) {
                        img.setColorFilter(Color.argb(130, 139, 202, 239));
                    } else {
                        img.setColorFilter(null);
                    }
                    //Toast.makeText(mContext, "Pizza Pizza", Toast.LENGTH_SHORT).show();
                }
            });

            detailsButton.setOnClickListener(new View.OnClickListener() {
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

