package com.pvi.jd.gt.personalvirtualinventories.Controller;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.MealPlanViewModel;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.MealSelectionViewModel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MealSelection extends AppCompatActivity {
    private MealSelectionViewModel viewModel;

    //Dummy recipe data
    private String[] names = {"Spaghetti", "Pizza", "Tacos", "Chicken Salad"};
    private String[] ingredients = {"Spaghetti, Tomato Sauce, Bell Peppers",
            "Wheat, Cheese, Pizza Sauce", "Taco Shells, Beef, Cheese", "Chicken Breast, Lettuce, Tomatoes"};
    private int[] imgIds = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};

    private List<Recipe> dummyRecipes = new LinkedList<>();
    private ArrayList<String> tempRecipeId;
    private MealCell adapter;
    private LiveData<List<Recipe>> recipeDataList;
    private View mProgressView;
    private String selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select your meals!");
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(this.getIntent().hasExtra("SELECTED_MEAL_ID")) {
            selectedID = this.getIntent().getStringExtra("SELECTED_MEAL_ID");
        } else {
            selectedID = "";
        }
        //createDummyRecipes();
        GridView mealSelectionGrid = (GridView) findViewById(R.id.meal_grid_view);
        mProgressView = findViewById(R.id.meal_loading_bar);
        showProgress(true);
        viewModel = ViewModelProviders.of(this).get(MealSelectionViewModel.class);
        viewModel.init(this);

        recipeDataList = viewModel.getUserRecipes();
        recipeDataList.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                showProgress(false);
                adapter = new MealCell(MealSelection.this, recipes);
                adapter.setActivityInUse("MealSelection");
                adapter.setSelectedID(selectedID);
                mealSelectionGrid.setAdapter(adapter);
            }
        });

        Button doneButton = (Button) findViewById(R.id.confirm_meal_selection);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selectedRecipeIDs = adapter.getSelectedMeals();
                if(selectedRecipeIDs.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MealSelection.this);
                    builder.setCancelable(false);
                    builder.setTitle("No Meals Selected");
                    builder.setMessage("Please choose at least one meal for your new meal plan.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    List<Recipe> selectedRecipes = recipeDataList.getValue().stream()
                            .filter(recipe -> selectedRecipeIDs.contains(recipe.getApiID()))
                            .collect(Collectors.toList());
                    if(MealSelection.this.getIntent().hasExtra("EDIT_MODE")) {
                        boolean editMode = MealSelection.this.getIntent().getBooleanExtra("EDIT_MODE", false);
                        if(editMode) {
                            viewModel.editCurrentMealPlan(selectedRecipes, getApplicationContext());
                            Intent newIntent = new Intent(MealSelection.this,
                                    MainScreen.class);
                            startActivity(newIntent);
                        } else {
                            viewModel.createMealPlan(selectedRecipes, getApplicationContext());
                            Intent newIntent = new Intent(MealSelection.this,
                                    MainScreen.class);
                            startActivity(newIntent);
                        }
                    } else {
                        viewModel.createMealPlan(selectedRecipes, getApplicationContext());
                        Intent newIntent = new Intent(MealSelection.this,
                                MainScreen.class);
                        startActivity(newIntent);
                    }
                }

//                viewModel.createMealPlan(selectedRecipes, getApplicationContext());
//                Intent newIntent = new Intent(MealSelection.this,
//                        MainScreen.class);
//                newIntent.putExtra("MEAL_PLAN_CREATED", true);
//
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("SELECTED_IDS", selectedRecipeIDs);
//
//                newIntent.putExtra("ID_BUNDLE", bundle);
                //startActivity(newIntent);
            }
        });
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

}
