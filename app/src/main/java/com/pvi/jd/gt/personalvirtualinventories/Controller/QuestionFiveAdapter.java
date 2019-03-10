package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pvi.jd.gt.personalvirtualinventories.Model.ApiRequestQueue;
import com.pvi.jd.gt.personalvirtualinventories.Model.Meal;
import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionFiveAdapter extends BaseAdapter {
    private final Context mContext;
    private final String[] mustHaveMeals = {"Tacos", "Pizza", "Pasta", "Chinese", "Burgers", "Salads"};
    private final String[] mealPicURLs = {"https://pinchofyum.com/wp-content/uploads/Chili-Lime-Fish-Tacos-Recipe.jpg",
            "https://www.tasteofhome.com/wp-content/uploads/2017/10/Chicken-Pizza_exps30800_FM143298B03_11_8bC_RMS-2.jpg",
            "https://static01.nyt.com/images/2016/08/15/dining/15COOKING-PASTA/15COOKING-PASTA-threeByTwoMediumAt2X-v2.jpg",
            "https://hips.hearstapps.com/del.h-cdn.co/assets/17/23/1496940098-shrimp-veggie-lo-mein-1sm.jpg",
            "https://www.tasteofhome.com/wp-content/uploads/2017/10/exps28800_UG143377D12_18_1b_RMS-696x696.jpg",
            "https://www.tablefortwoblog.com/wp-content/uploads/2018/05/quick-chopped-salad-recipe-photos-tablefortwoblog-3.jpg"};
    private List<String> selected = new ArrayList<>();

    public QuestionFiveAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mustHaveMeals.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mustHaveMeals[position];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null && mustHaveMeals != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meal_cell_layout, parent, false);
        }
        final NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.recipe_img_button);
        final TextView recipeTitle = (TextView) convertView.findViewById(R.id.recipe_select_title);
        recipeTitle.setSelected(true);
        final FloatingActionButton fab = (FloatingActionButton) convertView.findViewById(R.id.fab);
        ImageLoader imageLoader = ApiRequestQueue.getInstance(
                this.mContext.getApplicationContext()).getImageLoader();
        img.setImageUrl(mealPicURLs[position], imageLoader);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        recipeTitle.setText(mustHaveMeals[position]);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setSelected(!fab.isSelected());
                if(fab.isSelected()) {
                    fab.setImageResource(R.drawable.checkbtn);
                    selected.add(mustHaveMeals[position]);
                } else {
                    fab.setImageResource(R.drawable.addbtn);
                    selected.remove(mustHaveMeals[position]);
                }
            }
        });
        return convertView;
    }

    public List<String> getSelectedData() {
        return selected;
    }
}
