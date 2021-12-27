package com.tfg.UIandroidGameEngine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SceneManager {



    public ArrayList<BasicGameObject> objectsInCurrentScene = new ArrayList<BasicGameObject>();

    public int currentScene;

    private long timeToNextTick ;

    public ArrayList<Event> eventsTriggered = new ArrayList<Event>();

    public InputManager theInputManager = new InputManager();

    public Context ctx;




    public void addObjectToCurrentScene(BasicGameObject toAdd){
        objectsInCurrentScene.add(toAdd);
    }

    public SceneManager(){
        this.currentScene = 0;
        this.timeToNextTick = 0;
    }



    public void drawCurrentScene(Canvas renderCanvas,Camera camera){
        for (int i = 0; i < objectsInCurrentScene.size(); i ++ ){
            objectsInCurrentScene.get(i).draw(renderCanvas,camera);
        }
    }

    public void updateCurrentScene(long elapsedTime){

        if(timeToNextTick > 1000){

           // System.exit(-1);
            eventsTriggered.add(new EachSecondEvent());

            timeToNextTick = 0;
        }else{

            timeToNextTick  += elapsedTime;

        }

        // añadir eventos a eventsTriggered
        // añadir a cada objeto  las collisiones : TODA LA LOGICA DEL QuadTree
        for(int i = 0; i < objectsInCurrentScene.size(); i++){

            for(int j = 0; j < objectsInCurrentScene.size(); j++){
                float s1 = objectsInCurrentScene.get(j).position.x   - 25 * objectsInCurrentScene.get(j).scale.x;
                float s2  = objectsInCurrentScene.get(j).position.x   + 25 * objectsInCurrentScene.get(j).scale.x;
                float s3 = objectsInCurrentScene.get(j).position.y   - 25 * objectsInCurrentScene.get(j).scale.y;
                float s4 = objectsInCurrentScene.get(j).position.y   + 25 * objectsInCurrentScene.get(j).scale.y;


                float s5 = objectsInCurrentScene.get(i).position.x  - 25 * objectsInCurrentScene.get(i).scale.x;
                float s6  = objectsInCurrentScene.get(i).position.x  + 25 * objectsInCurrentScene.get(i).scale.x;
                float s7  = objectsInCurrentScene.get(i).position.y  - 25 * objectsInCurrentScene.get(i).scale.y;
                float s8 = objectsInCurrentScene.get(i).position.y  + 25 * objectsInCurrentScene.get(i).scale.y;


                if(j!= i){
                    if ((s1 > s5 && s1< s6 && s4 > s7 && s4 < s8) || (s1 > s5 && s1< s6 && s3> s7 && s3 < s8  ) || (s2 > s5 && s2 < s6 && s4 > s7 && s4 < s8 ) || (s2 > s5 && s2 < s6 && s3 > s7 && s3 < s8)
                        || (s5 > s1 && s5< s2 && s7 > s3 && s7 < s4) || (s5 > s1 && s5< s2 && s8> s3 && s8 < s4  ) || (s6 > s1 && s6 < s2 && s7 > s3 && s7 < s4 ) || (s6 > s1 && s6 < s2 && s8 > s3 && s8 < s4)){

                       objectsInCurrentScene.get(i).collisionsReceived.add(new Collision(objectsInCurrentScene.get(j)));

                    }
                }


            }
        }

        for (int i = 0; i < objectsInCurrentScene.size(); i++){
            //añadir a este objeto los inputEvents

            objectsInCurrentScene.get(i).update(elapsedTime,eventsTriggered);
        }

        for (int i = 0; i < objectsInCurrentScene.size(); i++){
            objectsInCurrentScene.get(i).postUpdate();
        }
        

        eventsTriggered.clear();

    }

    private BasicGameObject castDescriptionToObject(String[] objectDescription){

        BasicGameObject aux = new BasicGameObject(Float.parseFloat(objectDescription[1]),Float.parseFloat(objectDescription[2]),Integer.parseInt(objectDescription[6]),theInputManager,ctx,objectDescription[0],Boolean.parseBoolean(objectDescription[7]));
        //BasicGameObject aux = new BasicGameObject(Float.parseFloat("1500.0") ,Float.parseFloat("1500.0"),0,theInputManager,ctx, objectDescription[0]);

      // Toast.makeText(ctx,objectDescription[6] , Toast.LENGTH_SHORT).show();
        //Toast.makeText(ctx,objectDescription[2] , Toast.LENGTH_SHORT).show();

        aux.actionHolder.collisionActions = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(objectDescription[8]); i ++){
            aux.actionHolder.collisionActions.add(new ArrayList<Action>());
        }
        aux.scale = new Vector(Float.parseFloat(objectDescription[3]),Float.parseFloat(objectDescription[4]));
        aux.preUpdateScale = new Vector(Float.parseFloat(objectDescription[3]),Float.parseFloat(objectDescription[4]));
        aux.rotation = Float.parseFloat(objectDescription[5]);
        aux.preUpdateRotation = Float.parseFloat(objectDescription[5]);

        return aux;
    }


    public void loadScene(ArrayList<String[]> sceneObjects){



            eventsTriggered.clear();
           objectsInCurrentScene.clear();
            //eventsTriggered.add(new StartSceneEvent());
       for (int i = 0; i < sceneObjects.size();i++){

           BasicGameObject aux = castDescriptionToObject(sceneObjects.get(i));
           aux.sceneHierarchyID = objectsInCurrentScene.size();

           objectsInCurrentScene.add(aux);

       }timeToNextTick = 0;




    }




}
