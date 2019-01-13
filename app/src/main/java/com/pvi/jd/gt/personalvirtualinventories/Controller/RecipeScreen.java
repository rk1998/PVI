package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pvi.jd.gt.personalvirtualinventories.R;

import org.w3c.dom.Text;

public class RecipeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        String recipeTitle = getIntent().getStringExtra("RECIPE_NAME");
        String recipeIngredients = getIntent().getStringExtra("RECIPE_INGREDIENTS");
        int img_resource = getIntent().getIntExtra("IMG_SOURCE", R.drawable.pizza);
        ImageView imgView = (ImageView) findViewById(R.id.recipe_img);
        TextView recipeTitleView = (TextView) findViewById(R.id.recipe_title);
        TextView recipeIngriedentsView = (TextView) findViewById(R.id.recipe_description);
        imgView.setImageResource(img_resource);
        recipeTitleView.setText(recipeTitle);
        recipeIngriedentsView.setText(recipeIngredients);
    }
}
