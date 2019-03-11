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

public class Condiments extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionnaireViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meats);


        ArrayList<String> list = new ArrayList<>();
        list.add("Ketchup");
        list.add("Mustard");
        list.add("Mayonnaise");
        list.add("Oil");
        list.add("Vinegar");
        list.add("Soy Sauce");
        list.add("Hot Sauce");
        list.add("BBQ Sauce");
        list.add("Pesto Sauce");
        list.add("Horseradish");
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
                viewModel.addDislikedFoods(selectedFoods);
                Intent nextIntent = new Intent(Condiments.this,
                        QuestionFour.class);
                startActivity(nextIntent);

            }
        });

    }
}
