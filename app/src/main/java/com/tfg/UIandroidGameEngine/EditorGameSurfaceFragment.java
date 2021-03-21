package com.tfg.UIandroidGameEngine;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditorGameView gv ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditorGameSurfaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditorGameSurfaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditorGameSurfaceFragment newInstance(String param1, String param2) {
        EditorGameSurfaceFragment fragment = new EditorGameSurfaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
        gv = new EditorGameView(getContext());

        ff.addView(gv);

    }


}