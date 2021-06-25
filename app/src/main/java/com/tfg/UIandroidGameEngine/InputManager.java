package com.tfg.UIandroidGameEngine;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.MotionEvent;

import androidx.fragment.app.FragmentManager;

import com.google.android.gms.dynamic.IFragmentWrapper;

public class InputManager {

    public boolean isRightPressed = false;

    public boolean isLeftPressed = false;

    public boolean isUpPressed = false;

    public int screenWidth = 0;

    public int screenHeight = 0;

    public  InputManager(){

    }
    public void setInputScreen(int width, int height){
        this.screenWidth = width;
        this.screenHeight = height;

    }
    public void processInput(MotionEvent e){
        boolean auxR = false;
        boolean auxL = false;
        boolean auxU = false;
        if(e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE ){
            for(int i = 0; i < e.getPointerCount(); i ++){

                if(e.getY(i) < screenHeight/2){
                    auxU = true;
                }

                if(e.getX(i) > screenWidth/2){
                    auxR = true;
                }else if(e.getX(i) < screenWidth/2 ){
                    auxL = true;
                }
            }

        }

        isRightPressed = auxR;
        isLeftPressed = auxL;
        isUpPressed = auxU;

    }


}
