package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class EditorGameView extends SurfaceView implements SurfaceHolder.Callback,Runnable, View.OnTouchListener {

    private  Thread drawThread;
    private UpdateThread updateThread;
    private SurfaceHolder holder;
    private Canvas canvas;
    private GameEngine theGameEngine = new GameEngine();

    private BasicGameObject testgameObject;

    public EditorGameView(Context context) {
        super(context);
        getHolder().addCallback(this);

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
         theGameEngine.addGameObject(new BasicGameObject((float)(getWidth()/2), (float)(getHeight()/2),0));
        //testgameObject = new BasicGameObject((float)(getWidth()/2), (float)(getHeight()/2),0);
       theGameEngine.addGameObject(new BasicGameObject((float)(getWidth()/2) + 100, (float)(getHeight()/2),0));
        this.holder = holder;
        drawThread = new Thread(this);
        updateThread = new UpdateThread(theGameEngine);
        drawThread.start();
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //pasar datos al InputManager
            theGameEngine.theInputManager.motionEvent = event;
        //referencia al gameEngine en el EditorUIfragment para poner en pausa el juego

        return false;
    }
}
