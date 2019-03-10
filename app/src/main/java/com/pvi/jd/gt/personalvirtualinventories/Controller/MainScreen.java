package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
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
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.pvi.jd.gt.personalvirtualinventories.Model.Meal;
import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlan;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.MealPlanViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MealPlanViewModel viewModel;
    private MealPlanCell adapter;
    private View mealPlanProgress;


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


        mealPlanProgress = findViewById(R.id.mealplan_progressBar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewModel = ViewModelProviders.of(this).get(MealPlanViewModel.class);
        viewModel.init(this);
        MutableLiveData<MealPlan> mealPlanMutableLiveData = viewModel.getMealPlan();

        // Create the observer which updates the UI.
        final Observer<MealPlan> mealPlanObserver = new Observer<MealPlan>() {
            @Override
            public void onChanged(@Nullable final MealPlan newMP) {
                Log.d("MEAL PLAN OBJECT CHANGED", Arrays.deepToString(newMP.getMealPlan().toArray()));
                //mealPlanMutableLiveData.setValue(newMP);
                for (int i = 0; i < newMP.getMealPlan().size(); i++) {
                    Meal m = newMP.getMealPlan().get(i);
                    if (m.getRecipe() == null) {
                        getSetRecipe(m, mealPlanMutableLiveData);
                    }
                }
                updateUI(mealPlanMutableLiveData);
            }
        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mealPlanMutableLiveData.observe(this, mealPlanObserver);



    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mealPlanProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mealPlanProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mealPlanProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mealPlanProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void getSetRecipe(Meal m, MutableLiveData<MealPlan> mp) {
        MutableLiveData<Recipe> recipeMutableLiveData = viewModel.getRecipe(m.getApiID(), this);
        final Observer<Recipe> recipeObserver = new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                m.setRecipe(recipe);
                updateUI(mp);
                Log.d("MAINSCREEN", "RECIPE UPDATED");
            }
        };
        recipeMutableLiveData.observe(this, recipeObserver);
    }

    private void updateUI(MutableLiveData<MealPlan> mealPlanMutableLiveData) {
        if (mealPlanMutableLiveData.getValue() == null) {
            Button createPlan = (Button) findViewById(R.id.createMealPlanButton);
            if (createPlan != null) {
                ((ViewManager) createPlan.getParent()).removeView(createPlan);
            }
            showProgress(true);
            return;
        }
        if (!mealPlanMutableLiveData.getValue().isExists()) {
            showProgress(false);
            Button createPlan = (Button) findViewById(R.id.createMealPlanButton);
//            ConstraintLayout layout = findViewById(R.id.meal_planning_layout);
//            layout.addView(createPlan, createPlan.getLayoutParams());
//           ((ViewManager) createPlan.getParent()).addView(createPlan, createPlan.getLayoutParams());
            createPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextIntent = new Intent(MainScreen.this,
                            MealSelection.class);
                    startActivity(nextIntent);
                }
            });
        } else {
            Button createPlan = (Button) findViewById(R.id.createMealPlanButton);
            if (createPlan != null) {
                ((ViewManager) createPlan.getParent()).removeView(createPlan);
            }
            if (mealPlanMutableLiveData.getValue().isExists() && mealPlanMutableLiveData.getValue()
                    .getMealPlan().stream().filter(meal -> meal.getRecipe() == null).count() == 0) {
                ListView mealPlanList = new ListView(this);
                ViewGroup layout = (ViewGroup) findViewById(R.id.meal_planning_layout);
                //final MealPlanCell adapter = new MealPlanCell(this, new LinkedList<Recipe>());
//            mealPlanList.setAdapter(adapter);
//            layout.addView(mealPlanList);


                mealPlanMutableLiveData.observe(this, new Observer<MealPlan>() {
                    @Override
                    public void onChanged(@Nullable MealPlan mealPlan) {
                        adapter = new MealPlanCell(MainScreen.this, mealPlan, viewModel);
                        mealPlanList.setAdapter(adapter);
                        layout.addView(mealPlanList);
                        showProgress(false);

                    }
                });
            }
        }
    }

    public void editMode(MenuItem item) {
        boolean editMode = adapter.getEditMode();
        if (!editMode) {
            item.setTitle("Done");
            adapter.setEditMode(true);
        } else {
            item.setTitle("Edit");
            adapter.setEditMode(false);
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
        if (id == R.id.edit_button) {
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
