package com.tfg.UIandroidGameEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    TextView mainHelpTitle;
    View helpButtonsList;
    View helpInfoView;
    private boolean infoIsShowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mainHelpTitle = findViewById(R.id.mainHelpTitle);
        helpButtonsList = findViewById(R.id.helpButtonList);
        helpInfoView = findViewById(R.id.helpInfoView);


        findViewById(R.id.helpEditorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoIsShowed = true;
                helpInfoView.setVisibility(View.VISIBLE);
                helpButtonsList.setVisibility(View.INVISIBLE);
                mainHelpTitle.setText("Editor");
            }
        });

    }

    @Override
    public void onBackPressed(){
        if (infoIsShowed){
            mainHelpTitle.setText("Ayuda");
            helpInfoView.setVisibility(View.INVISIBLE);
            helpButtonsList.setVisibility(View.VISIBLE);
            infoIsShowed = false;
        }else{
            super.onBackPressed();
        }
    }
}