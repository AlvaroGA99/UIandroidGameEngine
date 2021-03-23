package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

import java.util.ArrayList;

public class GameEngine {

    private ArrayList<GameObject> objectsInEngine = new ArrayList<GameObject>();

    public GameEngine(){

    }

    public void addGameObject(GameObject toAdd){
        objectsInEngine.add(toAdd);
    }

    public void drawAll(Canvas renderCanvas){
        for (int i = 0; i < objectsInEngine.size(); i ++){
            objectsInEngine.get(i).draw(renderCanvas);
        }
    }

}
