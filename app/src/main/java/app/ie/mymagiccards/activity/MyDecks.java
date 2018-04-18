package app.ie.mymagiccards.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huxq17.swipecardsview.SwipeCardsView;

import java.util.ArrayList;
import java.util.List;

import app.ie.mymagiccards.R;
import app.ie.mymagiccards.adapters.CardAdapter;
import app.ie.mymagiccards.models.Decks;
import app.ie.mymagiccards.utils.Prefs;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDecks extends AppCompatActivity  {

    private FirebaseDatabase        database;
    private DatabaseReference       databaseReference;
    private List<Decks>             deckList;
    private FirebaseUser            user;
    private FirebaseAuth            mAuth;
    private CardAdapter             cardAdapter;
    private Prefs                   myKey;
    private String                  keyValue;


    @BindView(R.id.swipeCardView)SwipeCardsView swipeCardsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_decks);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(user.getUid()).child("Deck");


        deckList = new ArrayList<>();
        swipeCardsView.retainLastCard(true);
        swipeCardsView.enableSwipe(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Decks deck = dataSnapshot.getValue(Decks.class);
                deckList.add(deck);

                cardAdapter = new CardAdapter(deckList, MyDecks.this);
                swipeCardsView.setAdapter(cardAdapter);
                String key = dataSnapshot.getKey();

                keyValue = key;
                Log.i("My Key", dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and show the items.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
                Log.i("The Key", keyValue);
                removeCard(keyValue);

                break;
            case R.id.add:

                break;
            case R.id.signout:
                if(mAuth != null && user != null){
                    mAuth.signOut();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void removeCard(String id){

        databaseReference.child(id).removeValue();
       recreate();

    }


}
