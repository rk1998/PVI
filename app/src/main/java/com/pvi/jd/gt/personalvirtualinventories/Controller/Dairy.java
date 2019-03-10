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
import java.util.List;


import com.pvi.jd.gt.personalvirtualinventories.R;

public class Dairy extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionnaireViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meats);


        ArrayList<String> list = new ArrayList<>();
        list.add("Butter");
        list.add("Sour Cream");
        list.add("Milk");
        list.add("Yogurt");
        list.add("Ice Cream");
        list.add("Cream Cheese");



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
                viewModel.addDislikedFoods(selectedFoods);
                Intent nextIntent = new Intent(Dairy.this,
                        QuestionFour.class);
                startActivity(nextIntent);

            }
        });
    }
}

