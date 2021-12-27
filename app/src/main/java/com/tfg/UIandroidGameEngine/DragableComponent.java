package com.tfg.UIandroidGameEngine;

public class DragableComponent extends Component {

    GameEngine theGameEngine;

    public DragableComponent(BasicGameObject container,GameEngine theGameEngine) {
        super(container, "DragableComponent");
        this.theGameEngine = theGameEngine;
    }

    @Override
    public void process(long elapsedTime) {
        //if(container.inputManager.) theGameEngine.las
    }
}
