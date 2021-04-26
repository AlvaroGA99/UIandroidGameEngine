package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class EditorGameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private  Thread drawThread;
    private UpdateThread updateThread;
    private SurfaceHolder holder;
    private Canvas canvas;
    public GameEngine theGameEngine;


    private BasicGameObject testgameObject;

    public EditorGameView(Context context, GameEngine theGameEngine) {
        super(context);
        getHolder().addCallback(this);
        this.theGameEngine = theGameEngine;
        drawThread = new Thread(this);
        updateThread = new UpdateThread(this.theGameEngine);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        theGameEngine.getTheInputManager().setInputScreen(this.getWidth(),this.getHeight());

       if(theGameEngine.getObjectsInScene().size() == 0){
            Toast.makeText(getContext(),"CReación", Toast.LENGTH_SHORT).show();
            BasicGameObject aux = new BasicGameObject((float)(getWidth()/2), (float)(getHeight()/2),0, theGameEngine.getTheInputManager());
            aux.addComponent("InputMovementPlatformerComponent");
            aux.addComponent("GravityComponent");
            aux.addComponent("GroundColliderComponent");
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth()/2) + 100, (float)(getHeight()/2),0,theGameEngine.getTheInputManager());
            aux.addComponent("InputMovementPlatformerComponent");
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth()/2), (float)(getHeight()/2),0,theGameEngine.getTheInputManager());
            aux.preUpdateScale.x = 7;
            theGameEngine.addGameObject(aux);
           this.setOnTouchListener(new View.OnTouchListener(){

               @Override
               public boolean onTouch(View v, MotionEvent event) {

                   theGameEngine.getTheInputManager().processInput(event);

                   //pasar datos al InputManager
               /* if(event.getAction() == MotionEvent.ACTION_DOWN){
                    for(int i = 0 ; i < event.getPointerCount() ; i ++){
                        if(event.getX(i) > 10/2){
                            theGameEngine.getTheInputManager().isRightPressed = true;
                        }else {
                            theGameEngine.getTheInputManager().isLeftPressed = true;
                        }
                    }
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    for(int i = 0 ; i < event.getPointerCount() ; i ++){
                        if(event.getX(i) > 1/2){
                            theGameEngine.getTheInputManager().isRightPressed = false;
                        }else{
                            theGameEngine.getTheInputManager().isLeftPressed = false;
                        }
                    }
                }
*/
                   //referencia al gameEngine en el EditorUIfragment para poner en pausa el juego

                   return true;
               }
           });
        }

        //testgameObject = new BasicGameObject((float)(getWidth()/2), (float)(getHeight()/2),0);




        this.holder = holder;

       if(!drawThread.isAlive())
        drawThread.start();
        if(!updateThread.isAlive())
        updateThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }



    @Override
    public void run() {
        while(true){
            canvas = holder.lockCanvas();
            if (canvas != null){
                canvas.drawARGB(255,0,0,0);
                theGameEngine.drawAll(canvas);
                //testgameObject.draw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

}
