package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashMap;

public class GameEngine {



    private HashMap<String,ArrayList<BasicGameObject>> SceneHierarchyDescription = new HashMap<>();

    private SceneManager theSceneManager = new SceneManager();

    public GameEngine(){

    }

    public void addGameObject(BasicGameObject toAdd){
        theSceneManager.addObjectToCurrentScene(toAdd);
    }

    public void drawAll(Canvas renderCanvas){
       theSceneManager.drawCurrentScene(renderCanvas);
    }

    public void loadScene(String key){
        if(!SceneHierarchyDescription.containsKey(key)){
            theSceneManager.loadScene(SceneHierarchyDescription.get(key));
        }
    }

}
