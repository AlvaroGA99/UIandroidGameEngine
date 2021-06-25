package com.tfg.UIandroidGameEngine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CollisionListAdapter extends RecyclerView.Adapter<CollisionListAdapter.ViewHolder> implements View.OnClickListener {

    public ArrayList<BasicGameObject> localDataSet;

    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView objectName;

        public ViewHolder(View view){
            super(view);
            objectName = (TextView) view.findViewById(R.id.objectIdForCollision);

        }

        public TextView getTextView(){
            return objectName;
        }
    }

    public CollisionListAdapter(ArrayList<BasicGameObject> dataSet, int id){
        this.localDataSet = new ArrayList<>();
        for (int i = 0; i < dataSet.size(); i ++){
            if(dataSet.get(i).sceneHierarchyID != id){
                this.localDataSet.add(dataSet.get(i));
            }
        }

    }

    public void  updateDataSet(ArrayList<BasicGameObject> dataSet, int id){
        this.localDataSet.clear();
        for (int i = 0; i < dataSet.size(); i ++){
            if(dataSet.get(i).sceneHierarchyID != id){
                this.localDataSet.add(dataSet.get(i));
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CollisionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collisions_layout,parent,false);

        view.setOnClickListener(this);

        return new CollisionListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CollisionListAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText("\nOnCollision with " + localDataSet.get(position).name + " (ID : " + localDataSet.get(position).sceneHierarchyID + ")\n");
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
}


