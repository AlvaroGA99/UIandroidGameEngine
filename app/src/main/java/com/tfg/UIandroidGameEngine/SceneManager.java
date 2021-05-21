package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SceneManager {



    public ArrayList<BasicGameObject> objectsInCurrentScene = new ArrayList<BasicGameObject>();

    public String currentScene;




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
        for (int i = 0; i < objectsInCurrentScene.size(); i++){
            objectsInCurrentScene.get(i).update(elapsedTime);
        }
        for (int i = 0; i < objectsInCurrentScene.size(); i++){
            objectsInCurrentScene.get(i).postUpdate();
        }
    }

    private BasicGameObject castDescriptionToObject(String[] objectDescription){
        BasicGameObject aux = new BasicGameObject(Float.parseFloat(objectDescription[0]),Float.parseFloat(objectDescription[1]),Integer.parseInt(objectDescription[5]),theInputManager,ctx);
        aux.scale = new Vector(Float.parseFloat(objectDescription[2]),Float.parseFloat(objectDescription[3]));
        aux.rotation = Float.parseFloat(objectDescription[4]);
        for (int i = 6; i < objectDescription.length; i++){
            aux.addComponent(objectDescription[i]);
        }
        return aux;
    }

    public void loadScene(ArrayList<String[]> sceneObjects){


        Toast.makeText(ctx,"" + "SODLFHS" , Toast.LENGTH_SHORT).show();

           objectsInCurrentScene.clear();
       for (int i = 0; i < sceneObjects.size();i++){
           BasicGameObject aux = castDescriptionToObject(sceneObjects.get(i));
           aux.sceneHierarchyID = objectsInCurrentScene.size();
           objectsInCurrentScene.add(aux);
       }
    }




}
