package io.collap.ivp.post_types;

import io.collap.ivp.game_data.entity.hearthstone.Card;

import java.util.Comparator;

public class CardStackComparator implements Comparator<CardStack> {

    /**
     * Order by cost, if equal order by type.
     */
    @Override
    public int compare (CardStack stack1, CardStack stack2) {
        Card card1 = stack1.getCard ();
        Card card2 = stack2.getCard ();

        if (card1.getCost () < card2.getCost ()) {
            return -1;
        }else if (card1.getCost () > card2.getCost ()) {
            return 1;
        }else { /* Cost is equal. */
            int o1 = card1.getType ().ordinal ();
            int o2 = card2.getType ().ordinal ();
            if (o1 < o2) {
                return -1;
            }else if (o1 > o2) {
                return 1;
            }
        }

        return 0;
    }

}
