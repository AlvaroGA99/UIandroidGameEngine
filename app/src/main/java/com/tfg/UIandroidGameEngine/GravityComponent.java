package com.tfg.UIandroidGameEngine;

public class GravityComponent extends Component{

    private float speed = 0;

    private float previousPosition ;

    public GravityComponent(BasicGameObject container) {
        super(container);
        previousPosition = container.position.y;
    }

    @Override
    public void process(long elapsedTime){
        if(container.position.y > previousPosition){
            speed = (container.position.y - previousPosition)/elapsedTime;
        }
        speed += elapsedTime;
        container.preUpdatePosition.y += speed*elapsedTime*0.001;
        previousPosition = container.preUpdatePosition.y;

    }
}
