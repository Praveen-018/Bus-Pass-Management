package com.example.buspass;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class home extends Fragment {
    private TextView idTextView;
    private TextView sourTextView;
    private TextView destiTextView;
    private TextView validity;


    public home() {
        // Required empty public constructor
    }

    public static home newInstance() {
        return new home();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find TextViews in the layout
        sourTextView = view.findViewById(R.id.sourcefromdb);
        destiTextView = view.findViewById(R.id.destinationfromdb);
        idTextView = view.findViewById(R.id.idfromdb);
        validity=view.findViewById(R.id.validityfromdb);
        // Retrieve the current user's UID from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            // Construct the DatabaseReference using the UID
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(uid);

            // Retrieve data from Firebase Realtime Database for multiple values
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Check if the snapshot exists
                    if (dataSnapshot.exists()) {
                        // Retrieve values for each field
                        String source = dataSnapshot.child("sour").getValue(String.class);
                        String destination = dataSnapshot.child("desti").getValue(String.class);
                        String id = dataSnapshot.child("name").getValue(String.class);
                        String purchaseDate = dataSnapshot.child("DateOfPurchase").getValue((String.class));
                        int days = 0;
                        long daysDifference = 0;
                        if(purchaseDate == null) {
                            days = 0;
                        }
                        else {
                            String currformattedDate = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                LocalDate currentDate = null;
                                currentDate = LocalDate.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                currformattedDate = currentDate.format(formatter);
                            }
                            DateTimeFormatter formatter = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate date1 = LocalDate.parse(purchaseDate, formatter);
                                LocalDate date2 = LocalDate.parse(currformattedDate, formatter);
                                daysDifference = ChronoUnit.DAYS.between(date2, date1);
                            }
                        }

                        if(30 - daysDifference > 0) {
                            days = (int) (30 - daysDifference);
                        }

                        // Set the retrieved values to corresponding TextViews
                        sourTextView.setText(source);
                        destiTextView.setText(destination);
                        idTextView.setText(id);
                        validity.setText(days + " days");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle errors
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        return view;
    }

}
