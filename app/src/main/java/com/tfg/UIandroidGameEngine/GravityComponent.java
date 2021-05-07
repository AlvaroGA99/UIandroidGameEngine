package com.tfg.UIandroidGameEngine;

public class GravityComponent extends Component{




    public GravityComponent(BasicGameObject container) {
        super(container,"GravityComponent");

    }

    @Override
    public void process(long elapsedTime){

        container.speed += elapsedTime;
        container.preUpdatePosition.y += container.speed*elapsedTime*0.001;


    }
}
