package com.tfg.UIandroidGameEngine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
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
    private EventsInObjectAdapter oe;
    private SceneListAdapter sa ;
    private RecyclerView objectsInScene;
    private RecyclerView componentsInObject;
    private RecyclerView eventsInObject;
    private RecyclerView scenes;
    private View selectComponent;
    private View selectObject;
    private Switch focusedByCamera;
    private int previousSelectedId;
     private TextView scaleX;
     private TextView scaleY;
     private TextView rotation;
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
        Button publishInProject = (Button) parentActivity.findViewById(R.id.publishInEditor);
        Button saveInEditor = (Button) parentActivity.findViewById(R.id.saveInEditor);

        View gravityComponent = parentActivity.findViewById(R.id.gravityComponent);
        View inputMovementPlatformerComponent = parentActivity.findViewById(R.id.inputMovementPlatformerComponent);
        View groundCollider = parentActivity.findViewById(R.id.groundColliderComponent);

        Button addRectangle = (Button)parentActivity.findViewById(R.id.addRectangle);;
        Button addCircle = (Button)parentActivity.findViewById(R.id.addCircle);
        Button addSprite = (Button)parentActivity.findViewById(R.id.addSprite);
        TextInputEditText gameObjectTextViewName = (TextInputEditText) parentActivity.findViewById(R.id.gameObjectTextView);




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
         eventsInObject = (RecyclerView) parentActivity.findViewById(R.id.eventsInObject);
         scenes = (RecyclerView) parentActivity.findViewById(R.id.scenes);
        objectsInScene.setLayoutManager(new LinearLayoutManager(getActivity()));
        componentsInObject.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventsInObject.setLayoutManager(new LinearLayoutManager(getActivity()));
        scenes.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));



        sa = new SceneListAdapter(theGameEngine.SceneHierarchyDescription);

        scenes.setAdapter(sa);





        pointerToSelectedObject = theGameEngine.getObjectsInScene().get(0);


       /* ValueAnimator animation = ObjectAnimator.ofFloat(ObjectHierarchy, "translationX", -100f);
        animation.setDuration(2000);
        ValueAnimator animation2 = ObjectAnimator.ofFloat(inspector, "translationX", 100f);
        animation2.setDuration(2000)*/;




        ObjectHierarchy.setTranslationX(-1*(parentActivity.getWidth()/2  - 100));
        inspector.setTranslationX((parentActivity.getWidth()/2  - 100));


        inspector.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                
                //if(canAnimateInspector){

                if(!inspectorReverse){
                    v.animate().translationX(-45);


                }else{
                    v.animate().translationX(parentActivity.getWidth()/2  - 100);
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
                    v.animate().translationX(-1*(parentActivity.getWidth()/2  - 100));
                }
                hierarchyReverse = !hierarchyReverse;

                //animation.reverse();
            }
        });

        oc = new ComponentsInObjectAdapter(theGameEngine.getObjectsInScene().get(0).components);
        oe = new EventsInObjectAdapter(theGameEngine.getObjectsInScene().get(0).actionHolder,theGameEngine.getObjectsInScene());


        focusedByCamera = (Switch) parentActivity.findViewById(R.id.isFocusedByCamera);
        focusedByCamera.setChecked(pointerToSelectedObject.isFocusedByCamera);

        scaleX = (TextView) parentActivity.findViewById(R.id.textoEscalaX);
        scaleY = (TextView) parentActivity.findViewById(R.id.textoEscalaY);
        rotation = (TextView) parentActivity.findViewById(R.id.textoRotacion);

        scaleX.setText("X : " + pointerToSelectedObject.scale.x);
        scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
        rotation.setText("" + pointerToSelectedObject.rotation);

        View moreScaleX = parentActivity.findViewById(R.id.aumentarEscalaX);
            moreScaleX.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pointerToSelectedObject.scale.x += 0.1;
                    pointerToSelectedObject.preUpdateScale.x += 0.1;
                    scaleX.setText("X : " + pointerToSelectedObject.scale.x);
                    return false;
                }
            });
        View moreScaleY  = parentActivity.findViewById(R.id.aumentarEscalaY);
            moreScaleY.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pointerToSelectedObject.scale.y += 0.1 ;
                    pointerToSelectedObject.preUpdateScale.y += 0.1;
                    scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
                    return false;
                }
            });
        View moreRotation = parentActivity.findViewById(R.id.aumentarRotacion);
            moreRotation.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pointerToSelectedObject.rotation += 0.1;
                    pointerToSelectedObject.preUpdateRotation += 0.1;
                    rotation.setText("" + pointerToSelectedObject.rotation);
                    return false;
                }
            });

        View lessScaleX = parentActivity.findViewById(R.id.disminuirEscalaX);
            lessScaleX.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pointerToSelectedObject.scale.x -= 0.1;
                    pointerToSelectedObject.preUpdateScale.x -= 0.1;
                    scaleX.setText("X : " + pointerToSelectedObject.scale.x);
                    return false;
                }
            });
        View lessScaleY = parentActivity.findViewById(R.id.disminuirEscalaY);
            lessScaleY.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pointerToSelectedObject.scale.y -= 0.1;
                    pointerToSelectedObject.preUpdateScale.y -= 0.1;
                    scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
                    return false;
                }
            });
        View lessRotation = parentActivity.findViewById(R.id.disminuirRotacion);
            lessRotation.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pointerToSelectedObject.rotation -= 0.1;
                    pointerToSelectedObject.preUpdateRotation -= 0.1;
                    rotation.setText("" + pointerToSelectedObject.rotation);
                    return false;
                }
            });


        focusedByCamera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!buttonView.isPressed()) {
                    Toast.makeText(getContext(),"NO HE PULSADO",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isChecked){
                    pointerToSelectedObject.isFocusedByCamera = false;
                }else{
                    int auxID = pointerToSelectedObject.sceneHierarchyID;
                    for(int i = 0; i < theGameEngine.getObjectsInScene().size(); i ++){
                        if (i != auxID){
                            theGameEngine.getObjectsInScene().get(i).isFocusedByCamera = false;
                        }else{
                            pointerToSelectedObject.isFocusedByCamera = true;
                        }
                    }
                }

                Toast.makeText(getContext(),"" + pointerToSelectedObject.isFocusedByCamera,Toast.LENGTH_SHORT).show();

            }
        });
        os = new ObjectsInSceneAdapter(theGameEngine.getObjectsInScene());

        sa.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                theGameEngine.saveThisScene();
                theGameEngine.loadScene(sa.localDataSet.get(scenes.getChildAdapterPosition(v)));
                os.notifyDataSetChanged();


                if(theGameEngine.getObjectsInScene().size() != 0){
                    oc.localDataSet = theGameEngine.getObjectsInScene().get(0).components ;
                    oe.updateLocalDataSet(theGameEngine.getObjectsInScene().get(0).actionHolder);
                    pointerToSelectedObject =  theGameEngine.getObjectsInScene().get(0);
                    scaleX.setText("X : " + pointerToSelectedObject.scale.x);
                    scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
                    rotation.setText("" + pointerToSelectedObject.rotation);

                    focusedByCamera.setChecked(pointerToSelectedObject.isFocusedByCamera);
                    theGameEngine.camera.fixedLookingPosition.x = theGameEngine.getObjectsInScene().get(0).position.x;
                    theGameEngine.camera.fixedLookingPosition.y = theGameEngine.getObjectsInScene().get(0).position.y;
                    oe.notifyDataSetChanged();
                    oc.notifyDataSetChanged();
                }




            }
        });

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
                oe.updateLocalDataSet(theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).actionHolder);
                pointerToSelectedObject =  theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v));
                scaleX.setText("X : " + pointerToSelectedObject.scale.x);
                scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
                rotation.setText("" + pointerToSelectedObject.rotation);

                focusedByCamera.setChecked(pointerToSelectedObject.isFocusedByCamera);
                theGameEngine.camera.fixedLookingPosition.x = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.x;
                theGameEngine.camera.fixedLookingPosition.y = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.y;
                oe.notifyDataSetChanged();
                oc.notifyDataSetChanged();
            }
        });

        addRectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectObject.setVisibility(View.INVISIBLE);
                int id = theGameEngine.addGameObject(0,gameObjectTextViewName.getText().toString());
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);
                Toast.makeText(getContext(),"" + id,Toast.LENGTH_SHORT).show();

            }
        });

        addCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectObject.setVisibility(View.INVISIBLE);
                int id = theGameEngine.addGameObject(1,gameObjectTextViewName.getText().toString());
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);
                Toast.makeText(getContext(),"" + id,Toast.LENGTH_SHORT).show();
            }
        });

        addSprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectObject.setVisibility(View.INVISIBLE);
                int id = theGameEngine.addGameObject(2,gameObjectTextViewName.getText().toString());
                os.notifyItemInserted(theGameEngine.getObjectsInScene().size()-1);
                Toast.makeText(getContext(),"" + id,Toast.LENGTH_SHORT).show();
            }
        });

        objectsInScene.setAdapter(os);

        componentsInObject.setAdapter(oc);
        eventsInObject.setAdapter(oe);



        theGameEngine.isInEditor = true;




        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!theGameEngine.isInEditor){

                    if(!theGameEngine.isGameRunning){

                        //poner boton de reset

                        theGameEngine.isInEditor = true;
                        theGameEngine.loadScene();

                        if(theGameEngine.mode == 1){
                            inspector.setVisibility(View.VISIBLE);
                            ObjectHierarchy.setVisibility(View.VISIBLE);
                            saveInEditor.setVisibility(View.VISIBLE);
                            publishInProject.setVisibility(View.VISIBLE);
                        }


                            //pointerToSelectedObject = theGameEngine.getObjectsInScene().get(previousSelectedId);
                            oc.localDataSet = pointerToSelectedObject.components;
                            oe.updateLocalDataSet(pointerToSelectedObject.actionHolder);
                            oc.notifyDataSetChanged();
                            oe.notifyDataSetChanged();

                            focusedByCamera.setChecked(pointerToSelectedObject.isFocusedByCamera);
                            scaleX.setText("X : " + pointerToSelectedObject.scale.x);
                            scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
                            rotation.setText("" + pointerToSelectedObject.rotation);

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
                    //previousSelectedId = pointerToSelectedObject.sceneHierarchyID;
                    theGameEngine.saveThisScene();
                    inspector.setVisibility(View.INVISIBLE);
                    ObjectHierarchy.setVisibility(View.INVISIBLE);
                    saveInEditor.setVisibility(View.INVISIBLE);
                    publishInProject.setVisibility(View.INVISIBLE);

                    theGameEngine.isInEditor = false;

                }
                for(int i = 0; i < theGameEngine.getObjectsInScene().size();i++){
                    if(theGameEngine.getObjectsInScene().get(i).isFocusedByCamera == true){
                        theGameEngine.camera.lookingAt = theGameEngine.getObjectsInScene().get(i);
                        break;
                    }
                }
                theGameEngine.isGameRunning = true;

            }
        });
        theGameEngine.saveThisScene();
        theGameEngine.loadScene(sa.localDataSet.get(1));



        if(theGameEngine.mode == 0){
            parentActivity.findViewById(R.id.UI_CONTAINER).setVisibility(View.INVISIBLE);
            saveInEditor.setVisibility(View.INVISIBLE);
            publishInProject.setVisibility(View.INVISIBLE);

        }
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