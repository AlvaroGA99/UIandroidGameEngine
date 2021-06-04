package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.appcompat.widget.VectorEnabledTintResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public  class BasicGameObject  {

        public Vector position;
        public Vector scale;
        public float rotation;

        public boolean isFocusedByCamera ;

        public Vector preUpdatePosition;
        public Vector preUpdateScale;
        public float preUpdateRotation;

        public String name = "";

        public InputManager inputManager;

        public float speed ;

        public int sceneHierarchyID;

        private int spriteType;
        private Sprite sprite;

        public ArrayList<Component> components = new ArrayList<Component>();
        public ArrayList<Event> inputEventsReceived = new ArrayList<Event>();
        public ArrayList<Collision> collisionsReceived = new ArrayList<Collision>();
        public ActionHolder actionHolder = new ActionHolder();
        //referencias


         public BasicGameObject(float posX, float posY, int spriteType, InputManager inputmanager, Context ctx,String name,boolean isFocusedByCamera)  {
             this.name = name;
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
                     sprite = new BitmapSprite(ctx);
                     break;

             }
            speed = 0;
            this.isFocusedByCamera = isFocusedByCamera;
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
             aux += "" + name + "_"+ position.x + "_" + position.y + "_" + scale.x + "_" + scale.y + "_" + rotation + "_" + spriteType + "_" + isFocusedByCamera;
             for(int i = 0; i < components.size(); i ++){
                 aux += "_" + components.get(i).name;
             }
             return aux.split("_");
        }

        public void draw(Canvas renderCanvas,Camera camera){
           sprite.draw( position,  scale,  rotation,renderCanvas, camera);

        }

        public void update(long elapsedTime,ArrayList<Event> eventsTriggered){
             for (int i = 0; i < components.size(); i++){
                 components.get(i).process(elapsedTime);

             }

            for (int j = 0; j < eventsTriggered.size(); j ++){
                eventsTriggered.get(j).dispatchEvent(this);
            }

            for (int k = 0; k < inputEventsReceived.size(); k ++){
                inputEventsReceived.get(k).dispatchEvent(this);
            }

            inputEventsReceived.clear();

            for (int l = 0; l < collisionsReceived.size(); l++){
               collisionsReceived.get(l).dispatchEvent(this);
            }

            collisionsReceived.clear();

        }

        public void postUpdate(){
             scale.x = preUpdateScale.x;
            scale.y = preUpdateScale.y;

            position.x = preUpdatePosition.x;
            position.y = preUpdatePosition.y;

            rotation = preUpdateRotation;
        }


    }
