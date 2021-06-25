package com.tfg.UIandroidGameEngine;

import android.view.View;

public class OnClickEvent implements Event{

   public OnClickEvent(){

   }


    @Override
    public void dispatchEvent(BasicGameObject container) {
        for(int i = 0; i < container.actionHolder.onClickActions.size(); i++){
            //System.exit(-1);
            container.actionHolder.onClickActions.get(i).execute(-1);
        }
    }
}
