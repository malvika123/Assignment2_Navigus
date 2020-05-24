package com.example.docviewer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class profile extends AppCompatActivity {
    ImageView img;
   FirebaseUser user;
    TextView schedule;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference rootRef;
    DatabaseReference userRef;
    TextView username;
    ArrayList<schedule> scheduleArrayList;
    TextView e_mail;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username=findViewById(R.id.username);
        e_mail=findViewById(R.id.email);
        schedule=findViewById(R.id.schedule);
        rootRef= FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Members");
        Intent i= getIntent();
        final String mail=i.getStringExtra("email");
        scheduleArrayList= new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
           String sch;
           String bookedby;
            String time;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(mail)) {
                        Iterator<DataSnapshot> items=ds.child("table").getChildren().iterator();
                        while(items.hasNext()){
                            DataSnapshot item=items.next();
                         time = item.child("time").getValue(String.class);
                            String timeslot = item.child("timeslot").getValue(String.class);
                         String sc= item.child("status").getValue(String.class);


                        bookedby = item.child("bookedby").getValue(String.class);
                        scheduleArrayList.add(new schedule(time,sc,bookedby,mail,timeslot));
                        }
                        break;
                    }

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerAdapter = new scheduleAdapter(scheduleArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);







        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_search_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(profile.this,search.class));
            }
        });





        userRef.addValueEventListener(new ValueEventListener() {
            String uname;
            String email;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("email").getValue().equals(mail)) {
                        uname = ds.child("username").getValue(String.class);
                        email = ds.child("email").getValue(String.class);
                        break;
                    }

                }
                username.setText(uname);
                e_mail.setText(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
