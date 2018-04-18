package app.ie.mymagiccards.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huxq17.swipecardsview.BaseCardAdapter;

import java.util.List;

import app.ie.mymagiccards.R;
import app.ie.mymagiccards.activity.MyDecks;
import app.ie.mymagiccards.models.Decks;

public class CardAdapter extends BaseCardAdapter {
    private List<Decks> deckList;
    private Context context;

    public CardAdapter(List<Decks> deckList, Context context) {
        this.deckList = deckList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return deckList.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.swipe_deck;
    }

    @Override
    public void onBindData(int position, View cardview) {
        if(deckList == null || deckList.size() == 0){
            return;
        }
        TextView textView = cardview.findViewById(R.id.cardNameDeck);
        ImageView imageView = cardview.findViewById(R.id.deckCard);
        Decks deck = deckList.get(position);
        Glide.with(context)
                .load(deck.getCardImage())
                .into(imageView);
        textView.setText(deck.getCardName());

    }
}
