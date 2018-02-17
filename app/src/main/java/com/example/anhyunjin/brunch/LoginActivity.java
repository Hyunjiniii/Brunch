package com.example.anhyunjin.brunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private boolean saveLoginData;
    private String email;
    private String pwd;

    private Button login_btn;
    private TextView join_btn;
    private EditText email_login;
    private EditText ps_login;
    private CheckBox autologin;
    SharedPreferences pref;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = (Button) findViewById(R.id.login_btn);
        join_btn = (TextView) findViewById(R.id.join);
        autologin = (CheckBox) findViewById(R.id.autologin);
        email_login = (EditText) findViewById(R.id.email_login);
        ps_login = (EditText) findViewById(R.id.ps_login);

        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        pref = getSharedPreferences("pref", 0);
        load();

        if (saveLoginData) {
            email_login.setText(email);
            ps_login.setText(pwd);
            autologin.setChecked(saveLoginData);
        }

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                userLogin();
            }
        });


    }

    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = pref.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", autologin.isChecked());
        editor.putString("EMAIL", email_login.getText().toString().trim());
        editor.putString("PWD", ps_login.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = pref.getBoolean("SAVE_LOGIN_DATA", false);
        email = pref.getString("EMAIL", "");
        pwd = pref.getString("PWD", "");
    }

    private void userLogin() {
        final String email = email_login.getText().toString().trim();
        final String password = ps_login.getText().toString().trim();

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
