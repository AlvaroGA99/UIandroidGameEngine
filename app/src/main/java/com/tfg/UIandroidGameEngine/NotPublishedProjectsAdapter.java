package com.tfg.UIandroidGameEngine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NotPublishedProjectsAdapter extends RecyclerView.Adapter<NotPublishedProjectsAdapter.ViewHolder>{

    public ArrayList<Project> localDataSet;

    private Activity mainActivity;

    private DatabaseReference myRef;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView projectName;
        private final TextView projectDescription;
        private final Button saveOrPublishButton;
        private final Button playOrOpenButton;

        public ViewHolder(View view){
            super(view);
            projectName = (TextView) view.findViewById(R.id.projectName);
            projectDescription= (TextView) view.findViewById(R.id.projectDescription);
            playOrOpenButton = (Button) view.findViewById(R.id.playOrOpenButton);
            saveOrPublishButton = (Button) view.findViewById(R.id.saveOrPublishButton);


        }

        public TextView getProjectName(){
            return projectName;
        }

        public Button getPlayOrOpenButton() {
            return playOrOpenButton;
        }

        public Button getSaveOrPublishButton() {
            return saveOrPublishButton;
        }

        public TextView getProjectDescription() {
            return projectDescription;
        }
    }

    public NotPublishedProjectsAdapter(ArrayList<Project> dataSet, Activity mainActivity, DatabaseReference myRef){
        this.localDataSet = dataSet;
        this.mainActivity = mainActivity;
        this.myRef = myRef;
    }

    @NonNull
    @Override
    public NotPublishedProjectsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_layout,parent,false);



        return new NotPublishedProjectsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NotPublishedProjectsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getPlayOrOpenButton().setText("Abrir Proyecto" );
        holder.getPlayOrOpenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, EditorActivity.class);

                intent.putExtra("KEY", localDataSet.get(position).getKey());
                intent.putExtra("USER", localDataSet.get(position).getUser());
                intent.putExtra("MODE",1);
                mainActivity.startActivity(intent);
            }
        });
        holder.getProjectDescription().setText("Usuario : " + localDataSet.get(position).getUser() + "                              Tipo de proyecto : "+ localDataSet.get(position).getType());
        holder.getProjectName().setText("" + localDataSet.get(position).getName());
        holder.getSaveOrPublishButton().setText("Publicar");
        holder.getSaveOrPublishButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("projects/"+localDataSet.get(position).getKey()).child("published").setValue(true);
            }
        });
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
