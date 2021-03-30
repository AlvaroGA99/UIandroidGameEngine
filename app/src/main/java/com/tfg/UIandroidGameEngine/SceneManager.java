package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

import java.util.ArrayList;

public class SceneManager {

    private ArrayList<BasicGameObject> objectsInCurrentScene;

    public void addObjectToCurrentScene(BasicGameObject toAdd){
        objectsInCurrentScene.add(toAdd);
    }

    public SceneManager(){

    }
    public void drawCurrentScene(Canvas renderCanvas){
        for (int i = 0; i < objectsInCurrentScene.size(); i ++){
            objectsInCurrentScene.get(i).draw(renderCanvas);
        }
    }

    public void loadScene(ArrayList<BasicGameObject> sceneObjects){
        objectsInCurrentScene =  sceneObjects;
    }


}
