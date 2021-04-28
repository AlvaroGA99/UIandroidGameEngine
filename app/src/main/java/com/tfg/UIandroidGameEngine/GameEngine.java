package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameEngine {

    public Context ctx;

    public  int WIDTH;

    public  int HEIGHT;


    public boolean isGameRunning = false;

    private HashMap<String,ArrayList<String []>> SceneHierarchyDescription = new HashMap<String, ArrayList<String[]>>();

    private SceneManager theSceneManager =  new SceneManager(ctx);

    public GameEngine(){
        addScene(theSceneManager.currentScene);


    }

    public InputManager getTheInputManager(){
        return theSceneManager.theInputManager;
    }

    public void addGameObject(BasicGameObject toAdd){
        toAdd.sceneHierarchyID = SceneHierarchyDescription.get(theSceneManager.currentScene).size();
        SceneHierarchyDescription.get(theSceneManager.currentScene).add(toAdd.castObjectToDescription());
        theSceneManager.addObjectToCurrentScene(toAdd);
    }

    public ArrayList<BasicGameObject> getObjectsInScene(){
       return theSceneManager.objectsInCurrentScene;
    }

    public int addGameObject(){
        BasicGameObject toAdd;

             toAdd = new BasicGameObject(theSceneManager.theInputManager.screenWidth/2 + 100,theSceneManager.theInputManager.screenHeight/2,0,this.getTheInputManager(),ctx);


        toAdd.addComponent("GravityComponent");
        toAdd.sceneHierarchyID = SceneHierarchyDescription.get(theSceneManager.currentScene).size();
        SceneHierarchyDescription.get(theSceneManager.currentScene).add(toAdd.castObjectToDescription());
        theSceneManager.addObjectToCurrentScene(toAdd);

        return toAdd.sceneHierarchyID;
    }


    public void drawAll(Canvas renderCanvas){
       theSceneManager.drawCurrentScene(renderCanvas);
    }

    public void addScene(String key){
        if(!SceneHierarchyDescription.containsKey(key)){
            SceneHierarchyDescription.put(key, new ArrayList<String[]>());
        }
    }

    public void updateAll(long elapsedTime){
        theSceneManager.updateCurrentScene(elapsedTime);
    }

    public void loadScene(String key){

            theSceneManager.loadScene(SceneHierarchyDescription.get(key));

    }

    public void playGame(){
       isGameRunning = true;
    }

    public void pause_restartGame(){
        if(isGameRunning){
            isGameRunning = false; //pause
            //change pause button for restart button
        }else{
            //restart : replace all gameobjects to its original position
        }
    }

}
