package com.example.autismapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.autismapp.R;

import java.util.Random;

public class MathActivity extends AppCompatActivity {

    private TextView quizTime;
    TextView textLevel;
    TextView textRightAnswered;
    TextView textQuestion;

    Button buttonOp1;
    Button buttonOp2;
    Button buttonOp3;

    int level = 0;
    int great = 0;
    int rightAnswer = 0;
    String realOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
//        Objects.requireNonNull(getSupportActionBar()).hide();

        getSupportActionBar().setTitle("Math Questions");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        quizTime = findViewById(R.id.Quiztime);

        // Create rotation animation
        Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation indefinitely
        animation.setDuration(3000); // Set the duration of the animation in milliseconds

        quizTime.startAnimation(animation);



        textLevel = findViewById(R.id.textQuestionNumber);
        textRightAnswered = findViewById(R.id.textRightAnswered);
        textQuestion = findViewById(R.id.textQuestion);

        buttonOp1 = findViewById(R.id.buttonOption1);
        buttonOp2 = findViewById(R.id.buttonOption2);
        buttonOp3 = findViewById(R.id.buttonOption3);

        textLevel.setText("Q : " + level + " / 10");
        textRightAnswered.setText("RA " + great + " / 10");


        if (level < 10) {
            getARandomQuestion();
        }


    }

    private void getARandomQuestion() {

        buttonOp1.setBackgroundResource(R.drawable.buttons_option_bg);
        buttonOp2.setBackgroundResource(R.drawable.buttons_option_bg);
        buttonOp3.setBackgroundResource(R.drawable.buttons_option_bg);

        // get random number for question

        int firstNumber = new Random().nextInt(10);
        int secondNumber = new Random().nextInt(10);

        // get random operation (+ , - , *)

        int operation = new Random().nextInt(3) + 1;

        // get two options

        int optionA = new Random().nextInt(100); // max number 10 , 10 * 10 = 100 then we add 100 as a max number
        int optionB = new Random().nextInt(100);

        if (operation == 1) {
            realOperation = "+";
            rightAnswer = firstNumber + secondNumber;
            textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
        } else {
            if (operation == 2) {
                realOperation = "*";
                rightAnswer = firstNumber * secondNumber;
                textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
            } else {
                if (operation == 3) {
                    realOperation = "-";

                    if (firstNumber < secondNumber) {
                        rightAnswer = secondNumber - firstNumber;
                        textQuestion.setText(secondNumber + " " + realOperation + " " + firstNumber + " = ?");
                    } else {
                        rightAnswer = firstNumber - secondNumber;
                        textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
                    }
                }
            }
        }


        // get random position of right answer

        int position = new Random().nextInt(3) + 1; // 1 or 2 or 3 position

        if (position == 1) {
            buttonOp1.setText("" + rightAnswer);
            buttonOp2.setText("" + optionA);
            buttonOp3.setText("" + optionB);
        } else {
            buttonOp1.setText("" + optionA);
            if (position == 2) {
                buttonOp2.setText("" + rightAnswer);
                buttonOp3.setText("" + optionB);
            } else {
                buttonOp2.setText("" + optionB);
                buttonOp3.setText("" + rightAnswer);
            }
        }


        buttonOp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonOp1.getText().equals(""+rightAnswer)){
                    buttonOp1.setBackgroundResource(R.drawable.right_answer_bg);
                    great = great + 1;
                    level = level + 1;
                    textLevel.setText("Q : "+ level + " / 10");
                    textRightAnswered.setText("RA : "+ great + " / 10");
                }else {
                    level = level + 1;
                    textLevel.setText("Q : "+ level + " / 10");
                    buttonOp1.setBackgroundResource(R.drawable.wrong_answer_bg);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (level < 10){
                            getARandomQuestion();
                        }else {
                            Intent intent = new Intent(MathActivity.this, MathResultActivity.class);
                            intent.putExtra("RA", great);
                            startActivity(intent);
                            finish();
                        }

                    }
                },1000); // 1 sec
            }
        });


        buttonOp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonOp2.getText().equals(""+rightAnswer)){
                    buttonOp2.setBackgroundResource(R.drawable.right_answer_bg);
                    great = great + 1;
                    level = level + 1;
                    textLevel.setText("Q : "+ level + " / 10");
                    textRightAnswered.setText("RA : "+ great + " / 10");
                }else {
                    level = level + 1;
                    textLevel.setText("Q : "+ level + " / 10");
                    buttonOp2.setBackgroundResource(R.drawable.wrong_answer_bg);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (level < 10){
                            getARandomQuestion();
                        }else {
                            Intent intent = new Intent(MathActivity.this, MathResultActivity.class);
                            intent.putExtra("RA", great);
                            startActivity(intent);
                            finish();
                        }

                    }
                },1000); // 1 sec
            }
        });

        buttonOp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonOp3.getText().equals(""+rightAnswer)){
                    buttonOp3.setBackgroundResource(R.drawable.right_answer_bg);
                    great = great + 1;
                    level = level + 1;
                    textLevel.setText("Q : "+ level + " / 10");
                    textRightAnswered.setText("RA : "+ great + " / 10");
                }else {
                    level = level + 1;
                    textLevel.setText("Q : "+ level + " / 10");
                    buttonOp3.setBackgroundResource(R.drawable.wrong_answer_bg);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (level < 10){
                            getARandomQuestion();
                        }else {
                            Intent intent = new Intent(MathActivity.this, MathResultActivity.class);
                            intent.putExtra("RA", great);
                            startActivity(intent);
                            finish();
                        }

                    }
                },1000); // 1 sec
            }
        });
    }

    public void quiz(View view) {
        Intent VidAc =new Intent(MathActivity.this, QuizActivity.class);
        startActivity(VidAc);
    }
}