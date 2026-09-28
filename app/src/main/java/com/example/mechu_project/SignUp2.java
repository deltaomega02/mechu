package com.example.mechu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

// 회원가입 - 이메일 인증 화면
public class SignUp2 extends AppCompatActivity {

    EditText editTextId, editPasswd, editEmail;
    EditText validEmail;
    String verificationCode;
    String userInputCode;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        dbHelper = new DatabaseHelper(this);

        editTextId = findViewById(R.id.editTextId);
        editPasswd = findViewById(R.id.editPasswd);

        Button signupNext = findViewById(R.id.signupNext);
        editEmail = findViewById(R.id.editEmail);
        validEmail = findViewById(R.id.validEmail);

        if (signupNext != null) {
            signupNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = editTextId.getText().toString();
                    String password = editPasswd.getText().toString();
                    String email = editEmail.getText().toString();

                    // 아이디와 비밀번호가 입력되면 다음 단계로 이동
                    if (!user_id.isEmpty() && !password.isEmpty()) {
                        insertUser(user_id, password);

                        Intent intent = new Intent(SignUp2.this, SignUp3.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                    } else {
                        showMessage("모든 필드를 입력해주세요.");
                    }
                }
            });
        }

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.buttonEmailCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientEmail = editEmail.getText().toString();

                verificationCode = generateVerificationCode();

                new SendMail().execute(recipientEmail, verificationCode);
            }
        });

        findViewById(R.id.buttonValidEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userInputCode = validEmail.getText().toString();
                if (verificationCode != null && verificationCode.equals(userInputCode)) {
                    showMessage("인증이 성공했습니다.");
                } else {
                    showMessage("인증이 실패했습니다. 올바른 인증 코드를 입력하세요.");
                }
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String generateVerificationCode() {
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            temp.append(rnd.nextInt(10));
        }
        return temp.toString();
    }

    private class SendMail extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            final String user_id = "YOUR_NAVER_ACCOUNT@naver.com"; // 발신용 네이버 계정
            final String password = "YOUR_NAVER_APP_PASSWORD"; // 발신 계정 비밀번호

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.naver.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user_id, password);
                        }
                    });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user_id, "MECHU Company"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(params[0]));
                message.setSubject("인증번호");
                message.setText("해당 인증번호는 " + params[1] + "입니다.");

                Transport.send(message);
                return true;
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showMessage("인증 이메일을 전송했습니다.");
            } else {
                showMessage("이메일 전송에 실패했습니다. 다시 시도해주세요.");
            }
        }
    }

    // 사용자 정보 DB 저장
    private void insertUser(String user_id, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("password", password);

        long result = db.insert("user", null, values);
        db.close();

        if (result != -1) {
            showMessage("데이터가 성공적으로 입력되었습니다.");
        } else {
            showMessage("데이터 입력에 실패했습니다.");
        }
    }
}
