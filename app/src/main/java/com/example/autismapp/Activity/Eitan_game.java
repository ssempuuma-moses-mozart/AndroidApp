package com.example.autismapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismapp.R;

import java.util.ArrayList;
import java.util.Random;

public class Eitan_game extends AppCompatActivity {

    String SentencesOpt[][] = {{"In the morning we __ to work", "get up", "get up", "get up"},
            {"After class I __ exercise", "I will", "will go", "I will go"},
            {"Are you __ to grandma's in the evening?", "come", "come", "come"},
            {"We __ to the north tomorrow", "went", "went", "went"},
            {"Tomorrow __ wine", "will drink", "will drink", "will drink"},
            {"Will you __ order a pizza with olives in the evening?", "will order", "will order", "will order"},
            {"He __ to class tomorrow don't worry", "will come", "come", "will come"},
            {"She __ the pots in the morning", "watered", "kissed", "watered"},
            {"We __ to the party tomorrow", "we will go out", "they went out", "we will go out"},
            {"The baby __ milk before bed", "takes", "yuck", "yuck"},
            {"__ the message when I turn", "I will listen", "I will listen", "I will listen"},
            {"We __ later to you", "we will join", "join", "we will join"},
            {"you __ later?", "call", "I'll call", "call"},
            {"I'll __ you at 8 p.m.", "I'll pick up", "I'll pick up", "I'll pick up"},
            {"Are you __ ready on time?", "Be", "I will be", "Be"},
            {"Alex and David __ the rest of the work", "will do", "will do", "will do"},
            {"Dor and Linui __ at the end of the year", "they will get married", "we will get married", "they will get married"},
            {"Yossi __ the DJ for Dor's wedding", "will close", "will close", "will close"},
            {"Yovel and Roy __ from the army at the end of the year", "will be released", "will be released", "will be released"},
            {"they __ belt soon", "will cut", "we will cut", "will cut"},
            {"So __ appointment for another two weeks?", "to be determined", "to be determined", "to be determined"},
            {"The doctor __ a prescription for treatment", "will give", "will give", "will give"},
            {"The children __ to Noa's birthday", "will be invited", "will be invited", "will be invited"},
            {"We __ the job successfully", "will finish", "finish", "will finish"},
            {"Soon we will __ the degree", "we will finish", "they will finish", "we will finish"},
            {"When __ is big", "I will be", "will be", "I will be"},
            {"We __ for grades at work", "we will wait", "I will wait", "we will wait"},
            {"we all __ work at the end", "found", "found", "found"} };
    ArrayList<Integer> optionsList= new ArrayList<Integer>( );
    ImageView v;
    ImageView x;
    TextView Sentence;
    Button OptionOne;
    Button OptionTwo;
    int randomNumber;
    int grade=0;
    int roundNumber=0;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EndGame();
    }

    // the function set on the controller the values and the answer , the location of the right ans change any time randomly
    public void SetControllers(int SentIndex)
    {
        //randomaly we choose the two options locations -random -1 or 2
        int randomNumber = new Random().nextInt(2)+1;
        Sentence.setText(SentencesOpt[SentIndex][0]);
        OptionOne.setText(SentencesOpt[SentIndex][randomNumber]);
        OptionTwo.setText(SentencesOpt[SentIndex][3-randomNumber]);
    }

    //take randomal number from the available one ,and set it and the controllers
    public int SetRound()
    {
        if(optionsList.size()!=0&& !(roundNumber>=10)) {
            int randomNumberIndex = new Random().nextInt(optionsList.size());
            randomNumber=optionsList.get(randomNumberIndex);
            optionsList.remove(randomNumberIndex);
            SetControllers(randomNumber);
            roundNumber++;
            return randomNumber;
        }else
            return -1;
    }
    //end game and return to the menu
    public void EndGame()
    {
        Intent HomePage =new Intent(Eitan_game.this, MainActivity.class);
        saveScore();
        startActivity(HomePage);
    }

    public void saveScore()
    {
        SharedPreferences sp = getSharedPreferences("ChildrenGameScore", MODE_PRIVATE);
        int savedValue = sp.getInt("key", 0);
        SharedPreferences.Editor sedt = sp.edit();
        sedt.putInt("key", savedValue+grade);
        sedt.commit();
        Toast.makeText(this, "You got: " + grade + " points", Toast.LENGTH_SHORT).show();

    }
    //this animation should show after every press , its fades in and then out , and then we skip to the next question/end game
    private void startFadeAnimation() {

        // Fade in animation
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(1000);
        fadeInAnimator.setInterpolator(new AccelerateInterpolator());

        // Fade out animation
        ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(v, "alpha", 1f, 0f);
        fadeOutAnimator.setDuration(1000);
        fadeOutAnimator.setInterpolator(new AccelerateInterpolator());

        // Chain the animations
        fadeInAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // After the fade in animation ends, start the fade out animation
                fadeOutAnimator.start();
            }
        });

        fadeOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // After the fade out animation ends, hide the image
                v.setVisibility(View.GONE);
                if(SetRound()==-1)
                {
                    EndGame();
                }
            }
        });

        // Start the fade in animation
        fadeInAnimator.start();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eitan_game);
        Sentence=findViewById(R.id.Eitan_game_text);
        OptionOne=findViewById(R.id.Eitan_btn1);
        OptionTwo=findViewById(R.id.Eitan_btn2);
        v=findViewById(R.id.v_img);
        //init the available options
        for (int i = 0; i < SentencesOpt.length; i++) {
            optionsList.add(i);
        }
        //init the first load
        SetRound();

        //in case of click on btn1
        findViewById(R.id.Eitan_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View z) {
                if(OptionOne.getText().equals(SentencesOpt[randomNumber][3]))
                {
                    //its true give a grade and show v
                    grade++;
                    v.setBackgroundResource(R.drawable.checkmarkvv);
                    v.setVisibility(View.VISIBLE);
                    startFadeAnimation();

                }
                else
                {
                    //its false show x
                    v.setBackgroundResource(R.drawable.xmark);
                    v.setVisibility(View.VISIBLE);
                    startFadeAnimation();


                }
            }
        });
        //in case of click on btn2
        findViewById(R.id.Eitan_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View z) {
                if(OptionTwo.getText().equals(SentencesOpt[randomNumber][3]))
                {
                    //its true give a grade and show v
                    grade++;
                    v.setBackgroundResource(R.drawable.checkmarkvv);
                    v.setVisibility(View.VISIBLE);
                    startFadeAnimation();


                }
                else
                {
                    //its false show x
                    v.setBackgroundResource(R.drawable.xmark);
                    v.setVisibility(View.VISIBLE);
                    startFadeAnimation();

                }




            }

        });

        getSupportActionBar().setTitle("Answers");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

}