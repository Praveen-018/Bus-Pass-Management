package com.example.buspass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    EditText Name, userName, ph, sour, desti;
    Button reg;
    DatabaseReference reference;
    String uid; // Variable to store the UID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name = findViewById(R.id.name);
        userName = findViewById(R.id.username);
        ph = findViewById(R.id.Phno);
        reg = findViewById(R.id.register);
        sour = findViewById(R.id.source);
        desti = findViewById(R.id.destination);

        // Get the UID from the previous activity
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
    }

    private void insert() {
        String name1 = Name.getText().toString();
        String username1 = userName.getText().toString();
        String ph1 = ph.getText().toString();
        String sour1 = sour.getText().toString();
        String desti1 = desti.getText().toString();

        // Create a new Users object with the provided user data
        Users users = new Users(name1, username1, ph1, sour1, desti1);

        // Set the validity field to 0
        users.setValidity("0");

        // Save the user data to the database
        reference.setValue(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle successful write operation
                        Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                        startActivity(loginIntent);
                        finish(); // Finish current activity to prevent back navigation
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failed write operation
                        Toast.makeText(register.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
