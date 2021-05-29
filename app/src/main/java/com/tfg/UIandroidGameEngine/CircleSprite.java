package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CircleSprite implements Sprite {

    private Canvas renderCanvas;

    public CircleSprite(){

    }

    @Override
    public void draw(Vector position, Vector scale, float rotation,Canvas renderCanvas, Camera camera) {
        Paint aux = new Paint();
        aux.setARGB(255,255 , 0 ,0);
        renderCanvas.save();
        renderCanvas.rotate(rotation,position.x + camera.getScreenSpaceX(),position.y + camera.getScreenSpaceY());
        renderCanvas.drawCircle( position.x + camera.getScreenSpaceX()  ,position.y + camera.getScreenSpaceY(), 25*scale.y, aux);




        renderCanvas.restore();
    }
}
