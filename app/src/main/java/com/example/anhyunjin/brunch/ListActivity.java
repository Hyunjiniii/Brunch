package com.example.anhyunjin.brunch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class  ListActivity extends AppCompatActivity implements View.OnClickListener {
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReference = firebaseDatabase.getReference();
    public static StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    public static ArrayList<Item> items = new ArrayList<>();
    private long time = 0;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter adapter;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab_logout, fab_add;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private TextView lottie_text;
    private LottieAnimationView lottieAnimationView;

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
        lottie_text = (TextView) findViewById(R.id.recycler_text);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        fab.setOnClickListener(this);
        fab_logout.setOnClickListener(this);
        fab_add.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(items, ListActivity.this);
        mRecyclerView.setAdapter(adapter);

        items.clear();
        addDataView();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            databaseReference.child("users").child(uid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Item item = dataSnapshot.getValue(Item.class);
                    items.clear();
                    if (item.isImage()) {
                        Item a = new Item(item.getTitle(), item.getContent(), item.getDate(), item.isImage(), item.geturl(), item.getAlign(), item.getFont());
                        items.add(a);
                        adapter.notifyDataSetChanged();
                    } else {
                        Item a = new Item(item.getTitle(), item.getContent(), item.getDate(), item.getAlign(), item.getFont());
                        items.add(a);
                        adapter.notifyDataSetChanged();
                    }

                    addDataView();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    finish();
                    startActivity(getIntent());
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    addDataView();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
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
                animateFAB();
                Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferences pref = getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                items.clear();
                finish();
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:
                animateFAB();
                intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivity(intent);
                Log.d("Raj", "Fab 2");
                break;
        }
    }

    public void addDataView() {
        if (items.size() == 0) {
            lottie_text.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
            lottieAnimationView.loop(true);
        } else {
            lottie_text.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.GONE);
            lottieAnimationView.clearAnimation();
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

