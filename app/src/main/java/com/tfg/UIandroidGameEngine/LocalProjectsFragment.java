package com.tfg.UIandroidGameEngine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        Toast.makeText(getActivity().getApplicationContext(), "CREATE", Toast.LENGTH_SHORT).show();
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

        HashMap<String, ArrayList<String[]>> aux = new HashMap<String, ArrayList<String[]>>();
        aux.put("ScaffoldScene", new ArrayList<String[]>());
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));
        dataPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",true, aux)));

        ArrayList<Project> dataNotPublished = new ArrayList<>();

        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));
        dataNotPublished.add(new Project("p1","user1",new ProjectData("Proyecto de Prueba","Plataformas",false, aux)));

        myPublishedProjects =  (RecyclerView) getActivity().findViewById(R.id.publishedProjects);

        PublishedProjectsAdapter publishedAdapter = new PublishedProjectsAdapter(dataPublished,getActivity());

        NotPublishedProjectsAdapter notPublishedAdapter = new NotPublishedProjectsAdapter(dataNotPublished, getActivity());

        myNotPublishedProjects = (RecyclerView) getActivity().findViewById(R.id.myProjects);

        myPublishedProjects.setLayoutManager(new LinearLayoutManager(getActivity()));
        myNotPublishedProjects.setLayoutManager(new LinearLayoutManager(getActivity()));

        myNotPublishedProjects.setAdapter(notPublishedAdapter);
        myPublishedProjects.setAdapter(publishedAdapter);

    }
}