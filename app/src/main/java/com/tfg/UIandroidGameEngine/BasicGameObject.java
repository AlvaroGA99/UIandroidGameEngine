package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import java.util.ArrayList;

public  class BasicGameObject  {

        public Vector position;
        public Vector scale;
        public float rotation;
        public int sceneHierarchyID;


        private Sprite sprite;
        private ArrayList<Component> components = new ArrayList<Component>();


         public BasicGameObject(float posX, float posY, int spriteType){
             switch(spriteType){
                 case 0 :
                     sprite = new RectangleSprite();
                     break;
                 case 1:
                     sprite = new CircleSprite();
                     break;
                 case 2:
                     sprite = new BitmapSprite();
                     break;

             }
           position = new Vector(posX,posY);
            scale = new Vector(1,1);
            rotation = 0;
        }

        public  void  addComponent(Component toAdd){
            components.add(toAdd);
        }

        public void addComponent(String component){
             switch(component){
                 case "Component" :
                     components.add(new Component(this));
                     break;
             }
        }

        public String[] castObjectToDescription(){
             String aux = "";
             aux += "" + position.x + " " + position.y + " " + scale.x + " " + scale.y + " " + rotation;
             for(int i = 0; i < components.size(); i ++){
                 aux += " " + components.get(i).getClass().getName();
             }
             return aux.split(" ");
        }

        public void draw(Canvas renderCanvas){
            Paint aux = new Paint();
            aux.setARGB(255,255,0,0);

            renderCanvas.drawRect(position.x,position.y,position.x + 50, position.y + 50, aux);

        }

        public void update(){
             for (int i = 0; i < components.size(); i++){
                 components.get(i).process();
             }
        }

    }
