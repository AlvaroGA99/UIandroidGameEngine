package com.tfg.UIandroidGameEngine;

public class GroundColliderComponent extends Component{

    public GroundColliderComponent(BasicGameObject container){
        super(container,"GroundColliderComponent");
    }

    @Override
    public void process(long elapsedTime){
        if(container.position.y > (container.inputManager.screenHeight/5)*4){
            container.preUpdatePosition.y =  (container.inputManager.screenHeight/5)*4;
            container.speed = 0;
        }
    }
}
