package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.ArrayList;

public class GroceryList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        recyclerView = (RecyclerView) findViewById(R.id.grocery_list);
        recyclerView.setHasFixedSize(true);

        // data to populate the RecyclerView with
        ArrayList<String> groceryItems = new ArrayList<>();
        groceryItems.add("16 oz. Mozzarella Cheese");
        groceryItems.add("18 oz. Tomato Sauce");
        groceryItems.add("12 Taco Shells");
        groceryItems.add("Frozen Meatballs");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.grocery_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, groceryItems);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
