package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.InventoryViewModel;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {

    private InventoryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        getSupportActionBar().setTitle("INVENTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> inventoryItems = new ArrayList<>();
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
                RecyclerView toolList = (RecyclerView) findViewById(R.id.inventory_list);
                toolList.addItemDecoration(new DividerItemDecoration(Inventory.this,
                        DividerItemDecoration.VERTICAL));
                toolList.setLayoutManager(new LinearLayoutManager(Inventory.this));
                final InventoryRecycler adapter = new InventoryRecycler(Inventory.this,
                        displayList);
                toolList.setAdapter(adapter);
            }
        });


    }
}
