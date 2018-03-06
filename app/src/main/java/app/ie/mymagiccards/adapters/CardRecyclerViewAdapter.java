package app.ie.mymagiccards.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import app.ie.mymagiccards.R;
import app.ie.mymagiccards.activity.CardDetailsActivity;
import app.ie.mymagiccards.models.Cards;


/**
 * Created by Eoin on 24/02/2018.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Cards> cardsList;

    public CardRecyclerViewAdapter(Context context, List<Cards> cards) {
        this.context = context;
        this.cardsList = cards;

    }

    @Override
    public CardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(CardRecyclerViewAdapter.ViewHolder holder, int position) {
        Cards card = cardsList.get(position);
        String cardImage = card.getImageUrl();

        holder.cardName.setText(card.getName());
        holder.cardType.setText(card.getType());
        holder.cardColor.setText(card.getColor());
        holder.cardRarity.setText(card.getRarity());

        if(!cardImage.equals("")) {
            Picasso.with(context)
                    .load(cardImage)
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

            cardImage = itemView.findViewById(R.id.cardImage);
            cardName = itemView.findViewById(R.id.cardName);
            cardColor = itemView.findViewById(R.id.colorid);
            cardType = itemView.findViewById(R.id.cardType);
            cardRarity = itemView.findViewById(R.id.cardRarity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cards card = cardsList.get(getAdapterPosition());
                    Intent intent = new Intent(context, CardDetailsActivity.class);
                    intent.putExtra("card", card);
                    ctx.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
