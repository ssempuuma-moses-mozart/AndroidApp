package com.example.autismapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.autismapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorHomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        getSupportActionBar().setTitle("Doctor Menu");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigator);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.homeMenu:
                                // Handle item 1 click
                                // Do something when item 1 is clicked
                                mAuth.signOut();
                                Intent VidAc =new Intent(DoctorHomeActivity.this, SignInActivity.class);
                                startActivity(VidAc);
                                Toast.makeText(DoctorHomeActivity.this, "You have Signed Out", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.chat:
                                // Handle item 2 click
                                // Do something when item 2 is clicked
                                Intent intent =new Intent(DoctorHomeActivity.this, ChatActivity.class);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                }
        );
    }
}