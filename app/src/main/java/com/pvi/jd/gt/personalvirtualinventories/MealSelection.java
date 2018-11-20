package com.pvi.jd.gt.personalvirtualinventories;

import com.pvi.jd.gt.personalvirtualinventories.MealCell;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class MealSelection extends AppCompatActivity {
    private String[] names = {"Spaghetti", "Pizza", "Tacos", "Chicken Salad"};
    private String[] ingredients = {"Spaghetti, Tomato Sauce, Bell Peppers",
            "Wheat, Cheese, Pizza Sauce", "Taco Shells, Beef, Cheese", "Chicken Breast, Lettuce, Tomatoes"};
    private int[] imgIds = {R.drawable.spagett, R.drawable.pizza, R.drawable.tacos, R.drawable.chickensalad};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView mealSelectionGrid = (GridView) findViewById(R.id.meal_grid_view);
        mealSelectionGrid.setAdapter(new MealCell(this, names, ingredients,  imgIds));
        Button doneButton = (Button) findViewById(R.id.confirm_meal_selection);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: this
                Intent newIntent = new Intent(MealSelection.this,
                        com.pvi.jd.gt.personalvirtualinventories.MainScreen.class);
                newIntent.putExtra("MEAL_PLAN_CREATED", true);
                startActivity(newIntent);
            }
        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
