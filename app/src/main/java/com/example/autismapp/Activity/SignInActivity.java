package com.example.autismapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class SignInActivity extends AppCompatActivity {

    private Button signUpBtn;
    private TextView signUpTextView;

    private EditText emailTV, passwordTV;
    private Button loginBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUpTextView = findViewById(R.id.signUpText);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();

        initializeUI();
        loginBtn.findViewById(R.id.signInBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent VidAc =new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(VidAc);
            }
        });
    }

    private void loginUserAccount() {

        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        else {
            progressDialog.setTitle("Logging In");
            progressDialog.setMessage("Please wait,While we are checking for your Account...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid =task.getResult().getUser().getUid();
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                firebaseDatabase.getReference().child("User").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        int usertype = snapshot.getValue(Integer.class);
                                        if (usertype == 0){
                                            Intent intent = new Intent(SignInActivity.this, ParentHomeActivity.class);
                                            startActivity(intent);
                                        }

                                        if (usertype == 1){
                                            Intent intent = new Intent(SignInActivity.this, DoctorHomeActivity.class);
                                            startActivity(intent);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });


                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();

                                // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                //startActivity(intent);
                                //  Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                // intent.putExtra("UserEmail", email);
                                // startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();
                            }
                        }
                    });

        }
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.emailEditSignIN);
        passwordTV = findViewById(R.id.passEditSignIn);

        loginBtn = findViewById(R.id.signInBtn);

        progressDialog =new ProgressDialog(this);
    }


    public void kidsgame(View view) {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}