package com.example.buspass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText emailfromuser, passworduser;
    Button login1;
    TextView regtext;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailfromuser = findViewById(R.id.useremail);
        passworduser = findViewById(R.id.userpass);
        login1 = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        regtext = findViewById(R.id.registertext);

        // Check if user is already authenticated
        if (mAuth.getCurrentUser() != null) {
            // User is already logged in, redirect to main activity
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Click listener for the "Register" text
        regtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register1.class));
            }
        });
    }

    private void loginUser() {
        String email = emailfromuser.getText().toString().trim();
        String password = passworduser.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailfromuser.setError("Email can't be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passworduser.setError("Password can't be empty");
            return;
        }

        // Sign in user with Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Authentication success.",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
