package com.example.autismapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.autismapp.R;

import java.util.Objects;

public class MathResultActivity extends AppCompatActivity {

    TextView textResult;
    int great;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_result);

        getSupportActionBar().setTitle("Math Questions Results");
        getSupportActionBar().setDisplayShowHomeEnabled(true);



//        Objects.requireNonNull(getSupportActionBar()).hide();

//        great = getIntent().getIntExtra("RA", 0);
//        textResult = findViewById(R.id.textResult);
//
//        textResult.setText("You answered "+ great + " / 10");

        // ... (other initialization code)

        great = getIntent().getIntExtra("RA", 0);
        textResult = findViewById(R.id.textResult);

        textResult.setText("You answered " + great + " / 10");

        // Check if correct answers are greater than 5
        if (great > 5) {
            showCongratulationsDialog();
        }
    }

    // Method to show the congratulatory dialog with a smiley icon
    private void showCongratulationsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!");
        builder.setMessage("You've answered " + great + " questions correctly! Great job! 😊");
        builder.setPositiveButton("OK", null); // You can add a button action if needed

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}