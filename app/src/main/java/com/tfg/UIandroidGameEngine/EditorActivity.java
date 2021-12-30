package com.tfg.UIandroidGameEngine;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity   {
    private int currentApiVersion;
   public FragmentManager fm;
   public GameEngine theGameEngine = new GameEngine();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef ;
   private EditorGameSurfaceFragment f1 = EditorGameSurfaceFragment.newInstance(theGameEngine);
    private EditorUiFragment f2 = EditorUiFragment.newInstance(theGameEngine);
    public int mode ;
    public String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor/*new EditorGameView(this)*/);
        key = getIntent().getStringExtra("KEY");
        myRef = database.getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/projects/" + key/*getIntent().getStringExtra("KEY")*/);
        mode = getIntent().getIntExtra("MODE",1);
        Toast.makeText(getApplicationContext(),"" + mode,Toast.LENGTH_SHORT).show();
        theGameEngine.mode = mode;
        theGameEngine.setContext(getApplicationContext());
        if(theGameEngine.getObjectsInScene().size() == 0){

            if (mode == 0 || mode == 1){



                ArrayList<String> SceneList = new ArrayList<>();
                ArrayList<String> auxObjectDescriptionList = new ArrayList<>();


                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot a : snapshot.child("scenes").getChildren()){

                            SceneList.add(a.getKey());
                            theGameEngine.addScene(a.getKey());
                            auxObjectDescriptionList.clear();

                            for(DataSnapshot c : a.getChildren()){
                                auxObjectDescriptionList.add(c.getValue(String.class));
                            }

                            for(int i = 0; i < auxObjectDescriptionList.size(); i ++){
                                //auxObjectList.add(auxObjectDescriptionList.get(i).split("_"));
                                theGameEngine.SceneHierarchyDescription.get(a.getKey()).add(auxObjectDescriptionList.get(i).split("_"));
                            }

                            // theGameEngine.SceneHierarchyDescription.put(a.getKey(), auxObjectList);*/
                        }


                       // if(SceneList.size() > 0){theGameEngine.loadScene(0);}
                       // Toast.makeText(EditorActivity.this, "" + SceneList.size() , Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(EditorActivity.this, "ERROR AL REALIZAR LA CONSULTAAAA" , Toast.LENGTH_SHORT).show();
                    }
                });


            }else{


/*


           // Toast.makeText(getApplicationContext(),"CReación", Toast.LENGTH_SHORT).show();*/



            switch (mode){
                case 2:
                    BasicGameObject aux = new BasicGameObject(-300,250,2, theGameEngine.getTheInputManager(),theGameEngine.ctx,"Jugador",false);
                    aux.addComponent("GravityComponent");
                    aux.addComponent("InputMovementPlatformerComponent");
                    aux.addComponent("JumpComponent");
                    theGameEngine.addGameObject(aux);
                     aux = new BasicGameObject(-300,300,0, theGameEngine.getTheInputManager(),theGameEngine.ctx,"Plataforma 1",false);
                    aux.scale.x = 5;
                    aux.preUpdateScale.x = 5;
                    aux.addComponent(new ColliderComponent(aux,theGameEngine));
                    theGameEngine.addGameObject(aux);
                     aux = new BasicGameObject(350, 100,0, theGameEngine.getTheInputManager(),theGameEngine.ctx,"Plataforma 2",false);
                    aux.scale.x = 5;
                    aux.preUpdateScale.x = 5;
                    aux.addComponent(new ColliderComponent(aux,theGameEngine));
                     theGameEngine.addGameObject(aux);

                     aux = new BasicGameObject(300,14,1, theGameEngine.getTheInputManager(),theGameEngine.ctx,"Meta",false);

                    theGameEngine.addGameObject(aux);
                    break;
                case 3:
                    break;
            }

            /*

            BasicGameObject aux = new BasicGameObject((float)(getWidth())/2, (float)(getHeight())/2,0, theGameEngine.getTheInputManager(),theGameEngine.ctx,"Meta",false);
            //aux.addComponent("GravityComponent");
            //aux.addComponent("InputMovementPlatformerComponent");
            //aux.addComponent("GroundColliderComponent");
            aux.preUpdateRotation = 45.0f;
            aux.rotation = 45.0f;
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth())/2 + 100, (float)(getHeight())/2,0,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Plataforma1",false);
            aux.scale.x = 10;
            aux.preUpdateScale.x = 10;
            aux.scale.y = 5;
            aux.preUpdateScale.y = 5;
            //aux.addComponent("GravityComponent");
           // aux.addComponent("GroundColliderComponent");
            aux.addComponent(new ColliderComponent(aux, theGameEngine));
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth())/2, (float)(getHeight())/2 + 100,2,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Player",true);
            aux.actionHolder.onClickActions.add(new DebugAction(aux,theGameEngine));

            //Toast.makeText(getApplicationContext(),"Acciones par el 1 : " + aux.actionHolder.collisionActions.size(),Toast.LENGTH_SHORT).show();
            aux.preUpdateScale.x = 3;
            aux.scale.x = 3;
            aux.preUpdateScale.y = 3;
            aux.scale.y = 3;
            aux.addComponent("InputMovementPlatformerComponent");
           aux.addComponent("GravityComponent");
            //aux.addComponent("GroundColliderComponent");
             aux.addComponent("JumpComponent");

            aux.preUpdateRotation = 45.0f;
            aux.rotation = 45.0f;
            theGameEngine.addGameObject(aux);
            aux.actionHolder.collisionActions.get(0).add(new DebugAction(aux,theGameEngine));



            aux = new BasicGameObject((float)(getWidth())/2 + 300, (float)(getHeight())/2 + 10,0,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Plataforma2",false);
            aux.scale.x = 10;
            aux.preUpdateScale.x = 10;
            aux.scale.y = 5;
            aux.preUpdateScale.y = 5;
            //aux.addComponent("GravityComponent");
            // aux.addComponent("GroundColliderComponent");
            aux.addComponent(new ColliderComponent(aux, theGameEngine));
            theGameEngine.addGameObject(aux);


            aux = new BasicGameObject((float)(getWidth())/2 + 800, (float)(getHeight())/2 + 40 ,0,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Plataforma3",false);
            aux.scale.x = 10;
            aux.preUpdateScale.x = 10;
            aux.scale.y = 5;
            aux.preUpdateScale.y = 5;
            //aux.addComponent("GravityComponent");
            // aux.addComponent("GroundColliderComponent");
            aux.addComponent(new ColliderComponent(aux, theGameEngine));
            theGameEngine.addGameObject(aux);
*/
/*
            BasicGameObject aux = new BasicGameObject((float)(getWidth())/2 + 1200, (float)(getHeight())/2 + 60,0,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Plataforma4",false);
            aux.scale.x = 10;
            aux.preUpdateScale.x = 10;
            aux.scale.y = 5;
            aux.preUpdateScale.y = 5;
            //aux.addComponent("GravityComponent");
            // aux.addComponent("GroundColliderComponent");
            aux.addComponent(new ColliderComponent(aux, theGameEngine));
            theGameEngine.addGameObject(aux);
*/          }



        }




        //saveProject();
        //if (savedInstanceState == null) {
             fm = getSupportFragmentManager();


            fm.beginTransaction().add(R.id.editorContainer, f1).add(R.id.editorContainer, f2,null).commit();
        //}



        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }





    public int getHeight(){

        DisplayMetrics dp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dp);
        return dp.heightPixels;
    }


    public  int getWidth(){

        DisplayMetrics dp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dp);
        return dp.widthPixels;
    }


    @Override
    public void onStart() {

        super.onStart();


    }

    public void saveProject(){

            //Button save = (Button) findViewById();
           // save.setClickable(false);
            //findViewById() ponemos a false
            myRef.child("users/admin123/idofproject1").child("scenes").setValue(theGameEngine.SceneHierarchyDescription).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //save.setClickable(true);
                    Toast.makeText(getApplicationContext(),"Guardado con éxito",Toast.LENGTH_SHORT);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //save.setClickable(true);
                    Toast.makeText(getApplicationContext(),"No se ha podido guardar, intentalo de nuevo",Toast.LENGTH_SHORT);
                }
            });

    }
    public void publishProject(){
        //Button publish = (Button) findViewById();
        //publish.setClickable(false);
        myRef.child("published").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               // publish.setClickable(true);
                Toast.makeText(getApplicationContext(),"Publicado con éxito",Toast.LENGTH_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //publish.setClickable(true);
                Toast.makeText(getApplicationContext(),"No se ha podido publicar, intentalo de nuevo",Toast.LENGTH_SHORT);
            }
        });
    }


}