package com.me.desaprajurit.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.me.desaprajurit.R;
import com.me.desaprajurit.home.MainActivity;
import com.me.desaprajurit.login.LoginActivity;

public class SplashscreenActivity extends AppCompatActivity {
    private static int splashInterval = 3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }, splashInterval);
    }
}
