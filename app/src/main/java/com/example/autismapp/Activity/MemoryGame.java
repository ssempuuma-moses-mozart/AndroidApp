package com.example.autismapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.autismapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MemoryGame extends AppCompatActivity {

    private List<Integer> cardNames; // List to store card names
    private List<Button> buttons; // List to store card buttons
    private List<Button> selectedCards; // List to store currently selected cards
    private int roundsRemaining; // Number of rounds remaining
    private int grade; // Player's score
    private int matches;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        endGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        initializeGame();
        startRound();

        getSupportActionBar().setTitle("Game");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initializeGame() {
        // Initialize card names
        cardNames = Arrays.asList(
                R.drawable.three_of_diamonds, R.drawable.three_of_hearts,
                R.drawable.three_of_spades, R.drawable.four_of_clubs,
                R.drawable.four_of_diamonds, R.drawable.four_of_hearts,
                R.drawable.four_of_spades, R.drawable.five_of_clubs,
                R.drawable.five_of_diamonds, R.drawable.five_of_hearts,
                R.drawable.five_of_spades, R.drawable.six_of_clubs,
                R.drawable.six_of_diamonds, R.drawable.six_of_hearts,
                R.drawable.six_of_spades, R.drawable.seven_of_clubs,
                R.drawable.seven_of_diamonds, R.drawable.seven_of_hearts,
                R.drawable.seven_of_spades, R.drawable.eight_of_clubs,
                R.drawable.eight_of_diamonds, R.drawable.eight_of_hearts,
                R.drawable.eight_of_spades, R.drawable.nine_of_clubs,
                R.drawable.nine_of_diamonds, R.drawable.nine_of_hearts,
                R.drawable.nine_of_spades, R.drawable.ten_of_clubs,
                R.drawable.ten_of_diamonds, R.drawable.ten_of_hearts,
                R.drawable.ten_of_spades, R.drawable.ace_of_clubs,
                R.drawable.three_of_clubs, R.drawable.ace_of_diamonds,
                R.drawable.two_of_spades, R.drawable.two_of_hearts,
                R.drawable.two_of_diamonds, R.drawable.two_of_clubs,
                R.drawable.jack_of_hearts, R.drawable.black_joker,
                R.drawable.jack_of_clubs, R.drawable.king_of_hearts,
                R.drawable.king_of_diamonds, R.drawable.king_of_clubs,
                R.drawable.jack_of_spades, R.drawable.jack_of_hearts,
                R.drawable.jack_of_diamonds, R.drawable.queen_of_clubs,
                R.drawable.king_of_spades, R.drawable.red_joker,
                R.drawable.queen_of_spades, R.drawable.queen_of_hearts,
                R.drawable.queen_of_diamonds, R.drawable.ace_of_spades,
                R.drawable.ace_of_hearts

        );

        // Initialize card buttons
        buttons = new ArrayList<>();
        buttons.add(findViewById(R.id.card1));
        buttons.add(findViewById(R.id.card2));
        buttons.add(findViewById(R.id.card3));
        buttons.add(findViewById(R.id.card4));
        buttons.add(findViewById(R.id.card5));
        buttons.add(findViewById(R.id.card6));
        buttons.add(findViewById(R.id.card7));
        buttons.add(findViewById(R.id.card8));


        // Set click listeners for card buttons
        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleCardClick(button);
                }
            });
        }

        // Initialize other variables
        selectedCards = new ArrayList<>();
        roundsRemaining = 3;
        grade = 0;
    }
    private List<Integer> getRandomUniqueCards(int count) {
        List<Integer> allCards = new ArrayList<>(cardNames);
        Collections.shuffle(allCards);

        return allCards.subList(0, count);
    }
    private void hideCards() {
        // Set default background to hide the cards
        for (Button button : buttons) {
            button.setBackgroundResource(R.drawable.card_back);
            button.setEnabled(true);

        }
    }
    private void showCards() {
        // Randomly select 4 unique card names
        List<Integer> selectedNames = getRandomUniqueCards(buttons.size()/2);
        ArrayList<Integer> available = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        for(int i = 0; i < buttons.size()/2; i++)
        {
            //generate two cards locations ,place them with img
            int randomIndex = new Random().nextInt(available.size());
            int firstIndex = available.get(randomIndex);
            available.remove(randomIndex);
            Integer cardName = selectedNames.get(i);
            buttons.get(firstIndex).setBackgroundResource(cardName);
            buttons.get(firstIndex).setTag(cardName);
            buttons.get(firstIndex).setVisibility(View.VISIBLE);


            randomIndex = new Random().nextInt(available.size());
            int secondIndex = available.get(randomIndex);
            available.remove(randomIndex);
            cardName = selectedNames.get(i);
            buttons.get(secondIndex).setBackgroundResource(cardName);
            buttons.get(secondIndex).setTag(cardName);
            buttons.get(secondIndex).setVisibility(View.VISIBLE);

        }


    }

    private void startRound() {
        // Show the cards for 1.5 seconds
        showCards();

        // Delay for 150 seconds and then hide the cards
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideCards();
            }
        }, 1500);
        matches=0;
    }





    private void handleCardClick(Button button) {
        // If the selected card is already revealed, ignore the click
        if (selectedCards.contains(button)) {
            return;
        }

        // Reveal the selected card
        Integer cardName = (Integer) button.getTag();
        if(cardName!=null) {
            button.setBackgroundResource(cardName);

            // Add the selected card to the list
            selectedCards.add(button);

            // If two cards are selected, check for a match
            if (selectedCards.size() == 2) {
                checkMatch();
            }
        }

    }

    private void checkMatch() {
        Button card1 = selectedCards.get(0);
        Button card2 = selectedCards.get(1);

        // Check if the selected cards match
        if (card1.getTag().equals(card2.getTag())) {
            // Match found, increment the score and disable the matched cards
            matches++;
            card1.setEnabled(false);
            card2.setEnabled(false);
            new Handler().postDelayed(() ->{
                card1.setVisibility(View.INVISIBLE);
                card2.setVisibility(View.INVISIBLE);
                selectedCards.clear();
            },200);

        } else {
            // No match, hide the selected cards after a short delay
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    card1.setBackgroundResource(R.drawable.card_back);
                    card2.setBackgroundResource(R.drawable.card_back);
                    selectedCards.clear();
                }
            }, 250);
        }
        new Handler().postDelayed(() ->{
            if(matches==4)
            {
                grade++;
                roundsRemaining--;
                // Check if the game is over
                if (roundsRemaining == 0) {
                    endGame();
                }
                else
                    startRound();

            }
        },500);




    }

    private void endGame() {
        Intent homePage = new Intent(MemoryGame.this, MainActivity.class);
        saveScore();
        startActivity(homePage);
    }

    private void saveScore() {
        SharedPreferences sp = getSharedPreferences("ChildrenGameScore", MODE_PRIVATE);
        int savedValue = sp.getInt("key", 0);
        SharedPreferences.Editor sedt = sp.edit();
        sedt.putInt("key", savedValue + grade);
        sedt.apply();
        Toast.makeText(this, "You got: " + grade + " points", Toast.LENGTH_SHORT).show();
    }


}
