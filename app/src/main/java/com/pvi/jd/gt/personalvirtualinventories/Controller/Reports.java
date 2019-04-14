package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.view.View;

import com.pvi.jd.gt.personalvirtualinventories.R;

public class Reports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ConstraintLayout mConstraint = (ConstraintLayout) findViewById(R.id.constraintLayout);

        Button reports = (Button) findViewById(R.id.reports_button);
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConstraint.setBackgroundResource(R.drawable.breakdown);
            }
        });

        Button overview = (Button) findViewById(R.id.overview_button);
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConstraint.setBackgroundResource(R.drawable.report);
            }
        });
    }
}
