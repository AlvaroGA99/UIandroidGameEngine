package com.tfg.UIandroidGameEngine;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PublishedProjectsAdapter extends  RecyclerView.Adapter<PublishedProjectsAdapter.ViewHolder> {

    public ArrayList<Project> localDataSet;

    private Activity mainActivity;

    public DatabaseReference myRef ;

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

    public PublishedProjectsAdapter(ArrayList<Project> dataSet, Activity mainActivity, DatabaseReference myRef){
        this.localDataSet = dataSet;
        this.mainActivity = mainActivity;
        this.myRef = myRef;
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
        holder.getProjectDescription().setText("Usuario : " + localDataSet.get(position).getUser() + "                              Tipo de proyecto : "+ localDataSet.get(position).getType());
        holder.getProjectName().setText("" + localDataSet.get(position).getName());
        holder.getSaveOrPublishButton().setText("Guardar en mis proyectos");
        holder.getSaveOrPublishButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference a = myRef.child("admin123").push();

                a.child("published").setValue(false);
                a.child("title").setValue(localDataSet.get(position).getName());
                a.child("project_type").setValue(localDataSet.get(position).getType());
                myRef.child(localDataSet.get(position).getKey()).child("scenes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot Scene : snapshot.getChildren()){
                            a.child("scenes").child(Scene.getKey()).setValue(Scene.getValue(ArrayList.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}
