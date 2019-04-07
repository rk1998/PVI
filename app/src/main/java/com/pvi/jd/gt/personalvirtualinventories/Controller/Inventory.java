package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.InventoryViewModel;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView inventoryList;
    private InventoryRecycler adapter;
    private InventoryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_inventory);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("INVENTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_inventory);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_inventory);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("INVENTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<String> inventoryItems = new ArrayList<>();
        inventoryItems.add("food 1");
        inventoryItems.add("food 2");
        inventoryItems.add("food 3");

        viewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);
        viewModel.init(this);
        MutableLiveData<ArrayList<IngredientQuantity>> inventory = viewModel.getCurrentInventory();

        inventory.observe(this, new Observer<ArrayList<IngredientQuantity>>() {
            @Override
            public void onChanged(@Nullable ArrayList<IngredientQuantity> ingredientQuantities) {
                ArrayList<String> displayList = viewModel.getInventoryDisplay(ingredientQuantities);
                inventoryList = (RecyclerView) findViewById(R.id.inventory_list);
                inventoryList.addItemDecoration(new DividerItemDecoration(Inventory.this,
                        DividerItemDecoration.VERTICAL));
                inventoryList.setLayoutManager(new LinearLayoutManager(Inventory.this));
                adapter = new InventoryRecycler(Inventory.this,
                        displayList);

                inventoryList.setAdapter(adapter);
            }
        });

        ImageButton addItem = (ImageButton) findViewById(R.id.add_inventory);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(Inventory.this,
                        AddItem.class);
                startActivity(nextIntent);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(pos);
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(inventoryList);
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
            //
        } else if (id == R.id.grocery_list) {
            Intent intent = new Intent(this,
                    GroceryList.class);
            startActivity(intent);
        } else if (id == R.id.settings) {

        }

        item.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_inventory);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
