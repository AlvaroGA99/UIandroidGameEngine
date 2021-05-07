package com.tfg.UIandroidGameEngine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComponentsInObjectAdapter extends RecyclerView.Adapter<ComponentsInObjectAdapter.ViewHolder> {

    public ArrayList<Component> localDataSet;

    public ComponentsInObjectAdapter(ArrayList<Component> dataSet){
        this.localDataSet = dataSet;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.components_layout,parent,false);

        return new ComponentsInObjectAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText("" + localDataSet.get(position).name);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

     private final TextView componentName;

        public ViewHolder( View view) {
            super(view);

            componentName = (TextView) view.findViewById(R.id.componentName);
        }

      public TextView getTextView(){
            return componentName;
      }
    }

}
