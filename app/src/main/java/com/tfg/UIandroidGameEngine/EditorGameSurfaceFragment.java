package com.tfg.UIandroidGameEngine;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditorGameSurfaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditorGameSurfaceFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public EditorGameView gv ;
    public GameEngine theGameEngine ;
    public ObjectsInSceneAdapter os;
    // TO saadf
    // TODO: Rename and change types of parameters


    public EditorGameSurfaceFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditorGameSurfaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditorGameSurfaceFragment newInstance(GameEngine gameEngine) {
        EditorGameSurfaceFragment fragment = new EditorGameSurfaceFragment();

        fragment.theGameEngine = gameEngine;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //Resources r = theGameEngine.ctx.getResources();
        gv = new EditorGameView(getContext(),theGameEngine);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editor_game_surface, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        FrameLayout ff = (FrameLayout) getActivity().findViewById(R.id.framelayout);


        ff.addView(gv);


        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.editorContainer, ((EditorActivity)getActivity()).f2,null).commit();



    }


}