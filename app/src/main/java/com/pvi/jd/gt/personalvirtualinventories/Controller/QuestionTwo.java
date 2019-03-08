package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.ArrayList;

public class QuestionTwo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_two);

        ArrayList<String> list = new ArrayList<>();
        list.add("Vegan");
        list.add("Vegetarian");
        list.add("Pescatarian");
        list.add("Low Carb");
        list.add("Low Sugar");

        RecyclerView toolList = (RecyclerView) findViewById(R.id.diet_pref_list);
        toolList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toolList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        toolList.setAdapter(adapter);

        Button next = (Button) findViewById(R.id.next_button_q2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionTwo.this,
                        QuestionThree.class);
                startActivity(nextIntent);
            }
        });

    }
}
