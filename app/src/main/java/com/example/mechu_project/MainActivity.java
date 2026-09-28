package com.example.mechu_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 좋아요 클릭 시 확대/축소 애니메이션
    ScaleAnimation scaleAnimation;
    BounceInterpolator bounceInterpolator;

    private static final int TAB_HOME = R.id.tab_home;
    private static final int TAB_SEARCHING = R.id.tab_searching;
    private static final int TAB_MECHU = R.id.tab_mechu;
    private static final int TAB_SETTINGS = R.id.tab_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == TAB_HOME) {
                    return true;
                } else if (item.getItemId() == TAB_SEARCHING) {
                     Intent searchIntent = new Intent(MainActivity.this, Search.class);
                     startActivity(searchIntent);
                    return true;
                } else if (item.getItemId() == TAB_MECHU) {
                    Intent menuRecommendationIntent = new Intent(MainActivity.this, Recommend.class);
                    startActivity(menuRecommendationIntent);
                    return true;
                } else if (item.getItemId() == TAB_SETTINGS) {
                     Intent profileIntent = new Intent(MainActivity.this, Profile.class);
                     startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });
     }

    public void onFavoriteButtonClick(View view) {
        view.startAnimation(scaleAnimation);
    }
}
