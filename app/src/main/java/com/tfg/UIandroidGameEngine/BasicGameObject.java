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

        public Vector preUpdatePosition;
        public Vector preUpdateScale;
        public float preUpdateRotation;

        public InputManager inputManager;

        public float speed ;

        public int sceneHierarchyID;

        private int spriteType;
        private Sprite sprite;

        public ArrayList<Component> components = new ArrayList<Component>();
        public ArrayList<Event> eventsReceived = new ArrayList<Event>();
        public ActionHolder actionHolder = new ActionHolder();
        //referencias


         public BasicGameObject(float posX, float posY, int spriteType, InputManager inputmanager, Context ctx)  {
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
                 aux += " " + components.get(i).name;
             }
             return aux.split(" ");
        }

        public void draw(Canvas renderCanvas,Camera camera){
           sprite.draw( position,  scale,  rotation,renderCanvas, camera);

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
