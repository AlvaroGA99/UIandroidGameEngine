package com.tfg.UIandroidGameEngine;

public class YAutoMovementComponent extends Component {
    public YAutoMovementComponent(BasicGameObject container) {
        super(container, "YAutoMovementComponent");
    }

    @Override
    public void process(long elapsedTime) {

        container.preUpdatePosition.y += elapsedTime*container.speed*container.invertMovementY;

    }
}
