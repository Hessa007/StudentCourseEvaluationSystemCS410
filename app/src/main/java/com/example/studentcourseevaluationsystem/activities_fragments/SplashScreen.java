package com.example.studentcourseevaluationsystem.activities_fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.studentcourseevaluationsystem.R;

public class SplashScreen extends AppCompatActivity {
    private static int TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               next_activity();
            }
        }, TIME_OUT);
    }//end onCreate()

    public void splashClicked(View view){
        next_activity();
        finish();
    }//end splashClicked
    public void next_activity(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Boolean is_logged_in=pref.getBoolean("is_login", false);
        Intent i;
        if(is_logged_in){ //go directly to Main activity
            i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{   //user should login
            i = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(i);
            finish();
        }//end if-else block

    }//end next_activity
}//end activity
