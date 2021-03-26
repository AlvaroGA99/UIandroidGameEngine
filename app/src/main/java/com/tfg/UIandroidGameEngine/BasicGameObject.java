package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public  class BasicGameObject {

        public Vector position;
        public Vector scale;
        public float rotation;


        private ArrayList<Component> components = new ArrayList<Component>();


         public BasicGameObject(float posX, float posY){
           position = new Vector(posX,posY);
            scale = new Vector(1,1);
            rotation = 0;
        }

        public void addComponent(Component toAdd){
            components.add(toAdd);
        }

        public void draw(Canvas renderCanvas){
            Paint aux = new Paint();
            aux.setARGB(255,255,0,0);
            renderCanvas.drawRect(position.x,position.y,position.x + 50, position.y + 50, aux);

        }

    }
