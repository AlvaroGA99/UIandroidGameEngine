package com.tfg.UIandroidGameEngine;

public class Collision  implements Event{


    private int eventTrigger;

    public void Collision(int eventTrigger){
        this.eventTrigger = eventTrigger;
    }

    @Override
    public void dispatchEvent(BasicGameObject container) {
        for (int i = 0; i < container.actionHolder.collisionActions.get(eventTrigger).size(); i ++){
            container.actionHolder.collisionActions.get(eventTrigger).get(i).execute(eventTrigger);
        }
    }
}
