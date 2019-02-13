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
    private ArrayList<String> tempRecipeId;

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
        tempRecipeId = new ArrayList<>();
        tempRecipeId.add("Simple-Skillet-Green-Beans-2352743");
        tempRecipeId.add("Chocolate-Pots-De-Creme-_Vegan-_-Paleo_-2615006");
        tempRecipeId.add("Arrabbiata-Pasta-1686610");
        tempRecipeId.add("Creamy-Pesto-Pasta-2137433");
        tempRecipeId.add("Greek-Pasta-Salad-2559770");
        tempRecipeId.add("Pasta-caprese-299294");
        tempRecipeId.add("Vegan-Spinach-Pesto-Pasta-2596986");
        tempRecipeId.add("Italian-Grilled-Cheese-1600270");
        tempRecipeId.add("Easy-4-Ingredient-Instant-Pot-Mac-and-Cheese-2623416");
        tempRecipeId.add("Marinated-Cucumbers-and-Red-Onions-2555614");
    }

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
                bundle.putStringArrayList("SELECTED_IDS", tempRecipeId);

                newIntent.putExtra("ID_BUNDLE", bundle);
                startActivity(newIntent);
            }
        });
    }

}
