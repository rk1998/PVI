package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pvi.jd.gt.personalvirtualinventories.Model.ApiRequestQueue;
import com.pvi.jd.gt.personalvirtualinventories.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);

        // retrieve information from recipe bundle
        Bundle recipeBundle = getIntent().getBundleExtra("RECIPE_BUNDLE");
        String recipeTitle = recipeBundle.getString("RECIPE_NAME");

        ArrayList<String> recipeIngredients = recipeBundle.getStringArrayList("RECIPE_INGREDIENTS");

        String img_resource = recipeBundle.getString("IMG_SOURCE");

        final String recipeDetails = recipeBundle.getString("RECIPE_DETAILS");
        final String recipeInstructions = recipeBundle.getString("RECIPE_INSTRUCTIONS");

        NetworkImageView imgView = (NetworkImageView) findViewById(R.id.recipe_img);
        TextView recipeTitleView = (TextView) findViewById(R.id.recipe_title);
        final TextView recipeDetailView = (TextView) findViewById(R.id.recipe_details);
        final TextView recipeIngredientsView = (TextView) findViewById(R.id.recipe_ingredients);
        final TextView recipeInstructionsView = (TextView) findViewById(R.id.recipe_instructions);


        ImageLoader imageLoader = ApiRequestQueue.getInstance(
                this.getApplicationContext()).getImageLoader();
        imageLoader.get(img_resource, ImageLoader.getImageListener(imgView,
                R.drawable.spagett, R.drawable.spagett));
        //imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgView.setImageUrl(img_resource, imageLoader);

        //imgView.setImageResource(img_resource);
        recipeTitleView.setText(recipeTitle);
        recipeDetailView.setText(recipeDetails);
        recipeInstructionsView.setText(recipeInstructions);
        recipeIngredientsView.setText("");
        for (String s : recipeIngredients) {
            recipeIngredientsView.append("\u2022 " + s);
            recipeIngredientsView.append(System.getProperty("line.separator"));
        }

        // tab functionality
        TabLayout tabs = (TabLayout) findViewById(R.id.tab_layout);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    recipeDetailView.setVisibility(View.VISIBLE);
                    recipeIngredientsView.setVisibility(View.INVISIBLE);
                    recipeInstructionsView.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    recipeDetailView.setVisibility(View.INVISIBLE);
                    recipeIngredientsView.setVisibility(View.VISIBLE);
                    recipeInstructionsView.setVisibility(View.INVISIBLE);
                } else {
                    recipeDetailView.setVisibility(View.INVISIBLE);
                    recipeIngredientsView.setVisibility(View.INVISIBLE);
                    recipeInstructionsView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayout.Tab defaultTab = tabs.getTabAt(0);
        defaultTab.select();
    }
}
