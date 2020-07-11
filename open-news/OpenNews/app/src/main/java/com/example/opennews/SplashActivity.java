package com.example.opennews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.opennews.Auth.AuthenticationActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = null;
    private final String TAG = "openNews";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if(user == null){
                    Log.e(TAG, "Not logged in");
                    startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
                } else{
                    Log.e(TAG, "Logged in");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        }, 2500);
    }
}
