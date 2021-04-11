package com.tfg.UIandroidGameEngine;

public class UpdateThread extends Thread {

    private GameEngine theGameEngine;

    private long elapsedTime = System.currentTimeMillis();

    public UpdateThread(GameEngine gameEngine){
        this.theGameEngine = gameEngine;
    }

    @Override
    public void run(){
        while(true){
            elapsedTime = System.currentTimeMillis() - elapsedTime;
            theGameEngine.updateAll(elapsedTime);
        }
    }

}
