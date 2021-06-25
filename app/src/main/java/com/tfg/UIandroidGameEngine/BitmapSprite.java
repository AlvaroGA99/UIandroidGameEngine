package com.tfg.UIandroidGameEngine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.io.IOException;

public class BitmapSprite implements Sprite{

    Bitmap bitmap ;
    Context ctx;
    Resources spriteDrawable;
    Matrix m;

    public BitmapSprite(Context ctx)  {

        this.ctx = ctx;
        ctx.getResources();
       //spriteDrawable = ctx.getResources();
         bitmap = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.sprite);
        //Toast.makeText(ctx,""+    bitmap.getHeight(),Toast.LENGTH_SHORT).show();
//bitmap.getHeight();
        m =  new Matrix();

    }

        @Override
            public void draw(Vector position, Vector scale, float rotation,Canvas renderCanvas,Camera camera) {
                m.reset();
                m.postScale(50.0f/bitmap.getWidth()*scale.x, 50.0f/bitmap.getHeight()*scale.y);
               // m.postRotate(rotation);
                m.postTranslate(position.x + camera.getScreenSpaceX()- 25*scale.x,position.y + camera.getScreenSpaceY() - 25*scale.y);
                renderCanvas.drawBitmap(bitmap,m,null);

            }
}
