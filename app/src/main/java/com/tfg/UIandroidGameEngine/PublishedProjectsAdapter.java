package com.tfg.UIandroidGameEngine;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PublishedProjectsAdapter extends  RecyclerView.Adapter<PublishedProjectsAdapter.ViewHolder> {

    public ArrayList<Project> localDataSet;

    private Activity mainActivity;


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

    public PublishedProjectsAdapter(ArrayList<Project> dataSet, Activity mainActivity){
        this.localDataSet = dataSet;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public PublishedProjectsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_layout,parent,false);



        return new PublishedProjectsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PublishedProjectsAdapter.ViewHolder holder, int position) {
        holder.getPlayOrOpenButton().setText("Jugar" );
        holder.getPlayOrOpenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, EditorActivity.class);

                intent.putExtra("KEY", localDataSet.get(position).getKey());
                intent.putExtra("USER", localDataSet.get(position).getUser());
                intent.putExtra("MODE",0);
                mainActivity.startActivity(intent);
            }
        });
        holder.getProjectDescription().setText("Usuario : " + localDataSet.get(position).getUser() + "                              Tipo de proyecto : "+ localDataSet.get(position).getProject().getProjectType());
        holder.getProjectName().setText("" + localDataSet.get(position).getProject().getTitle());
        holder.getSaveOrPublishButton().setText("Guardar en mis proyectos");
        holder.getSaveOrPublishButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}
