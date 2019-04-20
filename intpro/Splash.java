package com.binary2quantum.android.intpro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.binary2quantum.android.intpro.databases.SessionManager;

public class Splash extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public final int SPLASH_DISPLAY_LENGTH = 3000;
    TextView t1;
    ImageView im1;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, Login.class);
                Splash.this.startActivity(mainIntent);
                sessionManager.checkLogin();
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

//    public void fad() {
//        im1=(ImageView)findViewById(R.id.splashimage);
//        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fad);
//        im1.startAnimation(animation);
//    }
//    public void blink() {
//        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
//        t1.startAnimation(animation);
//    }
}
