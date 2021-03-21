package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class EditorGameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private  Thread drawThread;
    private SurfaceHolder holder;
    private Canvas canvas;

    public EditorGameView(Context context) {
        super(context);
        getHolder().addCallback(this);

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.holder = holder;
        drawThread = new Thread(this);
        drawThread.start();
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
                canvas.drawARGB(255,255,0,0);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
