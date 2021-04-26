package com.tfg.UIandroidGameEngine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObjectsInSceneAdapter extends RecyclerView.Adapter<ObjectsInSceneAdapter.ViewHolder> {

    private ArrayList<BasicGameObject> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView objectName;

        public ViewHolder(View view){
            super(view);
            objectName = (TextView) view.findViewById(R.id.objectId);

        }

        public TextView getTextView(){
            return objectName;
        }
    }

    public ObjectsInSceneAdapter(ArrayList<BasicGameObject> dataSet){
        this.localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_object_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText("" + localDataSet.get(position).sceneHierarchyID);
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
