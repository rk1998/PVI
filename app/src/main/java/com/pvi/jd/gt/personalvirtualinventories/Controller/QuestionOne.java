package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.QuestionnaireViewModel;

public class QuestionOne extends AppCompatActivity {
    private QuestionnaireViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_one);

        viewModel = ViewModelProviders.of(this).get(QuestionnaireViewModel.class);
        EditText familyMembers = (EditText) findViewById(R.id.family_input);
        EditText daysPerWeek = (EditText) findViewById(R.id.days_a_week_input);
        EditText timeSpent = (EditText) findViewById(R.id.time_spent_input);
        Button next = (Button) findViewById(R.id.next_button_q1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String family = familyMembers.getText().toString();
                String days = daysPerWeek.getText().toString();
                String time = timeSpent.getText().toString();
                if(family == null || days == null || time == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuestionOne.this);
                    builder.setCancelable(true);
                    builder.setTitle("Invalid Entries");
                    builder.setMessage("Please enter the number of family members, number of days" +
                            " per week you wish to cook, and the amount of time (in minutes) " +
                            "you want to spend on each meal.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    int numFamily = Integer.parseInt(family);
                    int numDays = Integer.parseInt(days);
                    int timePerMeal = Integer.parseInt(time);
                    if(time.isEmpty() || days.isEmpty() || family.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionOne.this);
                        builder.setCancelable(true);
                        builder.setTitle("Invalid Entires");
                        builder.setMessage("Please enter the number of family members, number of days" +
                                "per week you wish to cook, and the amount of time (in minutes) " +
                                "you want to spend on each meal.");
                        builder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } else if(timePerMeal <= 0 || numFamily <= 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionOne.this);
                        builder.setCancelable(true);
                        builder.setTitle("Invalid Entires");
                        builder.setMessage("Cook time and Number of Family Members cannot be zero. Please" +
                                "enter a valid number.");
                        builder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        viewModel.setGeneralInfo(numFamily, timePerMeal);
                        Intent nextIntent = new Intent(QuestionOne.this,
                                QuestionTwo.class);
                        startActivity(nextIntent);
                    }

                }
            }
        });
    }


}
