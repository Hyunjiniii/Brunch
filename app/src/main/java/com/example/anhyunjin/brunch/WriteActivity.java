package com.example.anhyunjin.brunch;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.anhyunjin.brunch.ListActivity.databaseReference;
import static com.example.anhyunjin.brunch.ListActivity.items;

public class WriteActivity extends AppCompatActivity {
    private ImageView cls;
    private ImageView chk;
    private ImageView pto;
    private EditText sub;
    private EditText title;
    private EditText content;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY/MM/dd (EEE)");
    String formatDate = sdfDate.format(date);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        chk = (ImageView) findViewById(R.id.check);
        cls = (ImageView) findViewById(R.id.close);
        pto = (ImageView) findViewById(R.id.photo);
        sub = (EditText) findViewById(R.id.sub_title);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.contents);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEdit = sub.getText().toString();
                String getEdit2 = title.getText().toString();
                String getEdit3 = content.getText().toString();

                if (getEdit.getBytes().length <= 0 && getEdit2.getBytes().length <= 0 && getEdit3.getBytes().length <= 0)
                    Toast.makeText(WriteActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();

                else {
                    Toast.makeText(WriteActivity.this, "저장되었습니다.", Toast.LENGTH_LONG).show();
                    Item a = new Item(title.getText().toString(), sub.getText().toString(), content.getText().toString(), formatDate);
                    items.add(a);
                    databaseReference.child("contents").push().setValue(a);

                    finish();
                }
            }
        });

        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEdit = sub.getText().toString();
                String getEdit2 = title.getText().toString();
                String getEdit3 = content.getText().toString();

                if (getEdit.getBytes().length <= 0 && getEdit2.getBytes().length <= 0 && getEdit3.getBytes().length <= 0)
                    finish();
                else
                    alt();
            }
        });

        pto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int GALLERY_INTENT = 100;

                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_INTENT);
            }
        });

    }

    public void onBackPressed() {
        sub = (EditText) findViewById(R.id.sub_title);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.contents);
        String getEdit = sub.getText().toString();
        String getEdit2 = title.getText().toString();
        String getEdit3 = content.getText().toString();

        if (getEdit.getBytes().length <= 0 && getEdit2.getBytes().length <= 0 && getEdit3.getBytes().length <= 0)
            finish();
        else
            alt();

    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    };

    public void alt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("작성중인 내용을 저장하지 않고 나가시겠습니까?");
        builder.setPositiveButton("확인", dialogListener);
        builder.setNegativeButton("취소", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}