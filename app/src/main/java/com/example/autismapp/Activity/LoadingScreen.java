package com.example.autismapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.autismapp.R;

public class LoadingScreen extends AppCompatActivity {

    ImageView loadingPic;
    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        //Removing the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        loadingPic = (ImageView)findViewById(R.id.welcomeIMG);
        welcomeText = (TextView)findViewById(R.id.welcomeMSG);

        ObjectAnimator mover = ObjectAnimator.ofFloat(loadingPic, "rotation", 400f,
                0f);
        mover.setDuration(2500);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(loadingPic, "alpha", 0f, 1f);
        fadeIn.setDuration(2500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeIn).with(mover);

        ObjectAnimator movertxt = ObjectAnimator.ofFloat(welcomeText, "translationY", 400f,
                0f);
        movertxt.setDuration(2500);
        ObjectAnimator fadeIntxt = ObjectAnimator.ofFloat(welcomeText, "alpha", 0f, 1f);
        fadeIntxt.setDuration(2500);
        AnimatorSet animatorSettxt = new AnimatorSet();
        animatorSettxt.play(fadeIntxt).with(movertxt);

        animatorSettxt.start();
        animatorSet.start();

        //set thread to run this activity and then move to main
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3500);
                        Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}