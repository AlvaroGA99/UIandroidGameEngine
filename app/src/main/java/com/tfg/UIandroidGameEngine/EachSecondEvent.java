package com.tfg.UIandroidGameEngine;

public class EachSecondEvent implements  Event {

    public EachSecondEvent(){

    }

    @Override
    public void dispatchEvent(BasicGameObject container) {
        for(int i = 0; i < container.actionHolder.updateActions.size(); i ++){
            container.actionHolder.updateActions.get(i).execute(-1);
        }
    }
}
