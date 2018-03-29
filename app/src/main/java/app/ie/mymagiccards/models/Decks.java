package app.ie.mymagiccards.models;

import java.util.List;

/**
 * Created by Eoin on 01/03/2018.
 */

public class Decks {
    private String cardName;
    private int cardCount;
    private List<String> deckList;
    private String cardImage;
    private String userid;

    public Decks(){

    }

    public Decks(String deckName, int cardCount, List<String> deckList, String image, String userid) {
        this.cardName = deckName;
        this.cardCount = cardCount;
        this.deckList = deckList;
        this.cardImage = image;
        this.userid = userid;
    }

    public Decks(String image, String userid) {
        this.cardImage   =   image;
        this.userid     =   userid;

    }


    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public List<String> getDeckList() {
        return deckList;
    }

    public void setDeckList(List<String> deckList) {
        this.deckList = deckList;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
