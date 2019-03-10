package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.QuestionnaireViewModel;

import java.util.ArrayList;

public class QuestionSix extends AppCompatActivity {
    private QuestionnaireViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_six);

        ArrayList<String> tools = new ArrayList<>();
        tools.add("Instant Pot");
        tools.add("Blender");
        tools.add("Food Processor");
        tools.add("Slow Cooker");
        tools.add("Grater");
        tools.add("Whisk");
        tools.add("Vegetable Peeler");
        tools.add("Baking Sheet Pan");
        tools.add("NutriBullet");

        RecyclerView toolList = (RecyclerView) findViewById(R.id.tool_list);
        toolList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toolList.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tools);
        toolList.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(QuestionnaireViewModel.class);
        Button next = (Button) findViewById(R.id.next_button_q6);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionSix.this,
                        QuestionMealSelection.class);
                viewModel.setKitchenTools(adapter.getSelectedData());
                startActivity(nextIntent);
            }
        });
    }
}
