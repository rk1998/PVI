package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.QuestionnaireViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.pvi.jd.gt.personalvirtualinventories.R;

public class Meats extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionnaireViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meats);


        ArrayList<String> list = new ArrayList<>();
        list.add("No Meats");
        list.add("No Seafood");
        list.add("Tofu");
        list.add("Pork");
        list.add("Fish");
        list.add("Salmon");
        list.add("Shrimp");
        list.add("Tuna");
        list.add("Chicken");
        list.add("Oysters");
        list.add("Beef");
        list.add("Crab");
        list.add("Turkey");
        list.add("Lamb");
        list.add("Duck");


        viewModel = ViewModelProviders.of(this).get(QuestionnaireViewModel.class);
        RecyclerView toolList = (RecyclerView) findViewById(R.id.meats_recycler);
        toolList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toolList.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        toolList.setAdapter(adapter);
        Button done = (Button) findViewById(R.id.done_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedFoods = adapter.getSelectedData();
                List<String> dislikedFoods = new ArrayList<>();
                if(selectedFoods.contains("No Meats")) {
                    for(int i = 2; i < list.size(); i++) {
                        dislikedFoods.add(list.get(i));
                    }
                } else if(selectedFoods.contains("No Seafood")) {
                    dislikedFoods.add("Salmon");
                    dislikedFoods.add("Shrimp");
                    dislikedFoods.add("Oysters");
                    dislikedFoods.add("Crab");
                    dislikedFoods.add("Fish");
                } else {
                    dislikedFoods = selectedFoods;
                }
                viewModel.addDislikedFoods(dislikedFoods);
                Intent nextIntent = new Intent(Meats.this,
                        QuestionFour.class);
                startActivity(nextIntent);

            }
        });
    }
}
