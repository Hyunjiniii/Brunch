package com.example.anhyunjin.brunch; //굿굿 ^^ 공부 굿굿 ^^ 굿굿 ^^!!!! 굿굿 ^^

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    final int GALLERY_INTENT = 100;
    private StorageReference mStorge = FirebaseStorage.getInstance().getReference();
    private ImageView cls;
    private ImageView chk;
    private ImageView pto;
    private EditText title;
    private EditText content;
    private ImageView align;
    private String align_value;
    static Uri downloadUrl;
    static boolean isimage = false;

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
                pto();
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
                            case R.id.right:
                                ((EditText) title).setGravity(Gravity.RIGHT);
                                ((EditText) content).setGravity(Gravity.RIGHT);
                                align_value = "RIGHT";
                                break;
                            default:
                                break;
                        }
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        FirebaseDatabase.getInstance().getReference().child("align").child(uid).push().setValue(align_value);
                        return false;
                    }

                });
                popup.show();
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
        title = (EditText) findViewById(R.id.write_title);
        content = (EditText) findViewById(R.id.write_contents);
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
        StorageReference filePath = mStorge.child("images/" + formatDate);

        filePath.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl = taskSnapshot.getDownloadUrl();
                isimage = true;
                Log.d("message", "들어옴");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WriteActivity.this, "예외 발생" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        Item a = new Item(title.getText().toString(), content.getText().toString(), formatDate, isimage, String.valueOf(downloadUrl));
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

}
