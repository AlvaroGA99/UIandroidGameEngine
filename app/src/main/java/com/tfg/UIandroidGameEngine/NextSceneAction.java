package com.tfg.UIandroidGameEngine;

public class NextSceneAction extends  Action {
    public NextSceneAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "NextSceneAction");
    }

    @Override
    public void execute(int evenTrigger) {
        int sceneGo = theGameEngine.getCurrentScene() + 1;
        if(sceneGo < theGameEngine.SceneList.size() && sceneGo >= 0){
            theGameEngine.loadScene(sceneGo);
        }

    }
}
