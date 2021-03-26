package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

import java.util.ArrayList;

public class GameEngine {

    private ArrayList<BasicGameObject> objectsInEngine = new ArrayList<BasicGameObject>();

    public GameEngine(){

    }

    public void addGameObject(BasicGameObject toAdd){
        objectsInEngine.add(toAdd);
    }

    public void drawAll(Canvas renderCanvas){
        for (int i = 0; i < objectsInEngine.size(); i ++){
            objectsInEngine.get(i).draw(renderCanvas);
        }
    }

}
