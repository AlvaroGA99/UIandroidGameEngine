package com.tfg.UIandroidGameEngine;

public class Collision  implements Event{


    public BasicGameObject eventTrigger;

    public  Collision(BasicGameObject eventTrigger){
        this.eventTrigger = eventTrigger;
    }

    @Override
    public void dispatchEvent(BasicGameObject container) {
        for (int i = 0; i < container.actionHolder.collisionActions.get(eventTrigger.sceneHierarchyID).size(); i ++){
            container.actionHolder.collisionActions.get(eventTrigger.sceneHierarchyID).get(i).execute(eventTrigger.sceneHierarchyID);
        }
    }
}
