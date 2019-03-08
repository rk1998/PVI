package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.ArrayList;

public class QuestionSix extends AppCompatActivity {

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tools);
        toolList.setAdapter(adapter);

        Button next = (Button) findViewById(R.id.next_button_q6);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionSix.this,
                        MainScreen.class);
                startActivity(nextIntent);
            }
        });
    }
}
