package com.tfg.UIandroidGameEngine;

public class JumpComponent extends  Component {
    public JumpComponent(BasicGameObject container) {
        super(container, "JumpComponent");
    }

    @Override
    public void process(long elapsedTime) {
        if(container.inputManager.isUpPressed){
            container.preUpdatePosition.y -= elapsedTime*1;

        }
    }
}
