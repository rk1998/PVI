package com.pvi.jd.gt.personalvirtualinventories;

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

public class MealCell extends BaseAdapter {
    private Context mContext;
    private final String[] mealNames;
    private final int[] mealPicId;
    public MealCell(Context c, String[] names, int[] imgIDs) {
        this.mContext = c;
        this.mealNames = names;
        this.mealPicId = imgIDs;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridCell;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            gridCell = inflater.inflate(R.layout.meal_cell_layout, null);
            ImageButton img = (ImageButton) gridCell.findViewById(R.id.recipe_img_button);
            Button detailsButton = (Button) gridCell.findViewById(R.id.details_button);
            img.setImageResource(this.mealPicId[position]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Pizza Pizza", 10);
                }
            });

            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Its Pizza", 10);
                }
            });
            return gridCell;
        } else {
            return convertView;
        }
    }
}

