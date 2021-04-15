package com.tfg.UIandroidGameEngine;

import android.app.Activity;
import android.view.MotionEvent;

public class InputMovementPlatformerComponent extends Component {



    public InputMovementPlatformerComponent(BasicGameObject container){
        super(container);

    }

    @Override
    public void process(long elapsedTime){
        //if(im.getAction() == MotionEvent.ACTION_DOWN){
           //for(int i = 0; i <  im.getPointerCount(); i ++){
               // if(im.getX(i) >  GameEngine.WIDTH/2){
        if(container.inputManager.isLeftPressed){
            container.preUpdatePosition.x -= elapsedTime*0.5 ;
        }


        if(container.inputManager.isRightPressed){
            container.preUpdatePosition.x += elapsedTime*0.5;
        }

                //}else{
                  //  container.preUpdatePosition.x -= elapsedTime*0.2;
                //}
            //}
       // }else if(im.getAction() == MotionEvent.ACTION_UP){

        //}

    }
}
