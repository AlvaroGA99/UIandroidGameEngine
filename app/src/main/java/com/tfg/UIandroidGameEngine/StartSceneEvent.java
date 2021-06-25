package com.tfg.UIandroidGameEngine;

public class StartSceneEvent implements Event {

    public void StartSceneEvent(){

    }


    @Override
    public void dispatchEvent(BasicGameObject container) {
        for(int i = 0; i < container.actionHolder.startSceneActions.size(); i ++){
            container.actionHolder.startSceneActions.get(i).execute(-1);
        }
    }
}
