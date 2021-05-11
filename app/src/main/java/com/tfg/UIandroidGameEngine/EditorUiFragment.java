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


    private EditorActivity parentActivity;
    private GameEngine theGameEngine;
    private ObjectsInSceneAdapter os;
    private ComponentsInObjectAdapter oc;
    private RecyclerView objectsInScene;
    private RecyclerView componentsInObject;
    private View selectComponent;
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
     * @param gameEngine Parameter 1.
     * @return A new instance of fragment EditorUiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditorUiFragment newInstance(GameEngine gameEngine) {
        EditorUiFragment fragment = new EditorUiFragment();
        fragment.theGameEngine = gameEngine;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            parentActivity = (EditorActivity) getActivity();

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
        View addComponent = parentActivity.findViewById(R.id.addComponent);

        View gravityComponent = parentActivity.findViewById(R.id.gravityComponent);
        View inputMovementPlatformerComponent = parentActivity.findViewById(R.id.inputMovementPlatformerComponent);
        View groundCollider = parentActivity.findViewById(R.id.groundColliderComponent);
         selectComponent = parentActivity.findViewById(R.id.selectComponentToAdd);


        addComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectComponent.getVisibility() == View.INVISIBLE){
                    selectComponent.setVisibility(View.VISIBLE);
                }else{
                    selectComponent.setVisibility(View.INVISIBLE);
                }

            }
        });
         objectsInScene = (RecyclerView) parentActivity.findViewById(R.id.objectsInCurrentScene);
         componentsInObject = (RecyclerView) parentActivity.findViewById(R.id.componentsInObject);
        objectsInScene.setLayoutManager(new LinearLayoutManager(getActivity()));
        componentsInObject.setLayoutManager(new LinearLayoutManager(getActivity()));



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

        oc = new ComponentsInObjectAdapter(theGameEngine.getObjectsInScene().get(0).components);

        os = new ObjectsInSceneAdapter(theGameEngine.getObjectsInScene());

        os.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                oc.localDataSet = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).components ;
                theGameEngine.camera.fixedLookingPosition.x = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.x;
                theGameEngine.camera.fixedLookingPosition.y = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.y;
                oc.notifyDataSetChanged();
            }
        });



        objectsInScene.setAdapter(os);

        componentsInObject.setAdapter(oc);







        Button addObject = (Button) parentActivity.findViewById(R.id.addObject);
        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = theGameEngine.addGameObject();
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);
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

        //theGameEngine.addGameObject();
        //os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);

        Toast.makeText(getContext(), "" + theGameEngine.isGameRunning,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();

    }
}