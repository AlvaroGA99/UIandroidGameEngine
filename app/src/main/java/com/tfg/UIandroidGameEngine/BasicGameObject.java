package com.tfg.UIandroidGameEngine;

import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.widget.VectorEnabledTintResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public  class BasicGameObject  {

        public Vector position;
        public Vector scale;
        public float rotation;

        public Vector startPosition;
        int invertMovementX;
        short invertMovementY;

        public boolean isFocusedByCamera ;

        public Vector preUpdatePosition;
        public Vector preUpdateScale;
        public float preUpdateRotation;

        public String name = "";

        public InputManager inputManager;

        public float speed ;
        public float speedx;

        public int sceneHierarchyID;

        public int spriteType;
        public int spriteNumber;
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
             invertMovementX = 1;
             invertMovementY = 1;
             startPosition = new Vector(0.0f,0.0f);
            speed = 0;
            this.isFocusedByCamera = isFocusedByCamera;
           position = new Vector(posX,posY);
            scale = new Vector(1,1);
            rotation = 0;

             preUpdatePosition = new Vector(posX,posY);
             preUpdateRotation = 0f;
             preUpdateScale = new Vector(1,1);
        }
       public BasicGameObject(){

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
                 case "JumpComponent":
                     components.add(new JumpComponent(this));
                     break;
             }
        }

        public String[] castObjectToDescription(){
             String aux = "";
             aux += "" + name + "_"+ position.x + "_" + position.y + "_" + scale.x + "_" + scale.y + "_" + rotation + "_" + spriteType + "_" + spriteNumber +"_" +  isFocusedByCamera;
                aux += "_" + actionHolder.collisionActions.size();
             for(int i = 0; i < components.size(); i ++){
                 aux += "_" + components.get(i).name;
             }

             //ACCIONES ONCLICK
             for(int i = 0;i < actionHolder.onClickActions.size(); i ++){

                 aux += "_OnClickEvent/" + actionHolder.onClickActions.get(i).name;

             }
             //A

            for(int i = 0;i < actionHolder.startSceneActions.size(); i ++){

                aux += "_OnStartSceneEvent/" + actionHolder.startSceneActions.get(i).name;

            }

            for(int i = 0;i < actionHolder.updateActions.size(); i ++){

                aux += "_OnEachSecondEvent/" + actionHolder.updateActions.get(i).name;

            }


            for(int i = 0;i < actionHolder.collisionActions.size(); i ++){
                for(int j = 0; j < actionHolder.collisionActions.get(i).size(); j ++){

                    aux += "_OnCollisionEvent/" + i + "/" + actionHolder.collisionActions.get(i).get(j).name;

                }


            }

             return aux.split("_");
        }

    public void addActionHolderFromDescription(String[] objectDescription, GameEngine theGameEngine){

             String[] auxSplit;

             for (int i = 8 + components.size() - 1; i < objectDescription.length; i ++){
                 auxSplit = objectDescription[i].split("/");
                 switch(auxSplit[0]){
                     case "OnClickEvent" : actionHolder.onClickActions.add(castNameToAction(auxSplit[1],theGameEngine));
                     break;
                     case "OnCollisionEvent" : actionHolder.collisionActions.get(Integer.parseInt(auxSplit[1])).add(castNameToAction(auxSplit[2],theGameEngine));
                 }
             }

    }

    public Action castNameToAction(String name,GameEngine theGameEngine){

        switch(name){


            case "DebugAction": return new DebugAction(this, theGameEngine);


        }

        return new DebugAction(this,theGameEngine);

    }


    public void draw(Canvas renderCanvas,Camera camera){
           sprite.draw( position,  scale,  rotation,renderCanvas, camera);

        }

        public void update(long elapsedTime,ArrayList<Event> eventsTriggered){
             for (int i = 0; i < components.size(); i++){
                 components.get(i).process(elapsedTime);

             }

            for (int j = 0; j < eventsTriggered.size(); j ++){
                //if(eventsTriggered.size() == 1)
                //System.exit(-1);
                eventsTriggered.get(j).dispatchEvent(this);
            }

            int auxSize = inputEventsReceived.size();
            for (int k = 0; k < auxSize; k ++){
                //Execute and remove fiorst element to avoid arraayList dinamic size conflicts

                //System.exit(-1);
                if(inputEventsReceived.size() != 0){
                    inputEventsReceived.get(0).dispatchEvent(this);
                    inputEventsReceived.remove(0);
                }

            }


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

        public void nextSprite(int length){
             if(spriteNumber == length - 1){
                 spriteNumber = 0;
             }else{
                 spriteNumber += 1;
             }

            sprite.loadSprite(spriteNumber);
        }

        public void previousSprite(int length){
             if(spriteNumber == 0){
                 spriteNumber = length - 1;
             }else{
                 spriteNumber-=1;
             }

             sprite.loadSprite(spriteNumber);
        }

        public void reloadSprite(){
             sprite.loadSprite(spriteNumber);
        }

    }
