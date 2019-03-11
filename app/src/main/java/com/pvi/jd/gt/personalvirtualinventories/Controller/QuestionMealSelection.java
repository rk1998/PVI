package com.pvi.jd.gt.personalvirtualinventories.Controller;

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
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.MealSelectionViewModel;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.RecipeSelectionViewModel;

import java.util.LinkedList;
import java.util.List;

public class QuestionMealSelection extends AppCompatActivity {
    private RecipeSelectionViewModel viewModel;
    private MealCell adapter;
    private LiveData<List<Recipe>> recipeDataList;
    private View mProgressView;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_meal_selection);
        GridView mealSelectionGrid = (GridView) findViewById(R.id.question_meal_grid_view);
        mProgressView = findViewById(R.id.recipe_search_progress);

        viewModel = ViewModelProviders.of(this).get(RecipeSelectionViewModel.class);
        viewModel.init(this);
        recipeDataList = viewModel.getSearchedRecipes();
        showProgress(true);
        //adapter = new MealCell(QuestionMealSelection.this, new LinkedList<>());
        //mealSelectionGrid.setAdapter(adapter);
        recipeDataList.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                showProgress(false);
                adapter = new MealCell(QuestionMealSelection.this, recipes);
                mealSelectionGrid.setAdapter(adapter);
//                for(int i = 0; i < recipes.size(); i++) {
//                    adapter.addNewRecipe(recipes.get(i));
//                }
            }
        });

        doneButton = (Button) findViewById(R.id.question_confirm_meal_selection);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Recipe> selectedRecipes = adapter.getSelectedRecipeList();
                if(selectedRecipes.size() < 10) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuestionMealSelection.this);
                    builder.setCancelable(true);
                    builder.setTitle("Not enough recipes");
                    builder.setMessage("Please select at least 10 recipes for your recipe bank.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    MutableLiveData<Integer> id = viewModel.writeUserData(selectedRecipes, QuestionMealSelection.this);
                    showProgress(true);
                    id.observe(QuestionMealSelection.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer userID) {
                            showProgress(false);
                            if (userID != null && viewModel.getCurrentUser().getValue() != null) {
                                viewModel.getCurrentUser().getValue().setId(userID);
                                Intent nextIntent = new Intent(QuestionMealSelection.this, MainScreen.class);
                                startActivity(nextIntent);
                            }
                        }
                    });

                }
            }
        });

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

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
            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }

    public void updateNextButton() {
        List<Recipe> selectedRecipes = adapter.getSelectedRecipeList();
        if (selectedRecipes.size() < 10) {
            doneButton.setText(selectedRecipes.size() + "/10");
            doneButton.setBackground(getDrawable(R.drawable.grayroundedbutton));
        } else {
            doneButton.setText("Finish");
            doneButton.setBackground(getDrawable(R.drawable.redroundedbutton));
        }
    }

}
