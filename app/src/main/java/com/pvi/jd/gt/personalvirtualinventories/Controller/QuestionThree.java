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

public class QuestionThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_three);

        ArrayList<String> list = new ArrayList<>();
        list.add("Gluten");
        list.add("Dairy");
        list.add("Egg");
        list.add("Peanut");
        list.add("Soy");

        RecyclerView toolList = (RecyclerView) findViewById(R.id.allergy_list);
        toolList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        toolList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        toolList.setAdapter(adapter);

        Button next = (Button) findViewById(R.id.next_button_q3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionThree.this,
                        QuestionFour.class);
                startActivity(nextIntent);
            }
        });
    }
}
