package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.GroceryListViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroceryList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroceryRecycler adapter;
    private RecyclerView.LayoutManager layoutManager;
    private GroceryListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);


        ArrayList<String> groceryItems = new ArrayList<>();
        groceryItems.add("16 oz. Mozzarella Cheese");
        groceryItems.add("18 oz. Tomato Sauce");
        groceryItems.add("12 Taco Shells");
        groceryItems.add("Frozen Meatballs");

        viewModel = ViewModelProviders.of(this).get(GroceryListViewModel.class);
        viewModel.init(this);
        MutableLiveData<ArrayList<IngredientQuantity>> mld = viewModel.getCurrentGroceryList();
        RecyclerView toolList = (RecyclerView) findViewById(R.id.grocery_list);
        toolList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toolList.setLayoutManager(new LinearLayoutManager(this));

        mld.observe(this, new Observer<ArrayList<IngredientQuantity>>() {
            @Override
            public void onChanged(@Nullable ArrayList<IngredientQuantity> ingredientQuantities) {
                final GroceryRecycler adapter = new GroceryRecycler(GroceryList.this,
                        viewModel.getGroceryListDisplay(ingredientQuantities));
                toolList.setAdapter(adapter);
            }
        });




    }


}
