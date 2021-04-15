package com.tfg.UIandroidGameEngine;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.MotionEvent;

import androidx.fragment.app.FragmentManager;

import com.google.android.gms.dynamic.IFragmentWrapper;

public class InputManager {

    public boolean isRightPressed = false;

    public boolean isLeftPressed = false;

    private int screenWidth = 0;

    private int screenHeight = 0;

    public  InputManager(){

    }
    public void setInputScreen(int width, int height){
        this.screenWidth = width;
        this.screenHeight = height;

    }
    public void processInput(MotionEvent e){
        boolean auxR = false;
        boolean auxL = false;
        if(e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE || e.getAction() == MotionEvent.ACTION_UP){
            for(int i = 0; i < e.getPointerCount(); i ++){
                if(e.getX(i) > screenWidth/2){
                    auxR = true;
                }else if(e.getX(i) < screenWidth/2 ){
                    auxL = true;
                }
            }

        }

        isRightPressed = auxR;
        isLeftPressed = auxL;

    }


}
