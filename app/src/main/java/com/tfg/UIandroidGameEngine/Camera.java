package com.tfg.UIandroidGameEngine;

public class Camera {

    public BasicGameObject lookingAt;

    public Vector fixedLookingPosition;

    private Vector cameraOffset;

    private Vector screenCenter;

    public Camera(){

        this.cameraOffset = new Vector(0.0f,0.0f);
        this.screenCenter = new Vector(0.0f,0.0f);
        this.fixedLookingPosition = new Vector(0.0f,0.0f);
    }

    public void lookAt(BasicGameObject lookAt){
        this.lookingAt = lookAt;
    }

    public void setCameraCenter(float x,float y){
        this.screenCenter.x = x;
        this.screenCenter.y = y;
    }

    public float getScreenSpaceX(){

        if(lookingAt != null){

            return screenCenter.x - cameraOffset.x - lookingAt.position.x;
        }else{
            return screenCenter.x - cameraOffset.x - fixedLookingPosition.x;
        }

    }

    public float getScreenSpaceY(){
        if(lookingAt != null){

            return screenCenter.y - cameraOffset.y - lookingAt.position.y;
        }else{
            return screenCenter.y - cameraOffset.y - fixedLookingPosition.y;
        }    }
}
