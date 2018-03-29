package app.ie.mymagiccards.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.ie.mymagiccards.R;

public class MainMenu extends AppCompatActivity {

    private Button search, decks, players, life;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        search = findViewById(R.id.searchButtonMenu);
        decks = findViewById(R.id.deckButton);
        players = findViewById(R.id.playerButton);
        life = findViewById(R.id.lifeButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent searching = new Intent(MainMenu.this, MainActivity.class);
                startActivity(searching);

                //dismiss or cancel the dialog



            }
        });

        decks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searching = new Intent(MainMenu.this, MyDecks.class);
                startActivity(searching);

            }
        });

        players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(MainMenu.this, Login.class);
                startActivity(login);
            }
        });

        life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMenu.this, "Life button pressed Coming soon", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
