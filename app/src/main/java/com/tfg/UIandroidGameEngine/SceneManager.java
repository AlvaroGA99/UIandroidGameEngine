package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SceneManager {



    public ArrayList<BasicGameObject> objectsInCurrentScene = new ArrayList<BasicGameObject>();

    public String currentScene;


    public ArrayList<Event> eventsTriggered = new ArrayList<Event>();

    public InputManager theInputManager = new InputManager();

    public Context ctx;




    public void addObjectToCurrentScene(BasicGameObject toAdd){
        objectsInCurrentScene.add(toAdd);
    }

    public SceneManager(){
        this.currentScene = "ScaffoldScene";
    }



    public void drawCurrentScene(Canvas renderCanvas,Camera camera){
        for (int i = 0; i < objectsInCurrentScene.size(); i ++){
            objectsInCurrentScene.get(i).draw(renderCanvas,camera);
        }
    }

    public void updateCurrentScene(long elapsedTime){


        // añadir eventos a eventsTriggered


        for (int i = 0; i < objectsInCurrentScene.size(); i++){
            // añadir a este objeto las collisiones : TODA LA LOGICA DEL QuadTree
            //añadir a este objeto los inputEvents
            objectsInCurrentScene.get(i).update(elapsedTime,eventsTriggered);
        }

        for (int i = 0; i < objectsInCurrentScene.size(); i++){
            objectsInCurrentScene.get(i).postUpdate();
        }

        eventsTriggered.clear();

    }

    private BasicGameObject castDescriptionToObject(String[] objectDescription){

        BasicGameObject aux = new BasicGameObject(Float.parseFloat(objectDescription[1]),Float.parseFloat(objectDescription[2]),Integer.parseInt(objectDescription[6]),theInputManager,ctx,objectDescription[0],Boolean.parseBoolean(objectDescription[7]));
        //BasicGameObject aux = new BasicGameObject(Float.parseFloat("1500.0") ,Float.parseFloat("1500.0"),0,theInputManager,ctx, objectDescription[0]);

      // Toast.makeText(ctx,objectDescription[6] , Toast.LENGTH_SHORT).show();
        //Toast.makeText(ctx,objectDescription[2] , Toast.LENGTH_SHORT).show();


        aux.scale = new Vector(Float.parseFloat(objectDescription[3]),Float.parseFloat(objectDescription[4]));
        aux.preUpdateScale = new Vector(Float.parseFloat(objectDescription[3]),Float.parseFloat(objectDescription[4]));
        aux.rotation = Float.parseFloat(objectDescription[5]);
        aux.preUpdateRotation = Float.parseFloat(objectDescription[5]);
        for (int i = 8; i < objectDescription.length; i++){
            aux.addComponent(objectDescription[i]);/////HSDFSDFSDFSDF/////
        }
        return aux;
    }

    public void loadScene(ArrayList<String[]> sceneObjects){




           objectsInCurrentScene.clear();
       for (int i = 0; i < sceneObjects.size();i++){

           BasicGameObject aux = castDescriptionToObject(sceneObjects.get(i));
           aux.sceneHierarchyID = objectsInCurrentScene.size();
           objectsInCurrentScene.add(aux);
       }
    }




}
