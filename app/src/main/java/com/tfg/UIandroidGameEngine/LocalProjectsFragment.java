package com.tfg.UIandroidGameEngine;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalProjectsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView myPublishedProjects;
    private RecyclerView myNotPublishedProjects;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocalProjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocalProjectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalProjectsFragment newInstance(String param1, String param2) {
        LocalProjectsFragment fragment = new LocalProjectsFragment();
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
        //Toast.makeText(getActivity().getApplicationContext(), "CREATE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_projects, container, false);
    }
    @Override
    public void onViewCreated( View view, Bundle savedInstanceState){



        ArrayList<Project> dataPublished = new ArrayList<>();

        //HashMap<String, ArrayList<String[]>> aux = new HashMap<String, ArrayList<String[]>>();
        //aux.put("ScaffoldScene", new ArrayList<String[]>());
        /*dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Platformer"));*/


        ArrayList<Project> dataNotPublished = new ArrayList<>();

        /*dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        dataPublished.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));*/
        ((MainActivity)getActivity()).slider = getActivity().findViewById(R.id.configSlider3);
        ((MainActivity)getActivity()).slider.setTranslationX(((MainActivity)getActivity()).width);
        TextView textUser = getActivity().findViewById(R.id.textUser3);
        getActivity().findViewById(R.id.logOutButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(getActivity().getBaseContext(),LoginActivity.class);
                startActivity(a);
            }
        });
        myRef.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textUser.setText( snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myPublishedProjects =  (RecyclerView) getActivity().findViewById(R.id.publishedProjects);

        PublishedProjectsAdapter publishedAdapter = new PublishedProjectsAdapter(dataPublished,getActivity(),myRef);

        NotPublishedProjectsAdapter notPublishedAdapter = new NotPublishedProjectsAdapter(dataNotPublished, getActivity(),myRef);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataPublished.clear();
                dataNotPublished.clear();

                Iterable<DataSnapshot> capture =  snapshot.child("projects").getChildren();
                for (DataSnapshot project : capture){

                    if(project.child("published").getValue(Boolean.class)){
                        dataPublished.add(new Project(project.getKey(),snapshot.child("username").getValue(String.class),project.child("title").getValue(String.class), project.child("project_type").getValue(String.class) ));
                    }else{
                        dataNotPublished.add(new Project(project.getKey(),snapshot.child("username").getValue(String.class),project.child("title").getValue(String.class), project.child("project_type").getValue(String.class) ));
                    }


                }
               notPublishedAdapter.notifyDataSetChanged();
                publishedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Error al recuperar los proyectos de la base de datos, inténtelo de nuevo",Toast.LENGTH_SHORT).show();
            }
        });

        myNotPublishedProjects = (RecyclerView) getActivity().findViewById(R.id.myProjects);

        myPublishedProjects.setLayoutManager(new LinearLayoutManager(getActivity()));
        myNotPublishedProjects.setLayoutManager(new LinearLayoutManager(getActivity()));

        myNotPublishedProjects.setAdapter(notPublishedAdapter);
        myPublishedProjects.setAdapter(publishedAdapter);


        String[] data = {"Publicados", "No Publicados"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_published_layout ,data );


        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position  == 0){
                    myPublishedProjects.setVisibility(View.VISIBLE);
                    myNotPublishedProjects.setVisibility(View.INVISIBLE);
                }else{
                    myPublishedProjects.setVisibility(View.INVISIBLE);
                    myNotPublishedProjects.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String[] dataProjectType = {"Todos","Plataformas","Matamarcianos"};
        Spinner spinnerProjectType = getActivity().findViewById(R.id.spinner4);
        ArrayAdapter<String> adapterprojecttype = new ArrayAdapter<String>(getContext(),R.layout.spinner_published_layout ,dataProjectType );
        spinnerProjectType.setAdapter(adapterprojecttype);
        spinnerProjectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       dataNotPublished.clear();
                       dataPublished.clear();
                       for(DataSnapshot project : snapshot.child("projects").getChildren()){
                           if(position == 0){
                               if(project.child("published").getValue(Boolean.class)){
                                   dataPublished.add(new Project(project.getKey(),snapshot.child("username").getValue(String.class),project.child("title").getValue(String.class), project.child("project_type").getValue(String.class) ));
                               }else{
                                   dataNotPublished.add(new Project(project.getKey(),snapshot.child("username").getValue(String.class),project.child("title").getValue(String.class), project.child("project_type").getValue(String.class) ));
                               }
                           }else{

                               if(project.child("project_type").getValue(String.class).equals(dataProjectType[position])) {

                                   if(project.child("published").getValue(Boolean.class)){

                                       dataPublished.add(new Project(project.getKey(), snapshot.child("username").getValue(String.class), project.child("title").getValue(String.class), project.child("project_type").getValue(String.class)));

                                   }else{
                                       dataNotPublished.add(new Project(project.getKey(),snapshot.child("username").getValue(String.class),project.child("title").getValue(String.class), project.child("project_type").getValue(String.class) ));
                                   }
                           }
                           }
                       }

                        notPublishedAdapter.notifyDataSetChanged();
                        publishedAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}