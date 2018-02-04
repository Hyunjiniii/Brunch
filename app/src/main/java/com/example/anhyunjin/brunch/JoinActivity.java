package com.example.anhyunjin.brunch;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ArrayAdapter<Object> adapter;
    private Button sign_up_btn;
    private EditText username;
    private EditText email;
    private EditText ps;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email_join);
        ps = (EditText) findViewById(R.id.ps_join);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            String getEdit = username.getText().toString();
            String getEdit2 = email.getText().toString();
            String getEdit3 = ps.getText().toString();

            @Override
            public void onClick(View v) {
                LoginData loginData = new LoginData(username.getText().toString(), email.getText().toString(), ps.getText().toString());
                databaseReference.child("user").push().setValue(loginData);

                if (getEdit.getBytes().length <= 0 || getEdit2.getBytes().length <= 0 || getEdit3.getBytes().length <= 0)
                    Toast.makeText(JoinActivity.this, "모두 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }

        });

        databaseReference.child("user").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LoginData loginData = dataSnapshot.getValue(LoginData.class);
                LoginData a = new LoginData(loginData.getUsername(), loginData.getEmail(), loginData.getPs());
                adapter.add(a);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
