package app.ie.mymagiccards.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import app.ie.mymagiccards.R;

public class LifeCounter extends AppCompatActivity {
    private int player1 = 20;
    private int player2 = 20;

    private Button playerOneMin, playerOnePlus, playerTwoMin, playerTwoPlus, dice, timer, coinFlip;
    private TextView lifeValue1, lifeValue2, buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);
        playerOneMin = findViewById(R.id.playerOneMinus);
        playerOnePlus = findViewById(R.id.playerOnePlus);
        playerTwoMin = findViewById(R.id.playerTwoMinus);
        playerTwoPlus = findViewById(R.id.playerTwoPlus);
        dice = findViewById(R.id.dice);
        timer = findViewById(R.id.timer);
        coinFlip = findViewById(R.id.coinFlip);
        lifeValue1 = findViewById(R.id.lifeValue1);
        lifeValue2 = findViewById(R.id.lifeValue2);
        buttonView = findViewById(R.id.buttonView);


        lifeValue1.setText(String.valueOf(player1));
        lifeValue2.setText(String.valueOf(player2));

        coinFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headsOrTails(view);
            }
        });

        playerOneMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1--;
                lifeValue1.setText(String.valueOf(player1));
            }
        });

        playerOnePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1++;
                lifeValue1.setText(String.valueOf(player1));
            }
        });

        playerTwoMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player2--;
                lifeValue2.setText(String.valueOf(player2));
            }
        });

        playerTwoPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player2++;
                lifeValue2.setText(String.valueOf(player2));
            }
        });

        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandNum(view);
            }
        });
    }


    public void generateRandNum(View view){
        Random rand = new Random();
        int number = rand.nextInt(20)+1;
        buttonView.setText("You rolled " +String.valueOf(number));
    }

    public void headsOrTails(View view){
        Random rand = new Random();
        int number = rand.nextInt(2)+1;
        String result;
        if(number == 1){
            result = "Heads";
            buttonView.setText("Its " + result);
        }else{
            result = "Tails";
            buttonView.setText("Its " + result);
        }

    }





}