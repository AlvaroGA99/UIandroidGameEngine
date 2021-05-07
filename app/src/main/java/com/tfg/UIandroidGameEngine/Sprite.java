package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

public interface Sprite {

    public void draw(Vector position, Vector scale, float rotation, Canvas renderCanvas, Camera camera);

}
