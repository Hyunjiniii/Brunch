package com.example.anhyunjin.brunch;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    private ImageView cls;
    private ImageView chk;
    private ImageView pto;
    private EditText sub;
    private EditText title;
    private EditText content;
    private ImageView fonts;
    private ImageView align;
    private String fonts_value;
    private String align_value;

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
        fonts = (ImageView) findViewById(R.id.fonts);
        align = (ImageView) findViewById(R.id.sort);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEdit = sub.getText().toString();
                String getEdit2 = title.getText().toString();
                String getEdit3 = content.getText().toString();


                if (getEdit.getBytes().length <= 0 && getEdit2.getBytes().length <= 0 && getEdit3.getBytes().length <= 0)
                    Toast.makeText(WriteActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();

                else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Toast.makeText(WriteActivity.this, "저장되었습니다.", Toast.LENGTH_LONG).show();
                    String uid = user.getUid();
                    Item a = new Item(title.getText().toString(), sub.getText().toString(), content.getText().toString(), formatDate);

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("contents").push().setValue(a);
                    finish();
                }
            }
        });


        cls.setOnClickListener(new View.OnClickListener()

        {
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

        pto.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final int GALLERY_INTENT = 100;

                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_INTENT);
            }
        });

        fonts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.menu_fonts, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.font1:
                                title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MILKYWAY.TTF"));
                                sub.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MILKYWAY.TTF"));
                                content.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MILKYWAY.TTF"));
                                fonts_value = "MILKYWAY";
                                break;
                            case R.id.font2:
                                title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/PNH_FRIENDNET.TTF"));
                                sub.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/PNH_FRIENDNET.TTF"));
                                content.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/PNH_FRIENDNET.TTF"));
                                fonts_value = "PNH_FRIENDNET";
                                break;
                            case R.id.font3:
                                title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
                                sub.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
                                content.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
                                fonts_value = "RIX_STAR_N_ME";
                                break;
                            default:
                                break;
                        }
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        FirebaseDatabase.getInstance().getReference().child("Item_plus").child(uid).child(title.getText().toString()).push().setValue("fonts : "+ fonts_value);
                        return false;
                    }

                });
                popup.show();

            }
        });

        align.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.menu_align, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.left:
                                ((EditText) title).setGravity(Gravity.LEFT);
                                ((EditText) sub).setGravity(Gravity.LEFT);
                                ((EditText) content).setGravity(Gravity.LEFT);
                                align_value = "LEFT";
                                break;
                            case R.id.center:
                                ((EditText) title).setGravity(Gravity.CENTER);
                                ((EditText) sub).setGravity(Gravity.CENTER);
                                ((EditText) content).setGravity(Gravity.CENTER);
                                align_value = "CENTER";
                                break;
                            case R.id.right:
                                ((EditText) title).setGravity(Gravity.RIGHT);
                                ((EditText) sub).setGravity(Gravity.RIGHT);
                                ((EditText) content).setGravity(Gravity.RIGHT);
                                align_value = "RIGHT";
                                break;
                            default:
                                break;
                        }
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        FirebaseDatabase.getInstance().getReference().child("Item_plus").child(uid).child(title.getText().toString()).push().setValue("align : "+ align_value);
                        return false;
                    }

                });
                popup.show();
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
