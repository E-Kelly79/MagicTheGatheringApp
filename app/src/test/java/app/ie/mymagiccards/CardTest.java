package app.ie.mymagiccards;

import org.junit.Test;

import app.ie.mymagiccards.models.Cards;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Eoin on 18/03/2018.
 */

public class CardTest {
    Cards card = new Cards("Jace", "5", "blue", "planewalker", "Mytic", "Hello World", "this.com", "100");

    @Test
    public void testCreate(){
        assertEquals("Jace", card.getName());
        assertEquals("5", card.getManaCost());
        assertEquals("blue", card.getColor());
        assertEquals("planewalker", card.getType());
        assertEquals("Mytic", card.getRarity());
        assertEquals("Hello World", card.getCardText());
        assertEquals("this.com", card.getImageUrl());
        assertEquals("100", card.getCardID());
    }

    @Test
    public void testToString(){
        assertEquals("Cards{name='Jace', manaCost='5', color='blue', type='planewalker', rarity='Mytic', cardText='Hello World', imageUrl='this.com', cardID='100'}", card.toString());

    }
}
