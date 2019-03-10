package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;

import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.QuestionnaireViewModel;

public class QuestionFive extends AppCompatActivity {
    private QuestionnaireViewModel viewModel;
    private QuestionFiveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_five);

        GridView gridView = (GridView) findViewById(R.id.question_five_gridview);
        adapter = new QuestionFiveAdapter(this);
        gridView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(QuestionnaireViewModel.class);

        Button next = (Button) findViewById(R.id.next_button_q5);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFive.this,
                        QuestionSix.class);
                viewModel.setFavoriteMeals(adapter.getSelectedData());
                startActivity(nextIntent);
            }
        });
    }
}
