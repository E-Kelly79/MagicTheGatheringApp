package app.ie.mymagiccards.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.ie.mymagiccards.R;

public class MainMenu extends AppCompatActivity {

    private Button search, decks, players, life;

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
                Toast.makeText(MainMenu.this, "Search button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        decks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMenu.this, "Decks button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMenu.this, "Players button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMenu.this, "Life button pressed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
