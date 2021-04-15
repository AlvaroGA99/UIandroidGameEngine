package com.tfg.UIandroidGameEngine;

public class GravityComponent extends Component{

    private float speed = 0;

    public GravityComponent(BasicGameObject container) {
        super(container);
    }

    @Override
    public void process(long elapsedTime){

        speed += elapsedTime;
        container.preUpdatePosition.y += speed*elapsedTime*0.001;

    }
}
