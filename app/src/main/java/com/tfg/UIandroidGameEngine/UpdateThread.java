package com.tfg.UIandroidGameEngine;

public class UpdateThread extends Thread {

    private GameEngine theGameEngine;

    private long elapsedTime  ;

    private long previousTimeMilis  ;

    public UpdateThread(GameEngine gameEngine){
        this.theGameEngine = gameEngine;
    }

    @Override
    public void run(){
        previousTimeMilis = System.currentTimeMillis();
        while(true){
            if(theGameEngine.isGameRunning){
                elapsedTime = System.currentTimeMillis();

                theGameEngine.updateAll(elapsedTime - previousTimeMilis);
                previousTimeMilis = elapsedTime;
            }else{
                previousTimeMilis = System.currentTimeMillis();
            }


        }
    }

}
