package com.tfg.UIandroidGameEngine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsInObjectAdapter extends RecyclerView.Adapter<EventsInObjectAdapter.ViewHolder> {


    private ArrayList<String> localDataSet;

    private ArrayList<BasicGameObject> objectsInScene;

    public EventsInObjectAdapter(ActionHolder actionHolder,ArrayList<BasicGameObject> objectsInScene){
        this.objectsInScene = objectsInScene;

        localDataSet = new ArrayList<String>();



        for(int i = 0; i < actionHolder.onClickActions.size(); i++){
            localDataSet.add("OnClick Event | " + actionHolder.onClickActions.get(i).name);
        }

        for(int i = 0; i < actionHolder.updateActions.size(); i++){
            localDataSet.add("OnEachSecond Event | " + actionHolder.updateActions.get(i).name);
        }

        for(int i = 0; i < actionHolder.startSceneActions.size(); i++){
            localDataSet.add("OnStartScene Event | " + actionHolder.startSceneActions.get(i).name);
        }

        for(int i = 0  ; i < actionHolder.collisionActions.size(); i++){
            for (int j = 0; j < actionHolder.collisionActions.get(i).size(); j++){
                localDataSet.add("Collision with " + objectsInScene.get(i).name +  " (ID : " + i + ") | " + actionHolder.collisionActions.get(i).get(j).name);
            }

        }




    }



    public void updateLocalDataSet(ActionHolder actionHolder){
        localDataSet.clear();

        for(int i = 0; i < actionHolder.onClickActions.size(); i++){
            localDataSet.add("OnClick Event | " + actionHolder.onClickActions.get(i).name);
        }

        for(int i = 0; i < actionHolder.updateActions.size(); i++){
            localDataSet.add("OnEachSecond Event | " + actionHolder.updateActions.get(i).name);
        }

        for(int i = 0; i < actionHolder.startSceneActions.size(); i++){
            localDataSet.add("OnStartScene Event | " + actionHolder.startSceneActions.get(i).name);
        }

        for(int i = 0  ; i < actionHolder.collisionActions.size(); i++){
            for (int j = 0; j < actionHolder.collisionActions.get(i).size(); j++){
                localDataSet.add("Collision with " + objectsInScene.get(i).name +  " (ID : " + i + ") | " + actionHolder.collisionActions.get(i).get(j));
            }

        }
        notifyDataSetChanged();;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView actionName;

        public ViewHolder( View view) {
            super(view);

            actionName = (TextView) view.findViewById(R.id.eventActionName);
        }

        public TextView getTextView(){
            return actionName;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_layout,parent,false);

        return new EventsInObjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
