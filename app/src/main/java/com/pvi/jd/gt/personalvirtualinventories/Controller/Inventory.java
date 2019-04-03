package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ArrayList<String> inventoryItems = new ArrayList<>();
        inventoryItems.add("food 1");
        inventoryItems.add("food 2");
        inventoryItems.add("food 3");

        RecyclerView toolList = (RecyclerView) findViewById(R.id.inventory_list);
        toolList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toolList.setLayoutManager(new LinearLayoutManager(this));
        final InventoryRecycler adapter = new InventoryRecycler(this, inventoryItems);
        toolList.setAdapter(adapter);
    }
}
