package com.example.autismapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismapp.R;

import java.util.ArrayList;
import java.util.Random;

public class Letters_complete extends AppCompatActivity {
    int randomNumber;
    int grade=0;
    int roundNumber=0;

    int[] pics = {R.drawable.chair, R.drawable.boy, R.drawable.cock, R.drawable.knight, R.drawable.shirt, R.drawable.socks
            , R.drawable.dog, R.drawable.cat, R.drawable.lion, R.drawable.fish, R.drawable.butterfly, R.drawable.bird
            , R.drawable.cow, R.drawable.horse, R.drawable.rabbit, R.drawable.elephant, R.drawable.bear, R.drawable.penguin
            , R.drawable.apple, R.drawable.cucumber, R.drawable.orange, R.drawable.banana, R.drawable.carrot, R.drawable.eggplant
            , R.drawable.squash, R.drawable.hammer, R.drawable.nail,  R.drawable.parcel, R.drawable.key, R.drawable.figure
            , R.drawable.table, R.drawable.cup, R.drawable.tree, R.drawable.flower, R.drawable.tooth, R.drawable.ear
            , R.drawable.eye, R.drawable.sun, R.drawable.moon, R.drawable.cloud, R.drawable.rain, R.drawable.snow, R.drawable.chalk
            , R.drawable.pilates, R.drawable.trampoline, R.drawable.collar, R.drawable.notebook, R.drawable.piano, R.drawable.food
            , R.drawable.book, R.drawable.television, R.drawable.telephone, R.drawable.north, R.drawable.south, R.drawable.east
            , R.drawable.west, R.drawable.sports, R.drawable.color, R.drawable.pool, R.drawable.playground, R.drawable.ball
            , R.drawable.track, R.drawable.beach, R.drawable.tower, R.drawable.counter, R.drawable.kitchen, R.drawable.song, R.drawable.family
            , R.drawable.party, R.drawable.icecream, R.drawable.station, R.drawable.music};
    String letters[][] = {
            {"hairc", "chair"}, {"yob", "boy"}, {"kcoc", "cock"},
            {"nigkht", "knight"}, {"hirts", "shirt"}, {"ssock", "socks"}, {"ogd", "dog"}, {"tac", "cat"}, {"loni", "lion"}, {"shif", "fish"}, {"flybutter", "butterfly"},
            {"dirb", "bird"}, {"woc", "cow"}, {"hores", "horse"}, {"bitrab", "rabbit"}, {"lephante", "elephant"}, {"earb", "bear"}, {"guinpen", "penguin"}, {"plepa", "apple"},
            {"cucumbre", "cucumber"}, {"rangeo", "orange"}, {"nanab", "banana"}, {"torrac", "carrot"}, {"eggplnat", "eggplant"}, {"uashqs", "squash"}, {"merham", "hammer"},
            {"lian", "nail"}, {"packgea", "package"}, {"yek", "key"}, {"figrue", "figure"}, {"bleta", "table"}, {"puc", "cup"}, {"eert", "tree"},
            {"flowre", "flower"},   {"thoot", "tooth"}, {"rae", "ear"}, {"yee", "eye"}, {"nus", "sun"}, {"noom", "moon"},
            {"duolc", "cloud"}, {"niar", "rain"}, {"wons", "snow"}, {"chakl", "chalk"}, {"pilatse", "pilates"}, {"polinetram", "trampoline"}, {"larcol", "collar"},
            {"booknote", "notebook"}, {"piona", "piano"},  {"doof", "food"}, {"koob", "book"},  {"televisoin", "television"},
            {"telepohne", "telephone"}, {"thron", "north"}, {"thuos", "south"}, {"aste", "east"}, {"estw", "west"}, {"ports", "sport"}, {"lorco", "color"},
            {"loop", "pool"}, {"playgronud", "playground"}, {"llab", "ball"}, {"rackt", "track"}, {"eachb", "beach"}, {"werot", "tower"}, {"ntercou", "counter"},
            {"kithcen", "kitchen"}, {"gons", "song"}, {"famiyl", "family"}, {"typar", "party"}, {"cream ice", "ice cream"}, {"tations", "station"},{"sicmu", "music"}
    };
    ArrayList<Integer> optionsList= new ArrayList<Integer>( );
    TextView hebrewText;
    ImageView imgView;
    EditText AnswerEnglish;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EndGame();
    }
    //after replacing letter , view should change
    public void SetController(int letterindex)
    {
        imgView.setBackground(getResources().getDrawable(pics[letterindex]));
        hebrewText.setText(letters[letterindex][0]);
        AnswerEnglish.setText("");
    }
    //for controlling on non repeating letters,we removed letter that we see ,if all done return flag
    public int RandNumber()
    {
        if(optionsList.size()!=0&& !(roundNumber>=10)) {
            int randomNumberIndex = new Random().nextInt(optionsList.size());
            randomNumber=optionsList.get(randomNumberIndex);
            optionsList.remove(randomNumberIndex);
            roundNumber++;
            return randomNumber;
        }else
            return -1;
    }
    public void EndGame()
    {
        Intent HomePage =new Intent(Letters_complete.this, MainActivity.class);
        startActivity(HomePage);
        saveScore();
        finish();
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

    private void submitText(String text) {
        String ans=text.toLowerCase().trim();
        if(letters[randomNumber][1].equals(ans))
        {
            // in case you right
            grade++;
            try {
                //put it in green when answer is right
                AnswerEnglish.setBackgroundColor(Color.parseColor("#5FEF37"));
                AnswerEnglish.setText("you right!");
                AnswerEnglish.setTypeface(null, Typeface.BOLD);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnswerEnglish.setBackgroundColor(Color.WHITE);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AnswerEnglish.setBackgroundColor(Color.parseColor("#5FEF37"));
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        AnswerEnglish.setBackgroundColor(Color.WHITE);
                                    }
                                }, 100);
                            }
                        }, 100);
                    }
                }, 100);


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            AnswerEnglish.setText("");
            AnswerEnglish.setTypeface(Typeface.DEFAULT);
            randomNumber=RandNumber();
            if(randomNumber==-1) {
                EndGame();
                finish();
                return;
            }
            SetController(randomNumber);


        }else//in case you faild
        {
            AnswerEnglish.setBackgroundColor(Color.parseColor("#FF0000"));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AnswerEnglish.setBackgroundColor(Color.WHITE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AnswerEnglish.setBackgroundColor(Color.parseColor("#FF0000"));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AnswerEnglish.setBackgroundColor(Color.WHITE);
                                }
                            }, 150);
                        }
                    }, 150);
                }
            }, 150);
        }
        AnswerEnglish.setText("");
        AnswerEnglish.setTypeface(Typeface.DEFAULT);
        randomNumber=RandNumber();
        if(randomNumber==-1) {
            EndGame();
            finish();
            return;
        }
        SetController(randomNumber);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters_complete);
        //init the available colors to use
        for (int i = 0; i < letters.length; i++) {
            optionsList.add(i);
        }
        hebrewText=findViewById(R.id.letters_complete_hebrew);
        imgView = findViewById(R.id.color_complete_img);
        AnswerEnglish=findViewById(R.id.answer_text_letters);
        randomNumber=RandNumber();
        if(randomNumber==-1) {
            EndGame();
            finish();
            return;
        }
        SetController(randomNumber);
        AnswerEnglish.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Check if the Enter key is pressed
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    // Perform the submission logic here
                    String text = AnswerEnglish.getText().toString();
                    submitText(text);
                    return true;
                }
                return false;
            }
        });

        getSupportActionBar().setTitle("Words");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

}