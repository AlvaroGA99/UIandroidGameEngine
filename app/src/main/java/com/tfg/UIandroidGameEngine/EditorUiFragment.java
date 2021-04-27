package com.tfg.UIandroidGameEngine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditorUiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditorUiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditorActivity parentActivity;
    private GameEngine theGameEngine;
    private ObjectsInSceneAdapter os;
    private RecyclerView objectsInScene;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private  boolean inspectorReverse = false;
    private boolean hierarchyReverse = false;





    public EditorUiFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditorUiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditorUiFragment newInstance(String param1, String param2) {
        EditorUiFragment fragment = new EditorUiFragment();
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
        }else{
            parentActivity = (EditorActivity) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_editor_ui, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState){

        View ObjectHierarchy =  parentActivity.findViewById(R.id.objectHierarchy);
        View inspector = parentActivity.findViewById(R.id.inspector);
        View pause = parentActivity.findViewById(R.id.pause);
        View resume = parentActivity.findViewById(R.id.resume);
         objectsInScene = (RecyclerView) parentActivity.findViewById(R.id.objectsInCurrentScene);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       /* ValueAnimator animation = ObjectAnimator.ofFloat(ObjectHierarchy, "translationX", -100f);
        animation.setDuration(2000);
        ValueAnimator animation2 = ObjectAnimator.ofFloat(inspector, "translationX", 100f);
        animation2.setDuration(2000)*/;




        ObjectHierarchy.setTranslationX(-1000f);
        inspector.setTranslationX(1000f);


        inspector.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                
                //if(canAnimateInspector){

                if(!inspectorReverse){
                    v.animate().translationX(-45);

                }else{
                    v.animate().translationX(1000);
                }
                inspectorReverse = !inspectorReverse;

                //}


            }
        });

        ObjectHierarchy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //if(canAnimateHierarchy){
                if(!hierarchyReverse){
                    v.animate().translationX(45);

                }else{
                    v.animate().translationX(-1000);
                }
                hierarchyReverse = !hierarchyReverse;

                //animation.reverse();
            }
        });
         theGameEngine = ((EditorGameSurfaceFragment)parentActivity.fm.getFragments().get(0)).theGameEngine;
        os = new ObjectsInSceneAdapter(theGameEngine.getObjectsInScene());
        objectsInScene.setAdapter(os);

        objectsInScene.setLayoutManager(new LinearLayoutManager(getActivity()));





        Button addObject = (Button) parentActivity.findViewById(R.id.addObject);
        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = theGameEngine.addGameObject();
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size());
                Toast.makeText(getContext(),"" + id,Toast.LENGTH_SHORT).show();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theGameEngine.pause_restartGame();
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theGameEngine.playGame();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        os.notifyDataSetChanged();
        Toast.makeText(getContext(), "" + theGameEngine.isGameRunning,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        os.notifyDataSetChanged();
    }
}