package com.tfg.UIandroidGameEngine;

public class EachSecondEvent implements  Event {

    public EachSecondEvent(){

    }

    @Override
    public void dispatchEvent(BasicGameObject container) {
        for(int i = 0; i < container.actionHolder.startSceneActions.size(); i ++){
            container.actionHolder.startSceneActions.get(i).execute(-1);
        }
    }
}
