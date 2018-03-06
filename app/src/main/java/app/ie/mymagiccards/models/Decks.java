package app.ie.mymagiccards.models;

import java.util.List;

/**
 * Created by Eoin on 01/03/2018.
 */

public class Decks {
    private String [] deckName;
    private int CardCount;
    private int landCount;
    private List<Cards> deckList;

    private Decks(){

    }

    public String[] getDeckName() {
        return deckName;
    }

    public void setDeckName(String[] deckName) {
        this.deckName = deckName;
    }

    public int getCardCount() {
        return CardCount;
    }

    public void setCardCount(int cardCount) {
        CardCount = cardCount;
    }

    public int getLandCount() {
        return landCount;
    }

    public void setLandCount(int landCount) {
        this.landCount = landCount;
    }
}
