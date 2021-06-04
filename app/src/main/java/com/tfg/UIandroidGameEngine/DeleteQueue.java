package com.tfg.UIandroidGameEngine;

import java.util.ArrayList;

public class DeleteQueue extends ArrayList<BasicGameObject> {



    public DeleteQueue(){

    }


    public void deleteObjectsInQueue(GameEngine theGameEngine){
        for (int i = 0; i < size(); i++){
            theGameEngine.removeGameObject(get(i).sceneHierarchyID);

        }

    }



}
