package com.tfg.UIandroidGameEngine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 123;
    private boolean registerState = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Choose authentication providers
        EditText email  = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login2);
        login.setBackgroundResource(R.drawable.roundborder_drawable);
        login.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.right);
        TextView gotoregister =  findViewById(R.id.goToRegister);
        TextView textLogin = findViewById(R.id.textLogin);
        EditText nickname = findViewById(R.id.nickname);

        EditText email2 = findViewById(R.id.username2);
        EditText password2 = findViewById(R.id.password2);
        Button register = findViewById(R.id.register);
        register.setBackgroundResource(R.drawable.roundborder_drawable);
        //email.getText().toString();
        FirebaseUser a = FirebaseAuth.getInstance().getCurrentUser();
        if (a!=null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);

            startActivity(intent);
        }
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerState){
                    registerState = false;
                    gotoregister.setText("Iniciar Sesión");
                    textLogin.setText("Registrarse");
                    login.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    email.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                    email2.setVisibility(View.VISIBLE);
                    password2.setVisibility(View.VISIBLE);
                    nickname.setVisibility(View.VISIBLE);
                }else{
                    registerState = true;
                    textLogin.setText("Iniciar Sesión");
                    gotoregister.setText("¿No tienes cuenta? Regístrate");
                    login.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    register.setVisibility(View.INVISIBLE);
                    email2.setVisibility(View.INVISIBLE);
                    password2.setVisibility(View.INVISIBLE);
                    nickname.setVisibility(View.INVISIBLE);
                }

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Introduzca un email válido",Toast.LENGTH_SHORT);
                if (!email.getText().toString().equals("")   &&  !password.getText().toString().equals("") ){
                    /*if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        Toast.makeText(getApplicationContext(),"Introduzca un email válido",Toast.LENGTH_SHORT);
                    }else{*/
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),   password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isCanceled()){
                                    Toast.makeText(getApplicationContext(),"Error en la autenticación",Toast.LENGTH_SHORT).show();
                                }else if (task.isSuccessful()){
                                    Intent a = new Intent(getApplicationContext(),MainActivity.class);
                                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(a);
                                }
                            }
                        });
                    }

               }
           // }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email2.getText().toString(),   password2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isCanceled()){
                            Toast.makeText(getApplicationContext(),"Error en la autenticación",Toast.LENGTH_SHORT).show();
                        }else if (task.isSuccessful()){

                            FirebaseDatabase.getInstance().getReference("users/" + task.getResult().getUser().getUid() +"/username").setValue( nickname.getText().toString());

                        }
                    }
                });
            }
        });

    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("exit")) {
            setIntent(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null) {
            if (("exit").equalsIgnoreCase(getIntent().getStringExtra(("exit")))) {
                onBackPressed();
            }
        }
    }

}
