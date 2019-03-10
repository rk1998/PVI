package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.QuestionnaireViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionFour extends AppCompatActivity {

    Dialog myDialog;
    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionnaireViewModel viewModel;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_four);
        myDialog = new Dialog(this);


        Button next = (Button) findViewById(R.id.next_button_q4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFour.this,
                        QuestionFive.class);
                startActivity(nextIntent);
            }
        });

        Button meats = (Button) findViewById(R.id.meats_button);
        meats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFour.this,
                        Meats.class);
                startActivity(nextIntent);

            }
        });

        Button fruits = (Button) findViewById(R.id.fruits_button);
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFour.this,
                        Fruits.class);
                startActivity(nextIntent);

            }
        });

        Button dairy = (Button) findViewById(R.id.dairy_button);
        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFour.this,
                        Dairy.class);
                startActivity(nextIntent);

            }
        });

        Button veggies = (Button) findViewById(R.id.vegetables_button);
        veggies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFour.this,
                        Veggies.class);
                startActivity(nextIntent);

            }
        });

        Button condiments = (Button) findViewById(R.id.condiments_button);
        condiments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFour.this,
                        Condiments.class);
                startActivity(nextIntent);

            }
        });

        Button grains = (Button) findViewById(R.id.grains_button);
        grains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(QuestionFour.this,
                        Grains.class);
                startActivity(nextIntent);

            }
        });





    }




//    public void ShowMeatsPopup() {
//
//
//        TextView txtclose;
//        myDialog.setContentView(R.layout.question_four_popup);
////
////        ArrayList<String> list = new ArrayList<>();
////        list.add("Chicken");
////        list.add("Beef");
////        list.add("Pork");
////        list.add("Fish");
////        list.add("Turkey");
////        RecyclerView toolList = (RecyclerView) findViewById(R.id.popup);
////        toolList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
////        toolList.setLayoutManager(new LinearLayoutManager(mContext));
////        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(mContext, list);
////        toolList.setAdapter(adapter);
//
//        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
//        txtclose.setText("X");
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//    }
}
