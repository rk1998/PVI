package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.GroceryListViewModel;

public class AddGroceryItem extends AppCompatActivity {
    private GroceryListViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        viewModel = ViewModelProviders.of(this).get(GroceryListViewModel.class);
        viewModel.init(this);
        getSupportActionBar().setTitle("ADD ITEM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EditText itemName = findViewById(R.id.add_name);
        EditText itemQuantity = findViewById(R.id.add_quantity);

        Button add = (Button) findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemName.getText().toString();
                String quantity = itemQuantity.getText().toString();
                if(name.isEmpty() || quantity.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddGroceryItem.this);
                    builder.setCancelable(true);
                    builder.setTitle("Invalid Entries");
                    builder.setMessage("Please enter the name and quantity of the grocery item you" +
                            "are adding to your list.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    IngredientQuantity newGroceryItem = new IngredientQuantity(name, quantity);
                    viewModel.addToGroceryList(newGroceryItem);
                    Intent nextIntent = new Intent(AddGroceryItem.this,
                            GroceryList.class);
                    startActivity(nextIntent);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
