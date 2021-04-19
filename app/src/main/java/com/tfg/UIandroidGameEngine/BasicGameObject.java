package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.appcompat.widget.VectorEnabledTintResources;

import java.util.ArrayList;

public  class BasicGameObject  {

        public Vector position;
        public Vector scale;
        public float rotation;

        public Vector preUpdatePosition;
        public Vector preUpdateScale;
        public float preUpdateRotation;

        public InputManager inputManager;

        public int sceneHierarchyID;

        private int spriteType;
        private Sprite sprite;
        private ArrayList<Component> components = new ArrayList<Component>();


         public BasicGameObject(float posX, float posY, int spriteType, InputManager inputmanager){
             this.inputManager = inputmanager;
             this.spriteType = spriteType;
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

             preUpdatePosition = new Vector(posX,posY);
             preUpdateRotation = 0f;
             preUpdateScale = new Vector(1,1);
        }

        public  void  addComponent(Component toAdd){
            components.add(toAdd);
        }

        public void addComponent(String component){
             switch(component){
                 case "InputMovementPlatformerComponent" :
                     components.add(new InputMovementPlatformerComponent(this));
                     break;
                 case "GravityComponent" :
                     components.add(new GravityComponent(this));
                     break;
                 case "GroundColliderComponent" :
                     components.add(new GroundColliderComponent(this));
                     break;
             }
        }

        public String[] castObjectToDescription(){
             String aux = "";
             aux += "" + position.x + " " + position.y + " " + scale.x + " " + scale.y + " " + rotation + " " + spriteType;
             for(int i = 0; i < components.size(); i ++){
                 aux += " " + components.get(i).getClass().getName();
             }
             return aux.split(" ");
        }

        public void draw(Canvas renderCanvas){
           sprite.draw( position,  scale,  rotation,renderCanvas);

        }

        public void update(long elapsedTime){
             for (int i = 0; i < components.size(); i++){
                 components.get(i).process(elapsedTime);
             }
        }

        public void postUpdate(){
             scale.x = preUpdateScale.x;
            scale.y = preUpdateScale.y;

            position.x = preUpdatePosition.x;
            position.y = preUpdatePosition.y;

            rotation = preUpdateRotation;
        }


    }
