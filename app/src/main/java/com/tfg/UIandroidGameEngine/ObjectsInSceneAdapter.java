package com.tfg.UIandroidGameEngine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObjectsInSceneAdapter extends RecyclerView.Adapter<ObjectsInSceneAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<BasicGameObject> localDataSet;

    private GameEngine theGameEngine ;

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
            objectName = (TextView) view.findViewById(R.id.objectId);

        }

        public TextView getTextView(){
            return objectName;
        }

    }

    public ObjectsInSceneAdapter(ArrayList<BasicGameObject> dataSet,GameEngine theGameEngine){
        this.localDataSet = dataSet;
        this.theGameEngine = theGameEngine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objects_layout,parent,false);

        view.setOnClickListener(this);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText("" + localDataSet.get(position).name);


    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
}
