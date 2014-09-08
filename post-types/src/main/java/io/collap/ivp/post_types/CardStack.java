package io.collap.ivp.post_types;

import io.collap.ivp.game_data.entity.hearthstone.Card;

public class CardStack {

    private Card card;
    private int amount;

    public CardStack (Card card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public Card getCard () {
        return card;
    }

    public int getAmount () {
        return amount;
    }

}
