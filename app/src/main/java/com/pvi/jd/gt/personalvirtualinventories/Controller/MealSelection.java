package com.pvi.jd.gt.personalvirtualinventories.Controller;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MealSelection extends AppCompatActivity {

    //Dummy recipe data
    private String[] names = {"Spaghetti", "Pizza", "Tacos", "Chicken Salad"};
    private String[] ingredients = {"Spaghetti, Tomato Sauce, Bell Peppers",
            "Wheat, Cheese, Pizza Sauce", "Taco Shells, Beef, Cheese", "Chicken Breast, Lettuce, Tomatoes"};
    private int[] imgIds = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};

    private List<Recipe> dummyRecipes = new LinkedList<>();

    private void createDummyRecipes() {
        Recipe spaghetti = new Recipe("Spaghetti", 10, 20, "Make spaghetti", new ArrayList<>(Arrays.asList(ingredients[0].split(", "))));
        Recipe pizza = new Recipe("Pizza", 20, 40, "Make pizza", new ArrayList<>(Arrays.asList(ingredients[1].split(", "))));
        Recipe tacos = new Recipe("Tacos", 10, 20, "TACO NIGHT", new ArrayList<>(Arrays.asList(ingredients[2].split(", "))));
        Recipe chickenSalad = new Recipe("Chicken Salad", 5, 20, "Put the salad together", new ArrayList<>(Arrays.asList(ingredients[3].split(", "))));
        dummyRecipes.add(spaghetti);
        dummyRecipes.add(pizza);
        dummyRecipes.add(tacos);
        dummyRecipes.add(chickenSalad);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createDummyRecipes();
        final GridView mealSelectionGrid = (GridView) findViewById(R.id.meal_grid_view);

        //Todo: get list of recipes from MealSelectionViewModel
        final MealCell selectionAdapter = new MealCell(this, dummyRecipes);
        mealSelectionGrid.setAdapter(selectionAdapter);
        Button doneButton = (Button) findViewById(R.id.confirm_meal_selection);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selectedRecipes = selectionAdapter.getSelectedMeals();
                Intent newIntent = new Intent(MealSelection.this,
                        MainScreen.class);
                newIntent.putExtra("MEAL_PLAN_CREATED", true);

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("SELECTED_IDS", selectedRecipes);

                newIntent.putExtra("ID_BUNDLE", bundle);
                startActivity(newIntent);
            }
        });
    }

}
