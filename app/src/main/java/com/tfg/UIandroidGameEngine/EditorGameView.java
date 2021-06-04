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
        theGameEngine.camera.setCameraCenter((float)this.getWidth()/2,(float) this.getHeight()/2);
        //theGameEngine.camera.lookAt(theGameEngine.getObjectsInScene().get(2));

           this.setOnTouchListener(new View.OnTouchListener(){

               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   if(event.getAction() == MotionEvent.ACTION_DOWN){
                       theGameEngine.lastTouched.x = event.getX();
                       theGameEngine.lastTouched.y = event.getY();
                   }
                   theGameEngine.getTheInputManager().processInput(event);
                       for(int i = 0 ; i < event.getPointerCount() ; i ++){

                           if(theGameEngine.isInEditor) {


                               int topId = -1;
                               //Toast.makeText(getContext(),"" + event.getX(i) + " , " + event.getY(i) ,Toast.LENGTH_SHORT).show();
                               for (int j = 0; j < theGameEngine.getObjectsInScene().size(); j++) {


                                   if (event.getX(i) > theGameEngine.getObjectsInScene().get(j).position.x + theGameEngine.camera.getScreenSpaceX() - 25 * theGameEngine.getObjectsInScene().get(j).scale.x
                                           && event.getX(i) < theGameEngine.getObjectsInScene().get(j).position.x + theGameEngine.camera.getScreenSpaceX() + 25 * theGameEngine.getObjectsInScene().get(j).scale.x
                                           && event.getY(i) > theGameEngine.getObjectsInScene().get(j).position.y + theGameEngine.camera.getScreenSpaceY() - 25 * theGameEngine.getObjectsInScene().get(j).scale.y
                                           && event.getY(i) < theGameEngine.getObjectsInScene().get(j).position.y + theGameEngine.camera.getScreenSpaceY() + 25 * theGameEngine.getObjectsInScene().get(j).scale.y) {
                                       if (theGameEngine.getObjectsInScene().get(j).sceneHierarchyID > topId) {
                                           topId = theGameEngine.getObjectsInScene().get(j).sceneHierarchyID;
                                       }
                                   }
                               }
                               if (topId > -1) {
                                   theGameEngine.getObjectsInScene().get(topId).position.x = event.getX(i) - theGameEngine.camera.getScreenSpaceX();
                                   theGameEngine.getObjectsInScene().get(topId).position.y = event.getY(i) - theGameEngine.camera.getScreenSpaceY();
                                   theGameEngine.getObjectsInScene().get(topId).preUpdatePosition.x = event.getX(i) - theGameEngine.camera.getScreenSpaceX();
                                   theGameEngine.getObjectsInScene().get(topId).preUpdatePosition.y = event.getY(i) - theGameEngine.camera.getScreenSpaceY();

                                   theGameEngine.lastTouched.x = event.getX();
                                   theGameEngine.lastTouched.y = event.getY();

                               } else if (event.getPointerCount() == 1) {
                                   if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                       theGameEngine.lastTouched.x = event.getX();
                                       theGameEngine.lastTouched.y = event.getY();
                                   }

                                   theGameEngine.camera.fixedLookingPosition.x -= event.getX() - theGameEngine.lastTouched.x;
                                   theGameEngine.camera.fixedLookingPosition.y -= event.getY() - theGameEngine.lastTouched.y;

                                   theGameEngine.lastTouched.x = event.getX();
                                   theGameEngine.lastTouched.y = event.getY();
                               }
                           }else if(theGameEngine.isGameRunning){


                                   for (int j = 0; j < theGameEngine.getObjectsInScene().size(); j++) {


                                       if (event.getX(i) > theGameEngine.getObjectsInScene().get(j).position.x + theGameEngine.camera.getScreenSpaceX() - 25 * theGameEngine.getObjectsInScene().get(j).scale.x
                                               && event.getX(i) < theGameEngine.getObjectsInScene().get(j).position.x + theGameEngine.camera.getScreenSpaceX() + 25 * theGameEngine.getObjectsInScene().get(j).scale.x
                                               && event.getY(i) > theGameEngine.getObjectsInScene().get(j).position.y + theGameEngine.camera.getScreenSpaceY() - 25 * theGameEngine.getObjectsInScene().get(j).scale.y
                                               && event.getY(i) < theGameEngine.getObjectsInScene().get(j).position.y + theGameEngine.camera.getScreenSpaceY() + 25 * theGameEngine.getObjectsInScene().get(j).scale.y) {


                                           //theGameEngine.getObjectsInScene().get(j).eventsReceived.add(new OnClickEvent());

                                            Toast.makeText(getContext(),"PULSADOOOOOOOOO",Toast.LENGTH_SHORT).show();

                                       }
                                   }


                           }
                       }


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
