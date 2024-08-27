package com.example.buspass;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register1 extends AppCompatActivity {

    EditText Emailreg, passreg;
    Button reg1;
    TextView log;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Emailreg = findViewById(R.id.editTextTextEmailAddress);
        passreg = findViewById(R.id.passwordemail);
        reg1 = findViewById(R.id.register1);
        log = findViewById(R.id.signupText);

        mAuth = FirebaseAuth.getInstance();

        reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register1.this, Login.class));
            }
        });
    }

    private void registerUser() {
        String email = Emailreg.getText().toString().trim();
        String password = passreg.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Emailreg.setError("Email can't be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passreg.setError("Password can't be empty");
            return;
        }

        // Register user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register1.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Sign up success, retrieve current user's UID
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            // Pass UID to the next activity
                            Intent intent = new Intent(Register1.this, register.class);
                            intent.putExtra("uid", uid);
                            startActivity(intent);

                            // Finish current activity to prevent back navigation
                            finish();
                        } else {
                            // If sign up fails, display a message to the user.
                            Snackbar.make(findViewById(android.R.id.content), "Registration Not Successful", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
