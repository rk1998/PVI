package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import com.pvi.jd.gt.personalvirtualinventories.R;

public class PantryMeals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_meals);


        Button next = (Button) findViewById(R.id.next_button_pantry);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(PantryMeals.this,
                        MainScreen.class);
                startActivity(nextIntent);
            }
        });
    }
}
