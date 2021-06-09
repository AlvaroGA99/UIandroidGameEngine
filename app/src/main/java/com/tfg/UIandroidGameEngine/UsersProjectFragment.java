package com.tfg.UIandroidGameEngine;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference   db = FirebaseDatabase.getInstance().getReference();
    private RecyclerView usersProject;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsersProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersProjectFragment newInstance(String param1, String param2) {
        UsersProjectFragment fragment = new UsersProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Toast.makeText(getActivity().getApplicationContext(), "CREATE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_project, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState){
         usersProject = getActivity().findViewById(R.id.usersProjects);
        ArrayList<Project> projectsDataset = new ArrayList<Project>();
        HashMap<String, ArrayList<String[]>> aux = new HashMap<String, ArrayList<String[]>>();
        aux.put("ScaffoldScene", new ArrayList<String[]>());
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        projectsDataset.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));


        PublishedProjectsAdapter  adapter = new PublishedProjectsAdapter(projectsDataset,getActivity());
        usersProject.setLayoutManager(new LinearLayoutManager(getActivity()));

        usersProject.setAdapter(adapter);



        /*db.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "JGJHGJHGJH" + db.child("admin12378") , Toast.LENGTH_SHORT).show();
                String user ;
                String key;
                ArrayList<Project> projectsDataset = new ArrayList<Project>();
                for(DataSnapshot e : snapshot.getChildren()){
                    user = e.getKey();
                    for(DataSnapshot f : e.getChildren()){
                        if(f.child("published").getValue(Boolean.class)){
                            key = f.getKey();
                            ProjectData pd = f.getValue(ProjectData.class);
                            Project p = new Project(key,user,pd);
                            projectsDataset.add(p);
                        }

                    }
                }
                adapter.localDataSet = projectsDataset;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

*/







    }
}