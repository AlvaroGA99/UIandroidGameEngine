package com.tfg.UIandroidGameEngine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class EditorActivity extends AppCompatActivity   {

   public FragmentManager fm;
   public DatabaseReference db ;
   public GameEngine theGameEngine = new GameEngine();
   private EditorGameSurfaceFragment f1 = EditorGameSurfaceFragment.newInstance(theGameEngine);
    private EditorUiFragment f2 = EditorUiFragment.newInstance(theGameEngine);
    public int mode ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor/*new EditorGameView(this)*/);
        db = FirebaseDatabase.getInstance().getReference();
        mode = getIntent().getIntExtra("MODE",1);
        if(theGameEngine.getObjectsInScene().size() == 0){
           // Toast.makeText(getApplicationContext(),"CReación", Toast.LENGTH_SHORT).show();
            theGameEngine.setContext(getApplicationContext());
            BasicGameObject aux = new BasicGameObject((float)(getWidth())/2, (float)(getHeight())/2,0, theGameEngine.getTheInputManager(),theGameEngine.ctx,"Objeto1",false);

            aux.addComponent("GravityComponent");
            aux.addComponent("GroundColliderComponent");
            aux.preUpdateRotation = 45.0f;
            aux.rotation = 45.0f;
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth())/2 + 100, (float)(getHeight())/2,0,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Objeto2",false);
            aux.addComponent("GravityComponent");
            aux.addComponent("GroundColliderComponent");
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth())/2, (float)(getHeight())/2,2,theGameEngine.getTheInputManager(),theGameEngine.ctx,"Player",true);
            aux.actionHolder.onClickActions.add(new DebugAction(aux,theGameEngine));

            aux.preUpdateScale.x = 3;
            aux.scale.x = 3;
            aux.preUpdateScale.y = 3;
            aux.scale.y = 3;
            aux.addComponent("InputMovementPlatformerComponent");
            aux.addComponent("GravityComponent");
            aux.addComponent("GroundColliderComponent");
            aux.preUpdateRotation = 45.0f;
            aux.rotation = 45.0f;
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

    public void saveProject(){
            //Button save = (Button) findViewById();
           // save.setClickable(false);
            //findViewById() ponemos a false
            db.child("users/admin123/idofproject1").child("scenes").setValue(theGameEngine.SceneHierarchyDescription).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        db.child("published").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
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