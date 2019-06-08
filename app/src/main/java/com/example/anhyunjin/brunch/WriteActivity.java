package com.example.anhyunjin.brunch; //굿굿 ^^ 공부 굿굿 ^^ 굿굿 ^^!!!! 굿굿 ^^

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.anhyunjin.brunch.ListActivity.databaseReference;
import static com.example.anhyunjin.brunch.ListActivity.firebaseDatabase;
import static com.example.anhyunjin.brunch.ListActivity.items;

public class WriteActivity extends AppCompatActivity {
    final int GALLERY_INTENT = 100;
    private StorageReference mStorge = FirebaseStorage.getInstance().getReference();
    private ImageView cls;
    private ImageView chk;
    private ImageView pto;
    private EditText title;
    private EditText content;
    private ImageView align;
    private ImageView font;
    private ImageView image_view;
    private ImageView image_show_down;
    private ImageView image_show_up;
    private FrameLayout image_layout;
    private TextView image_text;
    static String font_value;
    static String align_value;
    static Uri downloadUrl;
    static boolean isimage = false;
    private int PERMISSIONS_READ_STORAGE = 0;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd (HH:mm:ss)");
    String formatDate = sdfDate.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        chk = (ImageView) findViewById(R.id.write_check);
        cls = (ImageView) findViewById(R.id.write_close);
        pto = (ImageView) findViewById(R.id.write_photo);
        title = (EditText) findViewById(R.id.write_title);
        content = (EditText) findViewById(R.id.write_contents);
        align = (ImageView) findViewById(R.id.write_align);
        font = (ImageView) findViewById(R.id.write_font);
        image_layout = (FrameLayout) findViewById(R.id.write_image_layout);
        image_view = (ImageView) findViewById(R.id.write_image_view);
        image_show_up = (ImageView) findViewById(R.id.write_image_show_up);
        image_show_down = (ImageView) findViewById(R.id.write_image_show_down);
        image_text = (TextView) findViewById(R.id.write_image_text);

        image_view.setVisibility(View.GONE);
        image_text.setVisibility(View.GONE);
        image_layout.setVisibility(View.GONE);
        image_show_down.setVisibility(View.GONE);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEdit2 = title.getText().toString();
                String getEdit3 = content.getText().toString();

                if (getEdit2.getBytes().length <= 0 && getEdit3.getBytes().length <= 0)
                    Toast.makeText(WriteActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();

                else {
                    chk_success();
                }
            }
        });

        cls.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                String getEdit2 = title.getText().toString();
                String getEdit3 = content.getText().toString();

                if (getEdit2.getBytes().length <= 0 && getEdit3.getBytes().length <= 0)
                    finish();
                else
                    alt();
            }
        });

        pto.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                permission();
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

        image_show_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_layout.setVisibility(View.VISIBLE);
                image_show_up.setVisibility(View.GONE);
                image_show_down.setVisibility(View.VISIBLE);

                if (isimage) {
                    image_view.setVisibility(View.VISIBLE);
                    image_text.setVisibility(View.GONE);

                    Glide.with(image_view.getContext())
                            .load(downloadUrl)
                            .into(image_view);

                } else {
                    image_view.setVisibility(View.GONE);
                    image_text.setVisibility(View.VISIBLE);
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
            }
        });

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteActivity.this, Onclick_ImageActivity.class);
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

    public void onBackPressed() {
        String getEdit2 = title.getText().toString();
        String getEdit3 = content.getText().toString();

        if (getEdit2.getBytes().length <= 0 && getEdit3.getBytes().length <= 0)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Test", "Permission ok");
                    pto();
                } else {
                    Log.e("Test", "Permission deny");
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
                    alert_confirm.setTitle("사진에 접근하기 위해 해당 권한이 필요합니다.").setMessage("[설정] > [권한]에서 해당 권한을 활성화 시켜주세요.")
                            .setCancelable(false).setPositiveButton("설정",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                            .setData(Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                }
                            }).setNegativeButton("닫기",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alert_confirm.create();
                    alert.show();
                }
                break;
        }
    }

    public void pto() {
        final int GALLERY_INTENT = 100;

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_INTENT);
    }

    public void chk_success() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(WriteActivity.this, "저장되었습니다.", Toast.LENGTH_LONG).show();
        String uid = user.getUid();
        Item a = new Item(title.getText().toString(), content.getText().toString(), formatDate, isimage, String.valueOf(downloadUrl), align_value, font_value);
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child(formatDate).setValue(a);

        finish();
    }

    public void alt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("작성중인 내용을 저장하지 않고 나가시겠습니까?");
        builder.setPositiveButton("확인", dialogListener);
        builder.setNegativeButton("취소", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void permission() {

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSIONS_READ_STORAGE);

    }


}//화이팅
