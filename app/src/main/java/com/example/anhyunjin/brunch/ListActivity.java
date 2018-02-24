package com.example.anhyunjin.brunch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhyunjin.brunch.model.UserModel;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity  implements View.OnClickListener{
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReference = firebaseDatabase.getReference();
    public static ArrayList<Item> items = new ArrayList<>();
    private long time = 0;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter adapter;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab_logout, fab_add;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_design);
        setContentView(R.layout.recyclerview);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_logout = (FloatingActionButton) findViewById(R.id.fab1);
        fab_add = (FloatingActionButton) findViewById(R.id.fab2);
        cardView = (CardView) findViewById(R.id.cardview);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        fab.setOnClickListener(this);
        fab_logout.setOnClickListener(this);
        fab_add.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(items);
        mRecyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("contents").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Item item = dataSnapshot.getValue(Item.class);
                    Item a = new Item(item.getTitle(), item.getSub_title(), item.getContent(), item.getDate());
                    items.add(a);
                    adapter.notifyDataSetChanged();
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
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, ClickActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 1500) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 1500)
            finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferences pref = getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = pref.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                items.clear();
                finish();
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:

                intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivity(intent);
                Log.d("Raj", "Fab 2");
                break;
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab_logout.startAnimation(fab_close);
            fab_add.startAnimation(fab_close);
            fab_logout.setClickable(false);
            fab_add.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab_logout.startAnimation(fab_open);
            fab_add.startAnimation(fab_open);
            fab_logout.setClickable(true);
            fab_add.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

}

