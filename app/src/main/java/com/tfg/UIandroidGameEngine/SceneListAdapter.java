package com.tfg.UIandroidGameEngine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class SceneListAdapter extends  RecyclerView.Adapter<SceneListAdapter.ViewHolder> implements View.OnClickListener {

    public ArrayList<String> localDataSet;

    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView sceneName;

        public ViewHolder(View view){
            super(view);
            sceneName = (TextView) view.findViewById(R.id.sceneName);

        }

        public TextView getTextView(){
            return sceneName;
        }
    }

    public SceneListAdapter(HashMap<String,ArrayList<String[]>> dataSet){
        localDataSet = new ArrayList<>();
       for(String key : dataSet.keySet()){
           localDataSet.add(key);
       }
    }

    public void updateDataSet(HashMap<String,ArrayList<String[]>> dataSet){
        localDataSet.clear();
        for(String key : dataSet.keySet()){
            localDataSet.add(key);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SceneListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scenes_layout,parent,false);

        view.setOnClickListener(this);

        return new SceneListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SceneListAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText("" + localDataSet.get(position));
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

}
