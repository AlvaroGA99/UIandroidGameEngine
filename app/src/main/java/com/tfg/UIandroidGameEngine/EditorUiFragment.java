package com.tfg.UIandroidGameEngine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

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
    private View selectObject;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private  boolean inspectorReverse = false;
    private boolean hierarchyReverse = false;

    private   BasicGameObject pointerToSelectedObject ;





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
        Button addComponent = (Button)parentActivity.findViewById(R.id.addComponent);
        Button addObject = (Button) parentActivity.findViewById(R.id.addObject);


        View gravityComponent = parentActivity.findViewById(R.id.gravityComponent);
        View inputMovementPlatformerComponent = parentActivity.findViewById(R.id.inputMovementPlatformerComponent);
        View groundCollider = parentActivity.findViewById(R.id.groundColliderComponent);

        Button addRectangle = (Button)parentActivity.findViewById(R.id.addRectangle);;
        Button addCircle = (Button)parentActivity.findViewById(R.id.addCircle);
        Button addSprite = (Button)parentActivity.findViewById(R.id.addSprite);
        TextInputLayout gameObjectTextViewName = (TextInputLayout)parentActivity.findViewById(R.id.gameObjectTextView);


        selectComponent = parentActivity.findViewById(R.id.selectComponentToAdd);
        selectObject = parentActivity.findViewById(R.id.selectObjectToAdd);








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


        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectObject.getVisibility() == View.INVISIBLE){
                    selectObject.setVisibility(View.VISIBLE);
                }else{
                    selectObject.setVisibility(View.INVISIBLE);
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

        pointerToSelectedObject = theGameEngine.getObjectsInScene().get(0);

        os = new ObjectsInSceneAdapter(theGameEngine.getObjectsInScene());


        gravityComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "GravityComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new GravityComponent(pointerToSelectedObject));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });

        inputMovementPlatformerComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "InputMovementPlatformerComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new InputMovementPlatformerComponent(pointerToSelectedObject));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });


        groundCollider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "GroundColliderComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new GroundColliderComponent(pointerToSelectedObject));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });

        os.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                oc.localDataSet = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).components ;
                pointerToSelectedObject =  theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v));
                theGameEngine.camera.fixedLookingPosition.x = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.x;
                theGameEngine.camera.fixedLookingPosition.y = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.y;
                oc.notifyDataSetChanged();
            }
        });

        addRectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectObject.setVisibility(View.INVISIBLE);
                int id = theGameEngine.addGameObject(0);
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);
                Toast.makeText(getContext(),"" + id,Toast.LENGTH_SHORT).show();

            }
        });

        addCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectObject.setVisibility(View.INVISIBLE);
                int id = theGameEngine.addGameObject(1);
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);
                Toast.makeText(getContext(),"" + id,Toast.LENGTH_SHORT).show();
            }
        });

        addSprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectObject.setVisibility(View.INVISIBLE);
                int id = theGameEngine.addGameObject(2);
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);
                Toast.makeText(getContext(),"" + id,Toast.LENGTH_SHORT).show();
            }
        });

        objectsInScene.setAdapter(os);

        componentsInObject.setAdapter(oc);



        theGameEngine.isInEditor = true;




        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!theGameEngine.isInEditor){

                    if(!theGameEngine.isGameRunning){

                        //poner boton de reset

                        theGameEngine.isInEditor = true;
                        theGameEngine.loadScene();
                        os.notifyDataSetChanged();

                        //poner boton de pausa
                    }
                }
                theGameEngine.isGameRunning = false;
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theGameEngine.isInEditor){
                    theGameEngine.saveThisScene();
                    theGameEngine.isInEditor = false;
                }
                theGameEngine.isGameRunning = true;

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