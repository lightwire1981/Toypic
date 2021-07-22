package com.example.tpictest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }
        setContentView(R.layout.activity_splash);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

//            PreferenceSetting preferenceSetting = new PreferenceSetting(getBaseContext());
        getWindow().getDecorView().postDelayed(() -> {
            Intent intent = new Intent(getBaseContext(), IntermediateActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 3000);

    }
}