package com.tfg.UIandroidGameEngine;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditorActivity extends AppCompatActivity   {

   public FragmentManager fm;
   public GameEngine theGameEngine = new GameEngine();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("users/admin123");
   private EditorGameSurfaceFragment f1 = EditorGameSurfaceFragment.newInstance(theGameEngine);
    private EditorUiFragment f2 = EditorUiFragment.newInstance(theGameEngine);
    public int mode ;
    public String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor/*new EditorGameView(this)*/);


        mode = getIntent().getIntExtra("MODE",1);
        key = getIntent().getStringExtra("KEY");
        if(theGameEngine.getObjectsInScene().size() == 0){
           // Toast.makeText(getApplicationContext(),"CReación", Toast.LENGTH_SHORT).show();
            theGameEngine.setContext(getApplicationContext());
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


            aux = new BasicGameObject((float)(getWidth())/2 + 1200, (float)(getHeight())/2 + 60,0,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Plataforma4",false);
            aux.scale.x = 10;
            aux.preUpdateScale.x = 10;
            aux.scale.y = 5;
            aux.preUpdateScale.y = 5;
            //aux.addComponent("GravityComponent");
            // aux.addComponent("GroundColliderComponent");
            aux.addComponent(new ColliderComponent(aux, theGameEngine));
            theGameEngine.addGameObject(aux);
        }
        if (mode == 0){

            theGameEngine.mode = mode;

        }
        //saveProject();
        //if (savedInstanceState == null) {
             fm = getSupportFragmentManager();


            fm.beginTransaction().add(R.id.editorContainer, f1).add(R.id.editorContainer, f2,null).commit();
        //}



        // View decorview = getWindow().getDecorView();
         //decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);




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