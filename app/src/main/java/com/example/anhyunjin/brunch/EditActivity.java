package com.example.anhyunjin.brunch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.anhyunjin.brunch.ListActivity.databaseReference;
import static com.example.anhyunjin.brunch.WriteActivity.align_value;
import static com.example.anhyunjin.brunch.WriteActivity.downloadUrl;
import static com.example.anhyunjin.brunch.WriteActivity.font_value;
import static com.example.anhyunjin.brunch.WriteActivity.isimage;

public class EditActivity extends AppCompatActivity {
    final int GALLERY_INTENT = 100;
    private StorageReference mStorge = FirebaseStorage.getInstance().getReference();
    private EditText title;
    private EditText content;
    private ImageView pto;
    private ImageView chk;
    private ImageView cls;
    private ImageView font;
    private ImageView align;
    private ImageView image_view;
    private ImageView image_show_down;
    private ImageView image_show_up;
    private ImageView image_del;
    private FrameLayout image_layout;
    private TextView image_text;
    static String font_value;
    static String align_value;
    static Uri downloadUrl;
    boolean isimage;
    private String title_value;
    private String content_value;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd (HH:mm:ss)");
    String formatDate = sdfDate.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = (EditText) findViewById(R.id.edit_title);
        content = (EditText) findViewById(R.id.edit_contents);
        chk = (ImageView) findViewById(R.id.edit_check);
        cls = (ImageView) findViewById(R.id.edit_close);
        image_layout = (FrameLayout) findViewById(R.id.edit_image_layout);
        image_show_down = (ImageView) findViewById(R.id.edit_image_show_down);
        image_show_up = (ImageView) findViewById(R.id.edit_image_show_up);
        image_view = (ImageView) findViewById(R.id.edit_image_view);
        image_text = (TextView) findViewById(R.id.edit_image_text);
        font = (ImageView) findViewById(R.id.edit_font);
        align = (ImageView) findViewById(R.id.edit_align);
        pto = (ImageView) findViewById(R.id.edit_photo);
        image_del = (ImageView) findViewById(R.id.edit_image_del);

        image_view.setVisibility(View.GONE);
        image_text.setVisibility(View.GONE);
        image_layout.setVisibility(View.GONE);
        image_show_down.setVisibility(View.GONE);

        Intent intent = getIntent();
        final String data = intent.getStringExtra("date");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        databaseReference.child("users").child(uid).child(data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Item item = dataSnapshot.getValue(Item.class);
                if (item.isImage()) {
                    downloadUrl = Uri.parse(item.geturl());
                }
                font_value = item.getFont();
                align_value = item.getAlign();
                isimage = item.isImage();
                title.setText(item.getTitle());
                content.setText(item.getContent());
                title_value = item.getTitle();
                content_value = item.getContent();

                setFA();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pto.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                pto();
            }
        });


        font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);

                getMenuInflater().inflate(R.menu.menu_font, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.BM_dohyeon:
                                Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/BMDOHYEON_otf.TTF");
                                title.setTypeface(typeFace);
                                content.setTypeface(typeFace);
                                font_value = "BMDOHYEON";
                                break;
                            default:
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
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
                                ((EditText) content).setGravity(Gravity.LEFT);
                                align_value = "LEFT";
                                break;
                            case R.id.center:
                                ((EditText) title).setGravity(Gravity.CENTER);
                                ((EditText) content).setGravity(Gravity.CENTER);
                                align_value = "CENTER";
                                break;
                            default:
                                break;
                        }
                        return false;
                    }

                });
                popup.show();
            }
        });

        image_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_view.setVisibility(View.GONE);
                image_text.setVisibility(View.VISIBLE);
                image_del.setVisibility(View.GONE);
                downloadUrl = null;
                isimage = false;
            }
        });

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(EditActivity.this, "수정되었습니다.", Toast.LENGTH_LONG).show();
                String uid = user.getUid();
                Item a = new Item(title.getText().toString(), content.getText().toString(), data, isimage, String.valueOf(downloadUrl), align_value, font_value);
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child(data).setValue(a);

                finish();
            }
        });

        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEdit2 = title.getText().toString();
                String getEdit3 = content.getText().toString();

                if (getEdit2.equals(title_value) && getEdit3.equals(content_value))
                    finish();

                else
                    alt();
            }
        });

        image_show_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_layout.setVisibility(View.VISIBLE);
                image_show_up.setVisibility(View.GONE);
                image_show_down.setVisibility(View.VISIBLE);

                if (isimage) {
                    image_view.setVisibility(View.VISIBLE);
                    image_text.setVisibility(View.GONE);
                    image_del.setVisibility(View.VISIBLE);

                    Glide.with(image_view.getContext())
                            .load(downloadUrl)
                            .into(image_view);

                } else {
                    image_view.setVisibility(View.GONE);
                    image_text.setVisibility(View.VISIBLE);
                    image_del.setVisibility(View.GONE);
                }
            }
        });

        image_show_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_layout.setVisibility(View.GONE);
                image_show_up.setVisibility(View.VISIBLE);
                image_show_down.setVisibility(View.GONE);
                image_view.setVisibility(View.GONE);
                image_text.setVisibility(View.GONE);
                image_del.setVisibility(View.GONE);
            }
        });

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, Onclick_ImageActivity.class);
                intent.putExtra("url", String.valueOf(downloadUrl));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case GALLERY_INTENT:
                setList_image(data);
                break;

            default:
                break;

        }
    }

    public void setList_image(Intent data) {
        Uri file = data.getData();
        final StorageReference filePath = mStorge.child("images/" + formatDate);

        filePath.putFile(file).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUrl = task.getResult();
                    isimage = true;
                }
            }
        });
    }

    public void pto() {
        final int GALLERY_INTENT = 100;

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_INTENT);
    }

    public void setFA() {
        if ("BMDOHYEON".equals(font_value)) {
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/BMDOHYEON_otf.TTF");
            title.setTypeface(typeFace);
            content.setTypeface(typeFace);
        }

        if ("LEFT".equals(align_value)) {
            title.setGravity(Gravity.START);
            content.setGravity(Gravity.START);
        } else if ("CENTER".equals(align_value)) {
            title.setGravity(Gravity.CENTER);
            content.setGravity(Gravity.CENTER);
        }
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
