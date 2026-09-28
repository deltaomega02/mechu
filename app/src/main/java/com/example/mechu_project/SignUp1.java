package com.example.mechu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SignUp1 extends AppCompatActivity {

    Button signupNext;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        signupNext = findViewById(R.id.signupNext);
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox4 = findViewById(R.id.checkbox4);
        // 전체 동의 체크박스 처리
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = checkBox1.isChecked();
                checkBox2.setChecked(isChecked);
                checkBox3.setChecked(isChecked);
                checkBox4.setChecked(isChecked);
            }
        });

        signupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox2.isChecked() && checkBox3.isChecked()) {
                    Intent intent = new Intent(SignUp1.this, SignUp2.class);
                    startActivity(intent);
                } else {
                    showMessage("필수동의를 모두 체크해주세요.");
                }
            }
        });

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
