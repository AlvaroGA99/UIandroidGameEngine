package com.tfg.UIandroidGameEngine;

import java.util.ArrayList;

public class QuadTree {

    private Vector leftTop;

    private Vector rightBottom;

    private ArrayList<BasicGameObject> objectsInQuad;

    private QuadTree[] children = new QuadTree[4] ;

    public QuadTree (float width, float height, ArrayList<BasicGameObject> objectsInScene){
       // this.width = width;
        //this.height = width;
        this.objectsInQuad = objectsInScene;
    }

    public void checkCollisionsInQuad(){

    }

    public void checkSplit(){
        if(objectsInQuad.size() >= 16){
            for(int i = 0; i < 4; i++){
                //children[i] = new QuadTree(leftTop,new Vector((leftTop.x + rightBottom.x)/2,rightBottom.y),)
            }
        }
    }

}
