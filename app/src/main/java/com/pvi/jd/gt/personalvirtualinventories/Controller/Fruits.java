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

public class Fruits extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionnaireViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meats);


        ArrayList<String> list = new ArrayList<>();
        list.add("No Fruits");
        list.add("Bananas");
        list.add("Avocados");
        list.add("Coconut");
        list.add("Cherries");
        list.add("Apples");
        list.add("Pineapples");
        list.add("Strawberries");
        list.add("Pears");
        list.add("Grapes");
        list.add("Oranges");
        list.add("Lemons");
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
                if(selectedFoods.contains("No Fruits")) {
                    for(int i = 1; i < list.size(); i++) {
                        dislikedFoods.add(list.get(i));
                    }
                } else {
                    dislikedFoods = selectedFoods;
                }
                viewModel.addDislikedFoods(dislikedFoods);
                Intent nextIntent = new Intent(Fruits.this,
                        QuestionFour.class);
                startActivity(nextIntent);

            }
        });
    }
}