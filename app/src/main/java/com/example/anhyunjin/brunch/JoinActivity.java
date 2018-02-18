package com.example.anhyunjin.brunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anhyunjin.brunch.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.anhyunjin.brunch.ListActivity.databaseReference;

public class JoinActivity extends AppCompatActivity {
    private Button sign_up_btn;
    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_ps;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    TextInputLayout pw_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        edit_name = (EditText) findViewById(R.id.name_join);
        edit_email = (EditText) findViewById(R.id.email_join);
        edit_ps = (EditText) findViewById(R.id.ps_join);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        pw_layout = (TextInputLayout) findViewById(R.id.pw_layout);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edit_email.getText().toString().trim();
                String password = edit_ps.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(JoinActivity.this, "Email을 입력해 주세요.", LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(JoinActivity.this, "Password를 입력해 주세요.", LENGTH_SHORT).show();
                    return;
                } else if (edit_ps.getText().toString().length() < 6) {
                    Toast.makeText(JoinActivity.this, "Password를 6자 이상 입력해 주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("등록중입니다. 기다려 주세요...");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String uid = task.getResult().getUser().getUid();
                                UserModel userModel = new UserModel();
                                userModel.UserName = edit_name.getText().toString();

                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);


                                if (!task.isSuccessful()){
                                    Toast.makeText(JoinActivity.this, "등록 에러! 이메일 중복!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                else
                                    finish();

                                progressDialog.dismiss();
                            }
                        });
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
