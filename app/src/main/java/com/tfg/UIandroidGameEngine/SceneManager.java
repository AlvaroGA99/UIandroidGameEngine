package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

import java.util.ArrayList;

public class SceneManager {



    private ArrayList<BasicGameObject> objectsInCurrentScene = new ArrayList<BasicGameObject>();

    public String currentScene;

    public void addObjectToCurrentScene(BasicGameObject toAdd){
        objectsInCurrentScene.add(toAdd);
    }

    public SceneManager(){
        this.currentScene = "ScaffoldScene";
    }
    public void drawCurrentScene(Canvas renderCanvas){
        for (int i = 0; i < objectsInCurrentScene.size(); i ++){
            objectsInCurrentScene.get(i).draw(renderCanvas);
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
        BasicGameObject aux = new BasicGameObject(Float.parseFloat(objectDescription[0]),Float.parseFloat(objectDescription[1]),Integer.parseInt(objectDescription[2]));
        aux.scale = new Vector(Float.parseFloat(objectDescription[3]),Float.parseFloat(objectDescription[4]));
        aux.rotation = Float.parseFloat(objectDescription[5]);
        for (int i = 6; i < objectDescription.length; i++){
            aux.addComponent(objectDescription[i]);
        }
        return aux;
    }

    public void loadScene(ArrayList<String[]> sceneObjects){
       objectsInCurrentScene.clear();
       for (int i = 0; i < sceneObjects.size();i++){
            objectsInCurrentScene.add(castDescriptionToObject(sceneObjects.get(i)));
       }
    }


}
