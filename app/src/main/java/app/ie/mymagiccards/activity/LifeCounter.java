package app.ie.mymagiccards.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import app.ie.mymagiccards.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifeCounter extends AppCompatActivity {
    private int player1 = 20;
    private int player2 = 20;

    @BindView(R.id.playerOneMinus)  Button playerOneMin;
    @BindView(R.id.playerOnePlus)   Button playerOnePlus;
    @BindView(R.id.playerTwoMinus)  Button playerTwoMin;
    @BindView(R.id.playerTwoPlus)   Button playerTwoPlus;
    @BindView(R.id.dice)            Button dice;
    @BindView(R.id.timer)           Button timer;
    @BindView(R.id.coinFlip)        Button coinFlip;
    @BindView(R.id.lifeValue1)      TextView lifeValue1;
    @BindView(R.id.lifeValue2)      TextView lifeValue2;
    @BindView(R.id.buttonView)      TextView buttonView;
    @BindView(R.id.headsOrTails)    ImageView coinImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);
        ButterKnife.bind(this);

        lifeValue1.setText(String.valueOf(player1));
        lifeValue2.setText(String.valueOf(player2));
    }

    @OnClick(R.id.coinFlip)
    public void coinFlip(){
        buttonView.setText("");
        Random rand = new Random();
        int number = rand.nextInt(2)+1;
        String result;
        if(number == 1){
            coinImage.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .load(R.drawable.heads)
                    .into(coinImage);
        }else{
            coinImage.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .load(R.drawable.tails)
                    .into(coinImage);
        }
    }

    @OnClick(R.id.dice)
    public void diceRoll(){
        coinImage.setVisibility(View.INVISIBLE);
        Random rand = new Random();
        int number = rand.nextInt(20)+1;
        buttonView.setText("You rolled " +String.valueOf(number));

    }

    @OnClick(R.id.playerOneMinus)
    public void playerOneMinusLife(){
        player1--;
        lifeValue1.setText(String.valueOf(player1));
    }

    @OnClick(R.id.playerOnePlus)
    public void playerOnePlusLife(){
        player1++;
        lifeValue1.setText(String.valueOf(player1));
    }

    @OnClick(R.id.playerTwoMinus)
    public void playerTwoMinusLife(){
        player2--;
        lifeValue2.setText(String.valueOf(player2));
    }

    @OnClick(R.id.playerTwoPlus)
    public void playerTwoPlusLife(){
        player2++;
        lifeValue2.setText(String.valueOf(player2));
    }

}