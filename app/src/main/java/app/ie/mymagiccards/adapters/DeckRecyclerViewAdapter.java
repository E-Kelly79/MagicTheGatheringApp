package app.ie.mymagiccards.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import app.ie.mymagiccards.R;
import app.ie.mymagiccards.models.Decks;

/**
 * Created by Eoin on 24/02/2018.
 */

public class DeckRecyclerViewAdapter extends RecyclerView.Adapter<DeckRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Decks> cardsList;

    public DeckRecyclerViewAdapter(Context context, List<Decks> cards) {
        this.context = context;
        this.cardsList = cards;

    }

    @Override
    public DeckRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_view_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(DeckRecyclerViewAdapter.ViewHolder holder, int position) {
        Decks card = cardsList.get(position);
        String cardImage = card.getCardImage();

        holder.cardName.setText(card.getCardName());
//        holder.cardType.setText(card.getType());
//        holder.cardColor.setText(card.getColor());
//        holder.cardRarity.setText(card.getRarity());
        Log.i("image", cardImage.toString());
        if(!cardImage.equals("")) {
            Glide.with(context)
                    .load(cardImage)
                    .into(holder.cardImage);
        }else{
            Glide.with(context)
                    .load(R.drawable.cardback)
                    .into(holder.cardImage);
        }

    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardName, cardColor, cardType, cardRarity;
        ImageView cardImage;

        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context = ctx;

            cardImage = itemView.findViewById(R.id.deckImage);
            cardName = itemView.findViewById(R.id.deckName);
//            cardColor = itemView.findViewById(R.id.colorid);
//            cardType = itemView.findViewById(R.id.cardType);
//            cardRarity = itemView.findViewById(R.id.cardRarity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Decks card = cardsList.get(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}

