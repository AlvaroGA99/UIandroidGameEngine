package com.tfg.UIandroidGameEngine;

public class ColliderComponent extends  Component{

    private GameEngine theGameEngine;

    public ColliderComponent(BasicGameObject container, GameEngine theGameEngine) {
        super(container, "ColliderComponent");
        this.theGameEngine = theGameEngine;
    }

    @Override
    public void process(long elapsedTime) {
        for (int i = 0; i < container.collisionsReceived.size(); i++){
            theGameEngine.getObjectsInScene().get(container.collisionsReceived.get(i).eventTrigger.sceneHierarchyID).speed = 0;
            theGameEngine.getObjectsInScene().get(container.collisionsReceived.get(i).eventTrigger.sceneHierarchyID).speed -= elapsedTime;
            //theGameEngine.getObjectsInScene().get(container.collisionsReceived.get(i).eventTrigger).preUpdatePosition.y -= elapsedTime*theGameEngine.getObjectsInScene().get(container.collisionsReceived.get(i).eventTrigger).speed*0.001;

            //System.exit(-1);

        }


    }
}
