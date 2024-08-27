package com.example.buspass;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

   TextView profile,name1,ph1,email1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find TextViews in the layout
        profile = view.findViewById(R.id.usernamefromdb);
        name1 = view.findViewById(R.id.Namefromdb);
        ph1 = view.findViewById(R.id.Phnofromdb);
        email1 = view.findViewById(R.id.mailfromdb);

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
                        String prof = dataSnapshot.child("userName").getValue(String.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        String phone = dataSnapshot.child("ph").getValue(String.class);

                        // Set the retrieved values to corresponding TextViews
                        profile.setText(prof);
                        name1.setText(name);
                        email1.setText(email);
                        ph1.setText(phone);
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