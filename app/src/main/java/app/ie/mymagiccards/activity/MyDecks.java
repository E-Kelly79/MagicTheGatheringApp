package app.ie.mymagiccards.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import app.ie.mymagiccards.adapters.DeckRecyclerViewAdapter;
import app.ie.mymagiccards.models.Decks;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDecks extends AppCompatActivity {

    private FirebaseDatabase        database;
    private DatabaseReference       databaseReference;
    private List<Decks>             deckList;
    private FirebaseUser            user;
    private FirebaseAuth            mAuth;
    private CardAdapter             cardAdapter;
    @BindView(R.id.swipeCardView)SwipeCardsView swipeCardsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_decks);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Decks").child("Athersquall");

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
}