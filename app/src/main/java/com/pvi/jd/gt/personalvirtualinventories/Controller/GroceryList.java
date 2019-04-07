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

public class GroceryList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private GroceryRecycler adapter;
    private RecyclerView.LayoutManager layoutManager;
    private GroceryListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_grocery);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_grocery);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_grocery);
        navigationView.setNavigationItemSelectedListener(this);


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
                        viewModel.getGroceryListDisplay(ingredientQuantities), ingredientQuantities,
                        viewModel);
                toolList.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_grocery);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_screen, menu);
//        return true;
//    }

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
            Intent intent = new Intent(this,
                    MainScreen.class);
            startActivity(intent);
        } else if (id == R.id.inventory) {
            Intent intent = new Intent(this,
                    Inventory.class);
            startActivity(intent);
        } else if (id == R.id.grocery_list) {
            //
        } else if (id == R.id.settings) {

        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        item.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_grocery);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
