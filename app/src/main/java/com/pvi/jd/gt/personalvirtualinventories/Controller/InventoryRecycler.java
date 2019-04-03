package com.pvi.jd.gt.personalvirtualinventories.Controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pvi.jd.gt.personalvirtualinventories.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryRecycler extends RecyclerView.Adapter<InventoryRecycler.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private Context context;
    private List<String> selectedData = new ArrayList<>();

    // data is passed into the constructor
    InventoryRecycler(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.inventory_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.myTextView.setText(text);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<String> getSelectedData() {
        return selectedData;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        boolean selected;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.inventory_item);
            selected = false;
            itemView.setOnClickListener(this);
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