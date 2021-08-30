package com.tfg.UIandroidGameEngine;

import android.bluetooth.BluetoothClass;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private CollisionListAdapter ca;
    private RecyclerView objectsInScene;
    private RecyclerView componentsInObject;
    private RecyclerView eventsInObject;
    private RecyclerView scenes;
    private RecyclerView collisionList;
    private View selectComponent;
    private View selectObject;
    private View selectEvent;
    private View selectAction;
    private Switch focusedByCamera;
    private  Button deleteSelectedObject;
    private TextView objectNameInInspector;
    private Button addComponent ;
    private Button addEvent ;

    private String selectedEvent;

    private int previousSelectedId;
     private TextView scaleX;
     private TextView scaleY;
     private TextView rotation;

     private String eventSelected = "";
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
         addComponent = (Button)parentActivity.findViewById(R.id.addComponent);
         addEvent = (Button) parentActivity.findViewById(R.id.addEvent);
        Button addObject = (Button) parentActivity.findViewById(R.id.addObject);
        Button publishInProject = (Button) parentActivity.findViewById(R.id.publishInEditor);
        Button saveInEditor = (Button) parentActivity.findViewById(R.id.saveInEditor);
        Button addScene = (Button) parentActivity.findViewById(R.id.addScene);


        int[] backgrounds = {R.drawable.background1};
        int[] sprites = {R.drawable.sprite};
        int[] colors = {R.drawable.roundborder_yellow_drawable,R.drawable.roundborder_black_drawable,R.drawable.roundborder_red_drawable,R.drawable.roundborder_drawable};

        int bgIndex;
        int spriteIndex;

        View onClickEvent ;

        Button addRectangle = (Button)parentActivity.findViewById(R.id.addRectangle);;
        Button addCircle = (Button)parentActivity.findViewById(R.id.addCircle);
        Button addSprite = (Button)parentActivity.findViewById(R.id.addSprite);
        TextInputEditText gameObjectTextViewName = (TextInputEditText) parentActivity.findViewById(R.id.gameObjectTextView);

         objectNameInInspector = (TextView) parentActivity.findViewById(R.id.nameOfObjectInInspector);


        selectComponent = parentActivity.findViewById(R.id.selectComponentToAdd);
        selectObject = parentActivity.findViewById(R.id.selectObjectToAdd);
        selectEvent = parentActivity.findViewById(R.id.selectEventToAdd);

        saveInEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theGameEngine.saveThisScene();
                EditorActivity a = (EditorActivity) getActivity();
                a.myRef.child(a.key).child("project_type").setValue("Platformero");
                a.myRef.child(a.key).child("title").setValue("testProject");
                a.myRef.child(a.key).child("scenes").removeValue();
                //HashMap aux = new HashMap<String, ArrayList<ArrayList<String>>>();
                ArrayList<String> auxlist = new ArrayList<>();


                for(String key : theGameEngine.SceneHierarchyDescription.keySet()){
                    auxlist.clear();
                    for(String[] objectDescription : theGameEngine.SceneHierarchyDescription.get(key)){
                        String aux = "";
                        for(int i = 0; i < objectDescription.length; i++){
                            aux += objectDescription[i] + "_";
                        }
                        auxlist.add(aux);

                    }
                    a.myRef.child(a.key).child("scenes").child(key).setValue(auxlist);

                    auxlist.clear();
                }

                Toast.makeText(getContext(), "GUARDAR", Toast.LENGTH_SHORT).show();
            }
        });





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

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEvent.setVisibility(View.VISIBLE);
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
        collisionList = (RecyclerView) parentActivity.findViewById(R.id.collisionsList);
         componentsInObject = (RecyclerView) parentActivity.findViewById(R.id.componentsInObject);
         eventsInObject = (RecyclerView) parentActivity.findViewById(R.id.eventsInObject);
         scenes = (RecyclerView) parentActivity.findViewById(R.id.scenes);
        objectsInScene.setLayoutManager(new LinearLayoutManager(getActivity()));
        componentsInObject.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventsInObject.setLayoutManager(new LinearLayoutManager(getActivity()));
        scenes.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        collisionList.setLayoutManager(new LinearLayoutManager(getActivity()));


        sa = new SceneListAdapter(theGameEngine.SceneHierarchyDescription);

        scenes.setAdapter(sa);

        addScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theGameEngine.SceneHierarchyDescription.put("Escena" + theGameEngine.SceneHierarchyDescription.keySet().size(),new ArrayList<String[]>());
                sa.updateDataSet(theGameEngine.SceneHierarchyDescription);
            }
        });



        pointerToSelectedObject = theGameEngine.getObjectsInScene().get(0);
        objectNameInInspector.setText(pointerToSelectedObject.name);
         deleteSelectedObject = (Button) parentActivity.findViewById(R.id.deleteSelectedObject);
        deleteSelectedObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theGameEngine.removeGameObject(pointerToSelectedObject.sceneHierarchyID);
                os.notifyItemRemoved(pointerToSelectedObject.sceneHierarchyID);
                ca.updateDataSet(theGameEngine.getObjectsInScene(),pointerToSelectedObject.sceneHierarchyID);
                hideInspectorElements();

            }
        });

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

        ca = new CollisionListAdapter(theGameEngine.getObjectsInScene(),pointerToSelectedObject.sceneHierarchyID);
        collisionList.setAdapter(ca);

        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // pointerToSelectedObject.actionHolder.collisionActions.get(ca.localDataSet.get(collisionList.getChildAdapterPosition(v)).sceneHierarchyID);
                //ca.updateDataSet(theGameEngine.getObjectsInScene(),pointerToSelectedObject.sceneHierarchyID);
                oe.updateLocalDataSet(pointerToSelectedObject.actionHolder);
                selectedEvent = "OnCollision_"+ ca.localDataSet.get(collisionList.getChildAdapterPosition(v)).sceneHierarchyID;
                selectEvent.setVisibility(View.INVISIBLE);
            }
        });

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
        os = new ObjectsInSceneAdapter(theGameEngine.getObjectsInScene(),theGameEngine);

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

       setupComponentButtons();
       setupEventsButtons();
       setupActionButtons();

        os.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                oc.localDataSet = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).components ;
                oe.updateLocalDataSet(theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).actionHolder);
                pointerToSelectedObject =  theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v));
                objectNameInInspector.setText(pointerToSelectedObject.name);
                scaleX.setText("X : " + pointerToSelectedObject.scale.x);
                scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
                rotation.setText("" + pointerToSelectedObject.rotation);

                focusedByCamera.setChecked(pointerToSelectedObject.isFocusedByCamera);
                theGameEngine.camera.fixedLookingPosition.x = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.x;
                theGameEngine.camera.fixedLookingPosition.y = theGameEngine.getObjectsInScene().get(objectsInScene.getChildAdapterPosition(v)).position.y;
                oe.notifyDataSetChanged();
                oc.notifyDataSetChanged();
                showInspectorElements();
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

                        if(theGameEngine.getObjectsInScene().size() != 0){
                            pointerToSelectedObject = theGameEngine.getObjectsInScene().get(pointerToSelectedObject.sceneHierarchyID);

                            oc.localDataSet = pointerToSelectedObject.components ;
                            oe.updateLocalDataSet(pointerToSelectedObject.actionHolder);
                            objectNameInInspector.setText(pointerToSelectedObject.name);
                            scaleX.setText("X : " + pointerToSelectedObject.scale.x);
                            scaleY.setText("Y : " + pointerToSelectedObject.scale.y);
                            rotation.setText("" + pointerToSelectedObject.rotation);

                            focusedByCamera.setChecked(pointerToSelectedObject.isFocusedByCamera);
                            theGameEngine.camera.fixedLookingPosition.x = pointerToSelectedObject.position.x;
                            theGameEngine.camera.fixedLookingPosition.y = pointerToSelectedObject.position.y;
                            oe.notifyDataSetChanged();
                            oc.notifyDataSetChanged();

                        }



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
        //theGameEngine.saveThisScene();
        //theGameEngine.loadScene(sa.localDataSet.get(1));



        if(theGameEngine.mode == 0){
            parentActivity.findViewById(R.id.UI_CONTAINER).setVisibility(View.INVISIBLE);
            saveInEditor.setVisibility(View.INVISIBLE);
            publishInProject.setVisibility(View.INVISIBLE);

        }
    }
    private void hideInspectorElements(){
        deleteSelectedObject.setVisibility(View.INVISIBLE);
        objectNameInInspector.setVisibility(View.INVISIBLE);
        componentsInObject.setVisibility(View.INVISIBLE);
        eventsInObject.setVisibility(View.INVISIBLE);
        addComponent.setVisibility(View.INVISIBLE);
        addEvent.setVisibility(View.INVISIBLE);
        focusedByCamera.setVisibility(View.INVISIBLE);

        rotation.setVisibility(View.INVISIBLE);
        scaleX.setVisibility(View.INVISIBLE);
        scaleY.setVisibility(View.INVISIBLE);

        parentActivity.findViewById(R.id.aumentarRotacion).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.disminuirRotacion).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.aumentarEscalaY).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.disminuirEscalaY).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.aumentarEscalaX).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.disminuirEscalaX).setVisibility(View.INVISIBLE);

        parentActivity.findViewById(R.id.Componentes).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.eventos).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.Escala).setVisibility(View.INVISIBLE);
        parentActivity.findViewById(R.id.Rotacion).setVisibility(View.INVISIBLE);
    }

    private void showInspectorElements(){
        deleteSelectedObject.setVisibility(View.VISIBLE);
        objectNameInInspector.setVisibility(View.VISIBLE);
        componentsInObject.setVisibility(View.VISIBLE);
        eventsInObject.setVisibility(View.VISIBLE);
        addComponent.setVisibility(View.VISIBLE);
        addEvent.setVisibility(View.VISIBLE);
        focusedByCamera.setVisibility(View.VISIBLE);

        rotation.setVisibility(View.VISIBLE);
        scaleX.setVisibility(View.VISIBLE);
        scaleY.setVisibility(View.VISIBLE);

        parentActivity.findViewById(R.id.aumentarRotacion).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.disminuirRotacion).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.aumentarEscalaY).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.disminuirEscalaY).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.aumentarEscalaX).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.disminuirEscalaX).setVisibility(View.VISIBLE);

        parentActivity.findViewById(R.id.Componentes).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.eventos).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.Escala).setVisibility(View.VISIBLE);
        parentActivity.findViewById(R.id.Rotacion).setVisibility(View.VISIBLE);
    }

    private void setupComponentButtons(){
        View gravityComponent = parentActivity.findViewById(R.id.gravityComponent);
        View inputMovementPlatformerComponent = parentActivity.findViewById(R.id.inputMovementPlatformerComponent);
        View groundCollider = parentActivity.findViewById(R.id.groundColliderComponent);
        View jumpComponent = parentActivity.findViewById(R.id.jumpComponent);;
        View dragableComponent = parentActivity.findViewById(R.id.dragableComponent);
        View xAutoMovementComponent= parentActivity.findViewById(R.id.xAutoMovementComponent);
        View yAutoMovementComponent = parentActivity.findViewById(R.id.yAutoMovementComponent);
        View colliderComponent = parentActivity.findViewById(R.id.colliderComponent);

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


        jumpComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "JumpComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new JumpComponent(pointerToSelectedObject));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });


        dragableComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "DragableComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new DragableComponent(pointerToSelectedObject));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });


        colliderComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "ColliderComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new ColliderComponent(pointerToSelectedObject,theGameEngine));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });


        xAutoMovementComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "XAutoMovementComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new XAutoMovementComponent(pointerToSelectedObject));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });


        yAutoMovementComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectComponent.setVisibility(View.INVISIBLE);
                boolean found = false;
                for(int i = 0; i < oc.localDataSet.size(); i ++){
                    if (oc.localDataSet.get(i).name == "YAutoMovementComponent"){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    oc.localDataSet.add(new YAutoMovementComponent(pointerToSelectedObject));
                    oc.notifyItemInserted(oc.localDataSet.size() - 1);
                }
            }
        });

    }

    private void setupEventsButtons(){

        View onStartEvent = parentActivity.findViewById(R.id.OnStartSceneEvent);
        View onEachSecondEvent= parentActivity.findViewById(R.id.OnEachSecondEvent);
        View onClickEvent= parentActivity.findViewById(R.id.OnClickEvent);
        selectAction = parentActivity.findViewById(R.id.selectActionToAdd);

        onStartEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedEvent = "OnStartScene";
                selectEvent.setVisibility(View.INVISIBLE);
                selectAction.setVisibility(View.VISIBLE);
            }
        });

        onEachSecondEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedEvent = "OnEachSecondEvent";
                selectEvent.setVisibility(View.INVISIBLE);
                selectAction.setVisibility(View.VISIBLE);
            }
        });

        onClickEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedEvent = "OnClickEvent";
                selectEvent.setVisibility(View.INVISIBLE);
                selectAction.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupActionButtons(){

        View debugAction = parentActivity.findViewById(R.id.DebugAction);
        debugAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] aux = selectedEvent.split("_");
                boolean found = false;
                switch(aux[0]){
                    case "OnCollision":
                        for (int i = 0; i <pointerToSelectedObject.actionHolder.collisionActions.get(Integer.parseInt(aux[1])).size();i ++){
                            if(pointerToSelectedObject.actionHolder.collisionActions.get(Integer.parseInt(aux[1])).get(i).equals("DebugAction")){

                                found = true;
                                break;

                            }
                        }
                        if(!found){
                            pointerToSelectedObject.actionHolder.collisionActions.get(Integer.parseInt(aux[1])).add(new DebugAction(pointerToSelectedObject,theGameEngine));
                        }
                        found = false;

                        break;
                    case "OnStartScene":
                        for (int i = 0; i <pointerToSelectedObject.actionHolder.startSceneActions.size();i ++){
                            if(pointerToSelectedObject.actionHolder.startSceneActions.get(i).equals("DebugAction")){

                                found = true;
                                break;

                            }
                        }
                        if(!found){
                            pointerToSelectedObject.actionHolder.startSceneActions.add(new DebugAction(pointerToSelectedObject,theGameEngine));
                        }
                        found = false;
                        break;



                    case "OnClickEvent":
                        for (int i = 0; i <pointerToSelectedObject.actionHolder.onClickActions.size();i ++){
                            if(pointerToSelectedObject.actionHolder.onClickActions.get(i).equals("DebugAction")){

                                found = true;
                                break;

                            }
                        }
                        if(!found){
                            pointerToSelectedObject.actionHolder.onClickActions.add(new DebugAction(pointerToSelectedObject,theGameEngine));
                        }
                        found = false;
                        break;
                    case "OnEachSecondEvent":
                        for (int i = 0; i <pointerToSelectedObject.actionHolder.updateActions.size();i ++){
                            if(pointerToSelectedObject.actionHolder.updateActions.get(i).equals("DebugAction")){

                                found = true;
                                break;

                            }
                        }
                        if(!found){
                            pointerToSelectedObject.actionHolder.updateActions.add(new DebugAction(pointerToSelectedObject,theGameEngine));
                        }
                        found = false;
                        break;
                }
                selectAction.setVisibility(View.INVISIBLE);
                oe.updateLocalDataSet(pointerToSelectedObject.actionHolder);
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