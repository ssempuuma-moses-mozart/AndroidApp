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
import com.example.autismapp.Model.UserTypers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private EditText userName, emailTV, passwordTV,confirmpasswordTV;
    private Button regBtn;
    // private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    //    String role;
    private TextView signInTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signInTextView = findViewById(R.id.signInText);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();

        initializeUI();
        regBtn.findViewById(R.id.signUpBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent VidAc =new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(VidAc);
            }
        });
    }

    private void registerNewUser() {
        //  progressBar.setVisibility(View.VISIBLE);

        String username, email, password, confirmpassword;
        username = userName.getText().toString();
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();
        confirmpassword = confirmpasswordTV.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please enter username...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(getApplicationContext(), "Please enter and confirm your password!", Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(confirmpassword)){
            Toast.makeText(SignUpActivity.this,"Password Don't match",Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            progressDialog.setTitle("Creating Account");
            progressDialog.setMessage("Please wait,While we are creating your Account...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String uid = task.getResult().getUser().getUid();

                                UserTypers user = new UserTypers(uid, username, email, password, 0);
                                firebaseDatabase.getReference().child("User").child(uid).setValue(user);


                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();

                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                // progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }

    private void initializeUI() {
        userName = findViewById(R.id.editUserName);
        emailTV = findViewById(R.id.editEmailSignUp);
        passwordTV = findViewById(R.id.editPassSignUp);
        regBtn = findViewById(R.id.signUpBtn);
        confirmpasswordTV=findViewById(R.id.editConfirmPassSignUp);
        // progressBar = findViewById(R.id.progressBar);
        progressDialog =new ProgressDialog(this);
    }



}