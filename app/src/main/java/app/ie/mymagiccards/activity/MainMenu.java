package app.ie.mymagiccards.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.ie.mymagiccards.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenu extends AppCompatActivity {

    @BindView(R.id.searchButtonMenu) Button search;
    @BindView(R.id.deckButton)       Button decks;
    @BindView(R.id.playerButton)     Button players;
    @BindView(R.id.lifeButton)       Button life;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);

    }
    @OnClick(R.id.searchButtonMenu)
    public void search(View view){
        Intent searching = new Intent(MainMenu.this, MainActivity.class);
        startActivity(searching);
    }

    @OnClick(R.id.playerButton)
    public void login(View view){
        Intent login = new Intent(MainMenu.this, Login.class);
        startActivity(login);
    }

    @OnClick(R.id.deckButton)
    public void decks(View view){
        Intent login = new Intent(MainMenu.this, MyDecks.class);
        startActivity(login);
    }

    @OnClick(R.id.lifeButton)
    public void lifeTools(View view){
        Intent login = new Intent(MainMenu.this, LifeCounter.class);
        startActivity(login);
    }






}
