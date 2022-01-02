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

    public int originEditorScene = 0;

    public int mode = 1;

    public HashMap<String,ArrayList<String []>> SceneHierarchyDescription = new HashMap<String, ArrayList<String[]>>();

    public ArrayList<String> SceneList = new ArrayList<>();

    public Camera camera = new Camera();

    public ArrayList<BasicGameObject> deleteQueue= new ArrayList<BasicGameObject>();


    private SceneManager theSceneManager =  new SceneManager();

    public GameEngine(){
        //collisionQuadTree = new QuadTree(50,50, getObjectsInScene());

        //addScene("Scene 1");
        //addScene("ScaffoldScene2");

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
       // Toast.makeText(ctx, "" + toAdd.actionHolder.collisionActions.size() , Toast.LENGTH_SHORT).show();
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
            SceneList.add(key);
        }
    }

    public void updateAll(long elapsedTime){
        theSceneManager.updateCurrentScene(elapsedTime);
        deleteObjectsInQueue();
    }

    public void loadScene(int key){
        deleteQueue.clear();
        camera.lookingAt = null;
        ArrayList<String[]> aux = SceneHierarchyDescription.get(SceneList.get(key));
        theSceneManager.loadScene(aux);
        String[] auxSplit ;
        for (int i = 0; i < getObjectsInScene().size();i++){

            for (int j = 10; j < aux.get(i).length; j++){
                auxSplit = aux.get(i)[j].split("/");
                switch(auxSplit[0]){
                    case "InputMovementPlatformerComponent" :
                        getObjectsInScene().get(i).addComponent(new InputMovementPlatformerComponent(getObjectsInScene().get(i)));
                        break;
                    case "GravityComponent" :
                        getObjectsInScene().get(i).addComponent(new GravityComponent(getObjectsInScene().get(i)));
                        break;
                    case "GroundColliderComponent" :
                        getObjectsInScene().get(i).addComponent(new GroundColliderComponent(getObjectsInScene().get(i)));
                        break;
                    case "JumpComponent":
                        getObjectsInScene().get(i).addComponent(new JumpComponent(getObjectsInScene().get(i)));
                        break;
                    case "XAutoMovementComponent":
                        getObjectsInScene().get(i).addComponent(new XAutoMovementComponent(getObjectsInScene().get(i)));
                        break;

                    case "YAutoMovementComponent" :
                        getObjectsInScene().get(i).addComponent(new YAutoMovementComponent(getObjectsInScene().get(i)));
                        break;
                    case "DragableComponent":
                        getObjectsInScene().get(i).addComponent(new DragableComponent(getObjectsInScene().get(i),this));
                        break;
                    case "OnClickEvent" :
                        switch (auxSplit[1]){
                            case "DebugAction":
                                getObjectsInScene().get(i).actionHolder.onClickActions.add(new DebugAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementXAction" :
                                getObjectsInScene().get(i).actionHolder.onClickActions.add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                                break;
                            case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new NextSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                                break;
                            case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetXAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new ResetXAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetYAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new ResetYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeXAction": getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                                break;

                        }
                        break;
                    case "OnCollisionEvent" :
                        switch(auxSplit[2]){
                            case "DebugAction":
                                getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new DebugAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementXAction" :
                                getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                                break;
                            case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new NextSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                                break;
                            case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetXAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new ResetXAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetYAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new ResetYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeXAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                                break;
                        }
                        break;
                    case "OnEachSecondEvent":
                        //
                        switch (auxSplit[1]){
                            case "DebugAction":
                                getObjectsInScene().get(i).actionHolder.updateActions.add(new DebugAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementXAction" :
                                getObjectsInScene().get(i).actionHolder.updateActions.add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                                break;
                            case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new NextSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                                break;
                            case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetXAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new ResetXAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetYAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new ResetYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeXAction": getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                                break;
                        }
                        break;
                    case "OnStartSceneAction" :
                        //
                        switch (auxSplit[1]){
                            case "DebugAction":
                                getObjectsInScene().get(i).actionHolder.startSceneActions.add(new DebugAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementXAction" :
                                getObjectsInScene().get(i).actionHolder.startSceneActions.add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                                break;
                            case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new NextSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                                break;
                            case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetXAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new ResetXAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetYAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new ResetYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeXAction": getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                                break;

                        }
                        break;
                }
            }
        }
            theSceneManager.currentScene = key;
    }



    public void loadScene(){
        deleteQueue.clear();
        camera.lookingAt = null;
        ArrayList<String[]> aux = SceneHierarchyDescription.get(SceneList.get(originEditorScene));
        theSceneManager.loadScene(aux);
        String[] auxSplit ;
        for (int i = 0; i < getObjectsInScene().size();i++){

            for (int j = 10; j < aux.get(i).length; j++){
                auxSplit = aux.get(i)[j].split("/");
               switch(auxSplit[0]){
                    case "InputMovementPlatformerComponent" :
                        getObjectsInScene().get(i).addComponent(new InputMovementPlatformerComponent(getObjectsInScene().get(i)));
                        break;
                    case "GravityComponent" :
                        getObjectsInScene().get(i).addComponent(new GravityComponent(getObjectsInScene().get(i)));
                        break;
                    case "GroundColliderComponent" :
                        getObjectsInScene().get(i).addComponent(new GroundColliderComponent(getObjectsInScene().get(i)));
                        break;
                    case "JumpComponent":
                        getObjectsInScene().get(i).addComponent(new JumpComponent(getObjectsInScene().get(i)));
                        break;
                    case "ColliderComponent" :
                        getObjectsInScene().get(i).addComponent(new ColliderComponent(getObjectsInScene().get(i),this));
                        break;
                   case "XAutoMovementComponent":
                       getObjectsInScene().get(i).addComponent(new XAutoMovementComponent(getObjectsInScene().get(i)));
                       break;

                   case "YAutoMovementComponent" :
                       getObjectsInScene().get(i).addComponent(new YAutoMovementComponent(getObjectsInScene().get(i)));
                       break;
                   case "DragableComponent":
                       getObjectsInScene().get(i).addComponent(new DragableComponent(getObjectsInScene().get(i),this));
                       break;
                    case "OnClickEvent" :
                        switch (auxSplit[1]){
                            case "DebugAction":
                                getObjectsInScene().get(i).actionHolder.onClickActions.add(new DebugAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementXAction" :
                                getObjectsInScene().get(i).actionHolder.onClickActions.add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                                break;
                            case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                                break;
                            case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new NextSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                                break;
                            case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                                break;
                            case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetXAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new ResetXAction(getObjectsInScene().get(i),this));
                                break;
                            case "ResetYAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new ResetYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeXAction": getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                                break;
                            case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.onClickActions.add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                                break;

                        }
                       break;
                   case "OnCollisionEvent" :
                       switch(auxSplit[2]){
                           case "DebugAction":
                               getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new DebugAction(getObjectsInScene().get(i),this));
                               break;
                           case "InvertMovementXAction" :
                               getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                               break;
                           case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                               break;
                           case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new NextSceneAction(getObjectsInScene().get(i),this));
                               break;
                           case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                               break;
                           case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                               break;
                           case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                               break;
                           case "ResetXAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new ResetXAction(getObjectsInScene().get(i),this));
                               break;
                           case "ResetYAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new ResetYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveNegativeXAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                               break;
                       }
                       break;
                   case "OnEachSecondEvent":
                       //
                       switch (auxSplit[1]){
                           case "DebugAction":
                               getObjectsInScene().get(i).actionHolder.updateActions.add(new DebugAction(getObjectsInScene().get(i),this));
                               break;
                           case "InvertMovementXAction" :
                               getObjectsInScene().get(i).actionHolder.updateActions.add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                               break;
                           case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                               break;
                           case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new NextSceneAction(getObjectsInScene().get(i),this));
                               break;
                           case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                               break;
                           case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                               break;
                           case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                               break;
                           case "ResetXAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new ResetXAction(getObjectsInScene().get(i),this));
                               break;
                           case "ResetYAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new ResetYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveNegativeXAction": getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.updateActions.add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.updateActions.add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                               break;
                       }
                       break;
                   case "OnStartSceneEvent" :
                       //
                       switch (auxSplit[1]){
                           case "DebugAction":
                               getObjectsInScene().get(i).actionHolder.startSceneActions.add(new DebugAction(getObjectsInScene().get(i),this));
                               break;
                           case "InvertMovementXAction" :
                               getObjectsInScene().get(i).actionHolder.startSceneActions.add(new InvertMovementXAction(getObjectsInScene().get(i),this));
                               break;
                           case "InvertMovementYAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new InvertMovementYAction(getObjectsInScene().get(i),this));
                               break;
                           case "NextSceneAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new NextSceneAction(getObjectsInScene().get(i),this));
                               break;
                           case "PlayMusicLoopAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new PlayMusicLoopAction(getObjectsInScene().get(i),this));
                               break;
                           case "PlayMusicOnceAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new PlayMusicOnceAction(getObjectsInScene().get(i),this));
                               break;
                           case "PreviousSceneAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new PreviousSceneAction(getObjectsInScene().get(i),this));
                               break;
                           case "ResetXAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new ResetXAction(getObjectsInScene().get(i),this));
                               break;
                           case "ResetYAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new ResetYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveNegativeXAction": getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveNegativeXAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveNegativeYAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveNegativeYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveXAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveXAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetAutoMoveYAction": getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetAutoMoveYAction(getObjectsInScene().get(i),this));
                               break;
                           case "SetFollowCameraAction":  getObjectsInScene().get(i).actionHolder.startSceneActions.add(new SetFollowCameraAction(getObjectsInScene().get(i),this));
                               break;

                       }
                       break;
                }
            }
        }
        theSceneManager.currentScene = originEditorScene;
    }

    public void setContext(Context context){
        this.ctx = context;
        this.theSceneManager.ctx = context;
    }

    public void saveThisScene(){
        SceneHierarchyDescription.get(SceneList.get(theSceneManager.currentScene)).clear();

        for(int i = 0; i < getObjectsInScene().size(); i ++){
            SceneHierarchyDescription.get(SceneList.get(theSceneManager.currentScene)).add(getObjectsInScene().get(i).castObjectToDescription());


        }
    }

    public void saveThatScene(String key, ArrayList<String[]> value){
        if(SceneHierarchyDescription.containsKey(key)){
            SceneHierarchyDescription.put(key,value);
        }
    }

    public int getCurrentScene(){
        return theSceneManager.currentScene;
    }

    public void playGame(){

       isGameRunning = true;
    }

    public void pause_restartGame(){
            isGameRunning = false; //pause
            //change pause button for restart button

    }



}
