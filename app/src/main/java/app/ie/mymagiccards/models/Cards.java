package app.ie.mymagiccards.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eoin on 24/02/2018.
 */

public class Cards implements Serializable {
    private static final long id = 1L;

    private String name;
    private String manaCost;
    private String color;
    private String type;
    private String rarity;
    private String cardText;
    private String imageUrl;
    private String power;
    private String toughness;
    private String cardID;



    public Cards() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText =  cardText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "name='" + name + '\'' +
                ", manaCost='" + manaCost + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", rarity='" + rarity + '\'' +
                ", cardText='" + cardText + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", power='" + power + '\'' +
                ", toughness='" + toughness + '\'' +
                ", cardID='" + cardID + '\'' +
                '}';
    }
}
