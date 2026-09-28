package com.example.mechu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

public class Introductory extends AppCompatActivity {

    ImageView miniLogo, app_name, bar;
    LottieAnimationView lottie;
    LinearLayout login_layout;

    Button sign_up_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        miniLogo = findViewById(R.id.miniLogo);
        app_name = findViewById(R.id.appName);
        bar = findViewById(R.id.bar);
        lottie = findViewById(R.id.lottie);
        login_layout = findViewById(R.id.loginLayout);
        login_layout.setAlpha(0f);

        bar.animate().translationY(-1100).setDuration(700).setStartDelay(2800);
        app_name.animate().translationY(-1100).setDuration(700).setStartDelay(2800);
        miniLogo.animate().translationY(-1100).setDuration(700).setStartDelay(2800);
        lottie.animate().translationY(3000).setDuration(700).setStartDelay(2500);

        SQLiteDatabase db = com.example.mechu_project.MyApplication.getDatabase();

        login_layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                login_layout.setVisibility(View.VISIBLE);
                login_layout.animate().alpha(1f).setDuration(500).start();
            }
        }, 3400); // 스플래시 대기 시간

        Button signUpButton = findViewById(R.id.signupButton);
        Button goChat = findViewById(R.id.goChat);

        Button loginButton = findViewById(R.id.loginButton);
        if (signUpButton != null) {
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Introductory.this, SignUp1.class);
                    startActivity(intent);
                }
            });
        }

        if (goChat != null) {
            goChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Introductory.this, Chatting.class);
                    startActivity(intent);
                }
            });
        }
    }
}
