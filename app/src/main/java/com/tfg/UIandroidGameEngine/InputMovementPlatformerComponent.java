package com.tfg.UIandroidGameEngine;

import android.app.Activity;
import android.view.MotionEvent;

public class InputMovementPlatformerComponent extends Component {



    public InputMovementPlatformerComponent(BasicGameObject container){
        super(container,"InputMovementPlatformerComponent");

    }

    @Override
    public void process(long elapsedTime){
        //if(im.getAction() == MotionEvent.ACTION_DOWN){
           //for(int i = 0; i <  im.getPointerCount(); i ++){
               // if(im.getX(i) >  GameEngine.WIDTH/2){
        container.speedx = 0;
        if(container.inputManager.isLeftPressed){
            container.speedx = -0.5f;
        }


        if(container.inputManager.isRightPressed){
            container.speedx = 0.5f;
        }

        container.preUpdatePosition.x += elapsedTime*container.speedx*container.invertMovementX;

                //}else{
                  //  container.preUpdatePosition.x -= elapsedTime*0.2;
                //}
            //}
       // }else if(im.getAction() == MotionEvent.ACTION_UP){

        //}

    }
}
