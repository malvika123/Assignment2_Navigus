package com.example.docviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
EditText username;
EditText email;
EditText password;
Button signup;
    HashMap hMap;
HashMap map;
FirebaseAuth fireAuth;
    DatabaseReference myRef;
    member m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        fireAuth=FirebaseAuth.getInstance();

         myRef = FirebaseDatabase.getInstance().getReference().child("Members");
      findViewById(R.id.log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString() == "" || password.getText().toString() == "") {
                    Toast.makeText(MainActivity.this, "Email and password fields can't be empty", Toast.LENGTH_SHORT).show();
                }else {
                    fireAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Log.d("result", "createUserWithEmail:success");
                                        FirebaseUser user = fireAuth.getCurrentUser();
                                       m=new member(email.getText().toString().trim(),username.getText().toString().trim(),password.getText().toString().trim());

                                        String newKey=myRef.push().getKey();
                                       myRef.child(newKey).setValue(m);
                                        for(int i=1;i<=24;i++){
                                            map= new HashMap<>();
                                            String str=String.valueOf(i);
                                            map.put("time",str);
                                            map.put("status","true");
                                            map.put("booked by","email");
                                            map.put("timeslot",str);
                                            myRef.child(newKey).child("table").child(myRef.push().getKey()).setValue(map);
                                        }

                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }

            }
        });

        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent= new Intent(MainActivity.this,Doclogin.class);
             startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
    public void  updateUI(FirebaseUser account){
        if(account != null){
           // Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Doclogin.class));
        }else {
            Toast.makeText(MainActivity.this,"Something went well",Toast.LENGTH_LONG).show();

        }
    }
}

// String key=fireAuth.getCurrentUser().getUid();



//                                        map.put("01-02",false); map.put("02-03",false);
//                                        map.put("03-04",false);
//                                        map.put("04-05",false);
//                                        map.put("05-06",false);
//                                        map.put("06-07",false);
//                                        map.put("07-08",false);
//                                        map.put("08-09",false);
//                                        map.put("19-10",false);
//                                        map.put("10-11",false);
//                                        map.put("11-12",false);
//                                        map.put("12-13",false);
//                                        map.put("13-14",false);
//                                        map.put("14-15",false);
//                                        map.put("15-16",false);
//                                        map.put("16-17",false);
//                                        map.put("17-18",false);
//                                        map.put("18-19",false);
//                                        map.put("19-20",false);
//                                        map.put("20-21",false);
//                                        map.put("21-22",false);
//                                        map.put("22-23",false);
//                                        map.put("23-00",false);
//                                        map.put("00-01",false);
