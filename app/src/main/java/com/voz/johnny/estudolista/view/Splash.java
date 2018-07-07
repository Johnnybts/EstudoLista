package com.voz.johnny.estudolista.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.voz.johnny.estudolista.R;

public class Splash extends AppCompatActivity {

    public static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        }, SPLASH_TIME_OUT);
    }
}
