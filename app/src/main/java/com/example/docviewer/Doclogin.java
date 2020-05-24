package com.example.docviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Doclogin extends AppCompatActivity {
EditText email;
EditText password;
    FirebaseAuth fireAuth;
    FirebaseAuth.AuthStateListener listener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doclogin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fireAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString() == "" || password.getText().toString() == "") {
                    Toast.makeText(Doclogin.this, "Email and password fields can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(Doclogin.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });

                }
            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
       // fireAuth.addAuthStateListener(listener);
    }
    public void  updateUI(FirebaseUser account){
        if(account != null){
           //
            Intent intent=new Intent(Doclogin.this,profile.class);
            intent.putExtra("email",account.getEmail());

            startActivity(intent);
        }else {
            Toast.makeText(this,"Wrong Email or password",Toast.LENGTH_LONG).show();
        }
    }
}
