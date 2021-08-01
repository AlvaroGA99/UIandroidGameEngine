package com.tfg.UIandroidGameEngine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentManager fg;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
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
        fg = getActivity().getSupportFragmentManager();
        Toast.makeText(getActivity().getApplicationContext(), "CREATE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        Button newProject = (Button) view.findViewById(R.id.newproject);
        Button localProjects = (Button) view.findViewById(R.id.localprojects);
        Button usersProject = (Button) view.findViewById(R.id.usersprojects);
        TextView users = (TextView) view.findViewById(R.id.usuario);
        Button closeSesion = (Button) view.findViewById(R.id.cerrar_sesion);

        closeSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        newProject.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Do something in response to button click

                fg.beginTransaction().replace(R.id.container, new NewProjectFragment()).addToBackStack(null).commit();

            }
        });

        localProjects.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Do something in response to button click

                fg.beginTransaction().replace(R.id.container, new LocalProjectsFragment()).addToBackStack(null).commit();
            }
        });

        usersProject.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Do something in response to button click

                fg.beginTransaction().replace(R.id.container, new UsersProjectFragment()).addToBackStack(null).commit();
            }
        });

    }


}