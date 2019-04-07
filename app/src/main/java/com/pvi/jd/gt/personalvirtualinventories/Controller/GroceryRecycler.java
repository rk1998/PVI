package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.R;
import com.pvi.jd.gt.personalvirtualinventories.ViewModels.GroceryListViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroceryRecycler extends RecyclerView.Adapter<GroceryRecycler.ViewHolder> {

    private List<String> mData;
    private List<IngredientQuantity> mIngredientQuantities;
    private LayoutInflater mInflater;
    private Context context;
    private List<String> selectedData = new ArrayList<>();
    private GroceryListViewModel groceryListViewModel;
    // data is passed into the constructor
    GroceryRecycler(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    /**
     * Constructor for GroceryRecycler
     * @param context Grocery List activity context
     * @param data grocery list data
     * @param viewModel reference to GroceryListViewModel
     */
    GroceryRecycler(Context context, List<String> data,
                    List<IngredientQuantity> ingredientQuantities, GroceryListViewModel viewModel) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.groceryListViewModel = viewModel;
        this.mIngredientQuantities = ingredientQuantities;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grocerylist_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.myTextView.setText(text);
        holder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientQuantity ingredient = mIngredientQuantities.get(position);
                groceryListViewModel.checkOffGroceryListItem(ingredient);
            }
        });
        holder.setPosition(position);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Removes item from grocery list view
     * @param position position
     */
    public void removeGroceryListItem(int position) {
        if(position >= 0 && position < mData.size()) {
            mData.remove(position);
        }
        notifyItemRemoved(position);
    }


    public List<String> getSelectedData() {
        return selectedData;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox myTextView;
        boolean selected;
        int position;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.grocery_view_item);
            selected = false;
            itemView.setOnClickListener(this);
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {


//            if (!selected) {
//                myTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                selectedData.add(myTextView.getText().toString());
//                selected = true;
//            } else {
//                myTextView.setBackgroundColor(0);
//                selectedData.remove(myTextView.getText().toString());
//                selected = false;
//            }
        }
    }
}