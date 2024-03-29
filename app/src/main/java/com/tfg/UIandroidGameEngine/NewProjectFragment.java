package com.tfg.UIandroidGameEngine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public DatabaseReference myRef = database.getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
    int mode = 1;
    String template = "Plataformas";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewProjectFragment newInstance(String param1, String param2) {
        NewProjectFragment fragment = new NewProjectFragment();
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
        return inflater.inflate(R.layout.fragment_new_project, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState){
        MainActivity ma = (MainActivity) getActivity();
        LinearLayout crearProyecto = (LinearLayout) ma.findViewById(R.id.crearProyecto);
        LinearLayout plantillas = (LinearLayout) ma.findViewById(R.id.plantillas) ;
        TextView tipoProyecto = (TextView) ma.findViewById(R.id.tipoProyecto);
        TextInputEditText ti = (TextInputEditText) ma.findViewById(R.id.editTextProyectName);
        ma.crearProyecto =  crearProyecto;
        ma.plantillas = plantillas;
        Button crear = (Button) getActivity().findViewById(R.id.buttonCrear);

        ((MainActivity)getActivity()).slider = getActivity().findViewById(R.id.configSlider2);
        ((MainActivity)getActivity()).slider.setTranslationX(((MainActivity)getActivity()).width);
        TextView textUser = getActivity().findViewById(R.id.textUser2);
        getActivity().findViewById(R.id.logOutButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(getActivity().getBaseContext(),LoginActivity.class);
                startActivity(a);
            }
        });
        getActivity().findViewById(R.id.helpButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity().getBaseContext(),HelpActivity.class);
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

        myRef = myRef.child("projects");
        crear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditorActivity.class);
                DatabaseReference a = myRef.push();
                String key = a.getKey();

                a.child("project_type").setValue(template);
                a.child("published").setValue(false);
                a.child("title").setValue(ti.getText().toString());

                intent.putExtra("KEY" , key );
                intent.putExtra("MODE",mode);

                startActivity(intent);

            }


        });











        Button plantillaPlataformas = (Button) ma.findViewById(R.id.plantillaPlataformas);
        plantillaPlataformas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;
                template = "Plataformas";
                tipoProyecto.setText("Tipo de Proyecto : Plataformas");
                crearProyecto.setVisibility(View.VISIBLE);
                plantillas.setVisibility(View.INVISIBLE);

            }
        });
    }




}