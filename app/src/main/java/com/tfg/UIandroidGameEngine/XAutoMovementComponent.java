package com.tfg.UIandroidGameEngine;

public class XAutoMovementComponent extends  Component{
    public XAutoMovementComponent(BasicGameObject container) {
        super(container, "XAutoMovementComponent");
    }

    @Override
    public void process(long elapsedTime) {

        container.preUpdatePosition.x += elapsedTime*container.speedx*container.invertMovementX;
    }
}
