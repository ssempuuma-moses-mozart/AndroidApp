package com.example.autismapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismapp.R;

public class MainActivity extends AppCompatActivity {
    int SMS_REQUEST = 1;

    TextView scoreView;
    int totalScore;

    @Override
    public void onBackPressed() {
        // Close the app
        super.onBackPressed();
        finishAffinity();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("ChildrenGameScore", MODE_PRIVATE);
        SharedPreferences.Editor sedt = sp.edit();
        sedt.putInt("key",0);
        scoreView=findViewById(R.id.total_score);

        loadScore();

        getSupportActionBar().setTitle("EarlySteps Autism Aid");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //connecting button click to memory game
        findViewById(R.id.card_view_correct_the_word).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent letterComplete =new Intent(MainActivity.this, Letters_complete.class);
                startActivity(letterComplete);

            }
        });
        //connecting button click to word_complete game
        findViewById(R.id.card_view_complete_the_sentence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sentenceComplete =new Intent(MainActivity.this, Eitan_game.class);
                startActivity(sentenceComplete);

            }
        });
        //connecting button click to color game
        findViewById(R.id.card_view_guess_the_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorGame =new Intent(MainActivity.this, Color_Game.class);
                startActivity(colorGame);

            }
        });
        //connecting button click to Eitan game
        findViewById(R.id.card_view_watch_the_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Eitan_game =new Intent(MainActivity.this,Eitan_game.class);
                startActivity(Eitan_game);

            }
        });

        //connecting button click to sens SMS
        findViewById(R.id.card_view_send_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Send_SMS =new Intent(MainActivity.this, com.example.autismapp.Activity.Send_SMS.class);
                if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Send_SMS);
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            android.Manifest.permission.SEND_SMS,
                    },SMS_REQUEST);

                }
            }
        });

        findViewById(R.id.card_view_watch_the_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VidAc =new Intent(MainActivity.this, Video.class);
                startActivity(VidAc);

            }
        });

        findViewById(R.id.card_view_make_calls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VidAc =new Intent(MainActivity.this, MathActivity.class);
                startActivity(VidAc);

            }
        });
    }

    public void loadScore()
    {
        SharedPreferences sp = getSharedPreferences("ChildrenGameScore", 0);
        //totalScore = sp.getInt("key", 0) + sp.getInt("EitanScore", 0) + sp.getInt("LettersScore", 0);
        totalScore=sp.getInt("key",0);
        scoreView.setText("Total Score: " + totalScore);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Reset the score to 0 when the activity is destroyed (app is closed)
//        resetScore();
//    }
//
//    private void resetScore() {
//        SharedPreferences sp = getSharedPreferences("ChildrenGameScore", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt("key", 0); // Resetting the score to 0
//        editor.apply();
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent Send_SMS = new Intent(MainActivity.this, Send_SMS.class);
//                startActivity(Send_SMS);
            } else
                Toast.makeText(MainActivity.this, "Need Permissions", Toast.LENGTH_LONG).show();
        }

    }

    // menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intentAbout = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intentAbout);
                return true;
//            case R.id.manageaccount:
//                //do something
//                Intent intentHelp = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intentHelp);
//
//                return true;
            case R.id.refresh:
                resetScore();
                scoreView.setText("Total Score: 0"); // Update the TextView displaying the score to "0"
                Toast.makeText(getApplicationContext(), "Score reset to 0", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetScore() {
        SharedPreferences sp = getSharedPreferences("ChildrenGameScore", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("key", 0); // Resetting the score to 0
        editor.apply();
    }
}