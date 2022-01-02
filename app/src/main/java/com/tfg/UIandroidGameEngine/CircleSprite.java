package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CircleSprite implements Sprite {

    private Canvas renderCanvas;
    int[] colors = {Color.parseColor("#FFF44336"),Color.parseColor("#FFE91E63"),Color.parseColor("#FF9C27B0"),Color.parseColor("#FF673AB7"),Color.parseColor("#FF3F51B5"),Color.parseColor("#FF2196F3"),Color.parseColor("#FF03A9F4"),Color.parseColor("#FF00BCD4"),Color.parseColor("#FF009688"),Color.parseColor("#FF4CAF50"),Color.parseColor("#FF8BC34A"),Color.parseColor("#FFCDDC39"),Color.parseColor("#FFFFEB3B"),Color.parseColor("#FFFFC107"),Color.parseColor("#FFFF9800"),Color.parseColor("#FFFF5722")};
    int color = colors[0];
    public CircleSprite(){

    }

    @Override
    public void draw(Vector position, Vector scale, float rotation,Canvas renderCanvas, Camera camera) {
        Paint aux = new Paint();
        aux.setColor(color);
        renderCanvas.save();
        renderCanvas.rotate(rotation,position.x + camera.getScreenSpaceX(),position.y + camera.getScreenSpaceY());
        renderCanvas.drawCircle( position.x + camera.getScreenSpaceX()  ,position.y + camera.getScreenSpaceY(), 25*scale.y, aux);




        renderCanvas.restore();
    }
@Override
    public void loadSprite(int spriteNumber){
        color = colors[spriteNumber];
    }
}
