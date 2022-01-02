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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

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
    boolean filterByUser;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("users");

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

        //Toast.makeText(getActivity().getApplicationContext(), "CREATE", Toast.LENGTH_SHORT).show();
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
        EditText editTextFilter = (EditText) getActivity().findViewById(R.id.editTextFilterProjects);

        ArrayList<Project> projectsDataset = new ArrayList<Project>();
        //HashMap<String, ArrayList<String[]>> aux = new HashMap<String, ArrayList<String[]>>();
        //aux.put("ScaffoldScene", new ArrayList<String[]>());
       // projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
       // projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        //projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        //projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
        //projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
//        projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
//        projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
//        projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));
//        projectsDataset.add(new Project("p1","user1","Proyecto de prueba","Plataformas"));

        ((MainActivity)getActivity()).slider = getActivity().findViewById(R.id.configSlider4);
        ((MainActivity)getActivity()).slider.setTranslationX(((MainActivity)getActivity()).width);
        TextView textUser = getActivity().findViewById(R.id.textUser4);
        getActivity().findViewById(R.id.logOutButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(getActivity().getBaseContext(),LoginActivity.class);
                startActivity(a);
            }
        });

        getActivity().findViewById(R.id.helpButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity().getBaseContext(),HelpActivity.class);
                startActivity(a);
            }
        });

        Spinner filterby = getActivity().findViewById(R.id.spinner2); //2
        Spinner projectType= getActivity().findViewById(R.id.spinner3); //3

        String[] datafilter = {"Usuario","Nombre de Proyecto"};
        String[] dataproject = {"Todos","Plataformas","Matamarcianos"};

        ArrayAdapter<String> adapterfilter = new ArrayAdapter<String>(getContext(),R.layout.spinner_published_layout ,datafilter );
        ArrayAdapter<String> adapterproject = new ArrayAdapter<String>(getContext(),R.layout.spinner_published_layout ,dataproject );

        filterby.setAdapter(adapterfilter);
        projectType.setAdapter(adapterproject);

        filterby.setSelection(0);
        projectType.setSelection(0);





        myRef.child(""+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textUser.setText( snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef = database.getReference("users");
        PublishedProjectsAdapter  adapter = new PublishedProjectsAdapter(projectsDataset,getActivity(),myRef);
        usersProject.setLayoutManager(new LinearLayoutManager(getActivity()));

        usersProject.setAdapter(adapter);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                projectsDataset.clear();
                projectsDataset.clear();
                Iterable<DataSnapshot> capture =  snapshot.getChildren();
                for (DataSnapshot user : capture){

                    for(DataSnapshot project : user.child("projects").getChildren()){
                        if(project.child("published").getValue(Boolean.class)){
                            projectsDataset.add(new Project(project.getKey(),user.child("username").getValue(String.class),project.child("title").getValue(String.class), project.child("project_type").getValue(String.class) ));
                        }
                    }



                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Error al recuperar los proyectos de la base de datos, inténtelo de nuevo",Toast.LENGTH_SHORT).show();
            }
        });
        filterby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){

                    filterByUser = true;

                }else{
                    filterByUser = false;

                }

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> users = snapshot.getChildren();
                        projectsDataset.clear();
                        String textToMatch = editTextFilter.getText().toString().toLowerCase();
                        for(DataSnapshot user : users){
                            for(DataSnapshot project : user.child("projects").getChildren()){

                                if((project.child("published").getValue(Boolean.class))){
                                    if(filterByUser){


                                        if((editTextFilter.getText().toString().equals("") || user.child("username").getValue(String.class).toLowerCase().contains(textToMatch))){
                                            projectsDataset.add(new Project(project.getKey(),user.child("username").getValue(String.class),project.child("title").getValue(String.class),project.child("project_type").getValue(String.class)));
                                        }
                                    }else{
                                        if(editTextFilter.getText().toString().equals("") || project.child("title").getValue(String.class).toLowerCase().contains(textToMatch)){
                                            projectsDataset.add(new Project(project.getKey(),user.child("username").getValue(String.class),project.child("title").getValue(String.class),project.child("project_type").getValue(String.class)));
                                        }
                                    }
                                }

                            }


                        }

                        adapter.notifyDataSetChanged();
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

        projectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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