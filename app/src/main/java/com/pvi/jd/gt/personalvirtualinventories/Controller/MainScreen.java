package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ListView;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.MealPlanViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String[] names = {"Spaghetti", "Pizza", "Tacos", "Chicken Salad"};
    private String[] ingredients = {"Spaghetti, Tomato Sauce, Bell Peppers",
            "Wheat, Cheese, Pizza Sauce", "Taco Shells, Beef, Cheese", "Chicken Breast, Lettuce, Tomatoes"};
    private int[] imgIds = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};

    private List<Recipe> dummyRecipes = new LinkedList<>();
    private MealPlanViewModel viewModel;

    private void createDummyRecipes() {
        Recipe spaghetti = new Recipe("Spaghetti", 10, 20, "Make spaghetti", new ArrayList<>(Arrays.asList(ingredients[0].split(", "))));
        spaghetti.setImgURL("http://i2.yummly.com/Hot-Turkey-Salad-Sandwiches-Allrecipes.l.png");
        Recipe pizza = new Recipe("Pizza", 20, 40, "Make pizza", new ArrayList<>(Arrays.asList(ingredients[1].split(", "))));
        pizza.setImgURL("https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg");
        Recipe tacos = new Recipe("Tacos", 10, 20, "TACO NIGHT", new ArrayList<>(Arrays.asList(ingredients[2].split(", "))));
        tacos.setImgURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwwwDZTip_gvqPmL75Wt1yasSnozKSRNBDyJAm5OgZvNl8tN7W");
        Recipe chickenSalad = new Recipe("Chicken Salad", 5, 20, "Put the salad together", new ArrayList<>(Arrays.asList(ingredients[3].split(", "))));
        chickenSalad.setImgURL("https://www.tasteofhome.com/wp-content/uploads/2017/10/Berry-Chicken-Salad_exps47579_CW143042B02_27_1b_RMS-696x696.jpg");
        dummyRecipes.add(spaghetti);
        dummyRecipes.add(pizza);
        dummyRecipes.add(tacos);
        dummyRecipes.add(chickenSalad);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(getIntent().hasExtra("MEAL_PLAN_CREATED")) {
            Button createPlan = (Button) findViewById(R.id.createMealPlanButton);
            ((ViewManager) createPlan.getParent()).removeView(createPlan);

            viewModel = ViewModelProviders.of(this).get(MealPlanViewModel.class);
            Bundle selectionBundle = getIntent().getBundleExtra("ID_BUNDLE");
            List<String> selectedIDs = selectionBundle.getStringArrayList("SELECTED_IDS");
            viewModel.init(selectedIDs, this);

            ListView mealPlanList = new ListView(this);
            ViewGroup layout = (ViewGroup) findViewById(R.id.meal_planning_layout);
            final MealPlanCell adapter = new MealPlanCell(this, new LinkedList<Recipe>());
            mealPlanList.setAdapter(adapter);
            layout.addView(mealPlanList);

            List<MutableLiveData<Recipe>> recipeLiveDataList = viewModel.getMealPlanRecipes();
            for(MutableLiveData<Recipe> rep : recipeLiveDataList) {
                rep.observe(this, new Observer<Recipe>() {
                    @Override
                    public void onChanged(@Nullable Recipe recipe) {
                        adapter.addNewRecipe(recipe);
                    }
                });
            }

        } else {
            Button createPlan = (Button) findViewById(R.id.createMealPlanButton);
            createPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextIntent = new Intent(MainScreen.this,
                            MealSelection.class);
                    startActivity(nextIntent);
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.meal_planning) {
            // Handle the camera action
        } else if (id == R.id.inventory) {

        } else if (id == R.id.grocery_list) {
            Intent intent = new Intent(this,
                    GroceryList.class);
            startActivity(intent);
        } else if (id == R.id.settings) {

        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        item.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
