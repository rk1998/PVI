package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Intent;
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

import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.ArrayList;
import java.util.List;

public class GroceryList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroceryRecycler adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);


        ArrayList<String> groceryItems = new ArrayList<>();
        groceryItems.add("16 oz. Mozzarella Cheese");
        groceryItems.add("18 oz. Tomato Sauce");
        groceryItems.add("12 Taco Shells");
        groceryItems.add("Frozen Meatballs");


        RecyclerView toolList = (RecyclerView) findViewById(R.id.grocery_list);
        toolList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toolList.setLayoutManager(new LinearLayoutManager(this));
        final GroceryRecycler adapter = new GroceryRecycler(this, groceryItems);
        toolList.setAdapter(adapter);


    }


}
