package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pvi.jd.gt.personalvirtualinventories.Model.ApiRequestQueue;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeScreen extends AppCompatActivity {

    private Menu menu;
    private String activityName;
    private String recipeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        Bundle recipeBundle = getIntent().getBundleExtra("RECIPE_BUNDLE");
        String recipeTitle = recipeBundle.getString("RECIPE_NAME");
        activityName = recipeBundle.getString("ACTIVITY", "");
        recipeID = recipeBundle.getString("RECIPE_ID", "");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_recipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipeTitle);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<String> recipeIngredients = recipeBundle.getStringArrayList("RECIPE_INGREDIENTS");

        String img_resource = recipeBundle.getString("IMG_SOURCE", "");

        final String recipeDetails = recipeBundle.getString("RECIPE_DETAILS");
        final String recipeInstructions = recipeBundle.getString("RECIPE_INSTRUCTIONS");

        //NetworkImageView imgView = (NetworkImageView) findViewById(R.id.recipe_img);
        ImageView imgView = (ImageView) findViewById(R.id.recipe_img);
        TextView recipeTitleView = (TextView) findViewById(R.id.recipe_title);
        final TextView recipeDetailView = (TextView) findViewById(R.id.recipe_details);
        final TextView recipeIngredientsView = (TextView) findViewById(R.id.recipe_ingredients);
        final TextView recipeInstructionsView = (TextView) findViewById(R.id.recipe_instructions);

        if(img_resource.isEmpty()) {
            imgView.setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            Picasso.get().load(img_resource).placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground).into(imgView);
        }
//        ImageLoader imageLoader = ApiRequestQueue.getInstance(
//                this.getApplicationContext()).getImageLoader();
//        imageLoader.get(img_resource, ImageLoader.getImageListener(imgView,
//                R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground));
        //imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imgView.setImageUrl(img_resource, imageLoader);

        //imgView.setImageResource(img_resource);
        recipeTitleView.setText(recipeTitle);
        recipeTitleView.setSelected(true);
        recipeDetailView.setText(recipeDetails);
        recipeDetailView.setMovementMethod(new ScrollingMovementMethod());

        recipeInstructionsView.setClickable(true);
        recipeInstructionsView.setMovementMethod(LinkMovementMethod.getInstance());
        String link = "<a href='" + recipeInstructions + "'> Instructions </a>";
        recipeInstructionsView.setText(Html.fromHtml(link, 0));
        if(!recipeInstructions.isEmpty()) {
            recipeInstructionsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "";
                    if(!recipeInstructions.startsWith("http://") && !recipeInstructions.startsWith("https://")) {
                        url = "http://" + recipeInstructions;
                    }
                    Uri uri = Uri.parse(url);
                    Log.d("URI", uri.toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
        }
        recipeIngredientsView.setText("");
        for (String s : recipeIngredients) {
            recipeIngredientsView.append("\u2022 " + s);
            recipeIngredientsView.append(System.getProperty("line.separator"));
        }
        recipeIngredientsView.setMovementMethod(new ScrollingMovementMethod());

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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.select_button) {
            if(activityName.equals("QuestionMealSelection")) {
                Intent newIntent = new Intent(this, QuestionMealSelection.class);
                newIntent.putExtra("SELECTED_MEAL_ID", recipeID);
                startActivity(newIntent);
            } else if(activityName.equals("MealSelection")) {
                Intent newIntent = new Intent(this, MealSelection.class);
                newIntent.putExtra("SELECTED_MEAL_ID", recipeID);
                startActivity(newIntent);
            } else if(activityName.equals("MainScreen")) {
                Intent newIntent = new Intent(this, MainScreen.class);
                newIntent.putExtra("SELECTED_MEAL_ID", recipeID);
                startActivity(newIntent);
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
