package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashMap;

public class GameEngine {




    private HashMap<String,ArrayList<String []>> SceneHierarchyDescription = new HashMap<String, ArrayList<String[]>>();

    private SceneManager theSceneManager =  new SceneManager();

    public GameEngine(){
        addScene(theSceneManager.currentScene);
    }

    public void addGameObject(BasicGameObject toAdd){
        toAdd.sceneHierarchyID = SceneHierarchyDescription.get(theSceneManager.currentScene).size();
        SceneHierarchyDescription.get(theSceneManager.currentScene).add(toAdd.castObjectToDescription());
        theSceneManager.addObjectToCurrentScene(toAdd);
    }

    public void drawAll(Canvas renderCanvas){
       theSceneManager.drawCurrentScene(renderCanvas);
    }

    public void addScene(String key){
        if(!SceneHierarchyDescription.containsKey(key)){
            SceneHierarchyDescription.put(key, new ArrayList<String[]>());
        }
    }

    public void updateAll(){
        theSceneManager.updateCurrentScene();
    }

    public void loadScene(String key){

            theSceneManager.loadScene(SceneHierarchyDescription.get(key));

    }

}
