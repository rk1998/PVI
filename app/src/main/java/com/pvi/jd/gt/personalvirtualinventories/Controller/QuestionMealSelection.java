package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.MealSelectionViewModel;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.RecipeSelectionViewModel;

import java.util.List;

public class QuestionMealSelection extends AppCompatActivity {
    private RecipeSelectionViewModel viewModel;
    private MealCell adapter;
    private LiveData<List<Recipe>> recipeDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_meal_selection);
        GridView mealSelectionGrid = (GridView) findViewById(R.id.question_meal_grid_view);

        /*viewModel = ViewModelProviders.of(this).get(MealSelectionViewModel.class);
        viewModel.init(this);

        recipeDataList = viewModel.getUserRecipes();
        recipeDataList.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                adapter = new MealCell(QuestionMealSelection.this, recipes);
                mealSelectionGrid.setAdapter(adapter);
            }
        });*/



        //this is for when we're ready to write the user data and move to main screen
        /*MutableLiveData<Integer> id = viewModel.writeUserData(user, getApplicationContext());
        id.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer userID) {
                if (userID != null && viewModel.getCurrentUser().getValue() != null) {
                    viewModel.getCurrentUser().getValue().setId(userID);
                    //TODO MOVE TO MEAL PLAN MAIN SCREEN
                }


            }
        });*/
    }


}
