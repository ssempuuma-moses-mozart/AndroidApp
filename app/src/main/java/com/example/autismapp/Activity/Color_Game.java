package com.example.autismapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismapp.R;

import java.util.ArrayList;
import java.util.Random;

public class Color_Game extends AppCompatActivity {

    MediaPlayer mySong;
    boolean FirstTime=false;
    int randomNumber;
    int grade=0;
    int roundNumber=0;
    int BackgroundColors[] = {0xFFFF0000, 0xFF008000, 0xFF0000FF, 0xFFFFFF00, 0xFFFFA500, 0xFFFFC0CB, 0xFF800080, 0xFFFFFFFF, 0xFF000000, 0xFF808080, 0xFF964B00, 0xFF40E0D0, 0xFF00FFFF, 0xFFFFD700, 0xFFC0C0C0, 0xFF00FFFF};
    String HebrewColors[]={"Red, Green", "Blue, Green", "Blue, Yellow", "Orange, Yellow", "Pink, Orange", "Pink, White", "Purple, Black", "White, Grey", "Black, Brown", "Grey, Turquoise", "Aqua, Brown", "Turquoise, Gold", "Silver, Aqua " , "Grey, gold", "Red, silver", "Grey, silver"};
    String EnglishColors[]={"red" ,"green","blue","yellow","orange","pink","purple", "white" , "black" , "grey" , "brown" , "turquoise" , "aqua" , "gold", "silver", "white"};
    ArrayList<Integer> optionsList= new ArrayList<Integer>( );

    TextView hebrewText;
    TextView ColorBackground;
    EditText AnswerEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mySong = MediaPlayer.create(Color_Game.this, R.raw.letslearncolors);
        mySong.start(); // Start playing the song when the activity is created
        //red ,green,blue,yellow,orange,pink,purple,white,black,grey,brown,turquoise,aqua,gold,silver
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_game);
        //init the available colors to use
        for (int i = 0; i < BackgroundColors.length; i++) {
            optionsList.add(i);
        }
        hebrewText=findViewById(R.id.color_hebrew);
        ColorBackground=findViewById(R.id.color_game_img);
        AnswerEnglish=findViewById(R.id.answer_text_color);
        //if we already was at the game and just rotate the screen , we need to restore the game
        if(savedInstanceState!=null)
        {
            randomNumber = savedInstanceState.getInt("randomNumber");
            FirstTime=savedInstanceState.getBoolean("FirstTime");

            if(savedInstanceState.containsKey("optionsList"))
                optionsList=savedInstanceState.getIntegerArrayList("optionsList");

        }
        else
        {
            //        optionsList=new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14) );
        }

        if(!FirstTime)
        {
            randomNumber=RandNumber();
            if(randomNumber==-1) {
                EndGame();
                finish();
                return;
            }
            SetController(randomNumber);
            FirstTime=true;
        }

        AnswerEnglish.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Check if the Enter key is pressed
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    // Perform the submission logic here
                    String text = AnswerEnglish.getText().toString();
                    checkAns();
                    return true;
                }
                return false;
            }
        });

       /* findViewById(R.id.Color_Btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans=AnswerEnglish.getText().toString().toLowerCase().trim();
                if(EnglishColors[randomNumber].equals(ans))
                {// in case you right
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
                                AnswerEnglish.setBackgroundColor(Color.TRANSPARENT);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        AnswerEnglish.setBackgroundColor(Color.parseColor("#5FEF37"));
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                AnswerEnglish.setBackgroundColor(Color.TRANSPARENT);
                                            }
                                        }, 100);
                                    }
                                }, 100);
                            }
                        }, 100);


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else//in case you faild
                {
                    AnswerEnglish.setBackgroundColor(Color.parseColor("#FF0000"));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AnswerEnglish.setBackgroundColor(Color.TRANSPARENT);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AnswerEnglish.setBackgroundColor(Color.parseColor("#FF0000"));
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            AnswerEnglish.setBackgroundColor(Color.TRANSPARENT);
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
        });*/


        getSupportActionBar().setTitle("Colors");
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EndGame();
    }

    //after replacing color , view should change
    public void SetController(int color)
    {
        ColorBackground.setBackgroundColor(BackgroundColors[color]);
        hebrewText.setText(HebrewColors[color]);
    }
    //for controlling on non repeating colors,we removed color that we see ,if all done return flag
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
        Intent HomePage =new Intent(Color_Game.this, MainActivity.class);
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
    public void checkAns()
    {
        String ans = AnswerEnglish.getText().toString().toLowerCase().trim();
        if (EnglishColors[randomNumber].equals(ans)) {// in case you right
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
        } else//in case you faild
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
        randomNumber = RandNumber();
        if (randomNumber == -1) {
            EndGame();
            finish();
            return;
        }
        SetController(randomNumber);




    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the ArrayList to the instance state
        outState.putIntegerArrayList("optionsList", optionsList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mySong != null && mySong.isPlaying()) {
            mySong.pause(); // Pauses the song when the activity loses focus
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mySong != null) {
            mySong.release(); // Releases resources when the activity is destroyed
        }
    }
}