package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameEngine {

    public Context ctx;

    //public QuadTree collisionQuadTree;

    public Vector lastTouched = new Vector(0.0f,0.0f);

    public boolean isGameRunning = false;

    public boolean isInEditor = true;

    public HashMap<String,ArrayList<String []>> SceneHierarchyDescription = new HashMap<String, ArrayList<String[]>>();

    public Camera camera = new Camera();

    public ArrayList<BasicGameObject> deleteQueue= new ArrayList<BasicGameObject>();


    private SceneManager theSceneManager =  new SceneManager();

    public GameEngine(){
        //collisionQuadTree = new QuadTree(50,50, getObjectsInScene());
        addScene(theSceneManager.currentScene);


    }

    public InputManager getTheInputManager(){
        return theSceneManager.theInputManager;
    }



    public void addGameObject(BasicGameObject toAdd){
        toAdd.sceneHierarchyID = getObjectsInScene().size();
        theSceneManager.addObjectToCurrentScene(toAdd);
        for(int i = 0; i < getObjectsInScene().size(); i ++ ){

            if(toAdd.sceneHierarchyID != i){
                getObjectsInScene().get(i).actionHolder.collisionActions.add(new ArrayList<Action>());
            }else {
                for(int j = 0; j <= toAdd.sceneHierarchyID; j++){
                    toAdd.actionHolder.collisionActions.add(new ArrayList<Action>());
                }
            }

        }

        //SceneHierarchyDescription.get(theSceneManager.currentScene).add(toAdd.castObjectToDescription());

    }

    public void removeGameObject(int id){


        getObjectsInScene().remove(id);

        for (int i = 0; i < getObjectsInScene().size();i ++){
            getObjectsInScene().get(i).actionHolder.collisionActions.remove(id);
            getObjectsInScene().get(i).sceneHierarchyID = i;
        }
    }

    public ArrayList<BasicGameObject> getObjectsInScene(){
       return theSceneManager.objectsInCurrentScene;
    }

    public int addGameObject(int spriteType,String name){
        BasicGameObject toAdd;

             toAdd = new BasicGameObject(theSceneManager.theInputManager.screenWidth/2 + 100,theSceneManager.theInputManager.screenHeight/2,spriteType,this.getTheInputManager(),ctx,name,false);


        //toAdd.sceneHierarchyID = SceneHierarchyDescription.get(theSceneManager.currentScene).size();
        toAdd.sceneHierarchyID = getObjectsInScene().size();
        theSceneManager.addObjectToCurrentScene(toAdd);
        for(int i = 0; i < getObjectsInScene().size(); i ++ ){

            if(toAdd.sceneHierarchyID != i){
                getObjectsInScene().get(i).actionHolder.collisionActions.add(new ArrayList<Action>());
            }else {
                for(int j = 0; j <= toAdd.sceneHierarchyID; j++){
                    toAdd.actionHolder.collisionActions.add(new ArrayList<Action>());
                }
            }

        }
        Toast.makeText(ctx, "" + toAdd.actionHolder.collisionActions.size() , Toast.LENGTH_SHORT).show();
        //SceneHierarchyDescription.get(theSceneManager.currentScene).add(toAdd.castObjectToDescription());


        return toAdd.sceneHierarchyID;
    }


    private void deleteObjectsInQueue(){
        for (int i = 0; i < deleteQueue.size(); i++){
            removeGameObject(deleteQueue.get(i).sceneHierarchyID);

        }
        deleteQueue.clear();
    }

    public void addObjectToDeleteQueue(BasicGameObject toDelete){
        for (int i = 0; i < getObjectsInScene().size(); i++){
            if(toDelete.sceneHierarchyID == getObjectsInScene().get(i).sceneHierarchyID){
                return;
            }
            deleteQueue.add(toDelete);
        }
    }
    public void drawAll(Canvas renderCanvas){
       theSceneManager.drawCurrentScene(renderCanvas,camera);
    }

    public void addScene(String key){
        if(!SceneHierarchyDescription.containsKey(key)){
            SceneHierarchyDescription.put(key, new ArrayList<String[]>());
        }
    }

    public void updateAll(long elapsedTime){
        theSceneManager.updateCurrentScene(elapsedTime);
        deleteObjectsInQueue();
    }

    public void loadScene(String key){

            theSceneManager.loadScene(SceneHierarchyDescription.get(key));

    }

    public void loadScene(){
        camera.lookingAt = null;
        theSceneManager.loadScene(SceneHierarchyDescription.get(theSceneManager.currentScene));

    }

    public void setContext(Context context){
        this.ctx = context;
        this.theSceneManager.ctx = context;
    }

    public void saveThisScene(){
        SceneHierarchyDescription.get(theSceneManager.currentScene).clear();

        for(int i = 0; i < getObjectsInScene().size(); i ++){
            SceneHierarchyDescription.get(theSceneManager.currentScene).add(getObjectsInScene().get(i).castObjectToDescription());


        }
    }

    public void playGame(){

       isGameRunning = true;
    }

    public void pause_restartGame(){
            isGameRunning = false; //pause
            //change pause button for restart button

    }



}
