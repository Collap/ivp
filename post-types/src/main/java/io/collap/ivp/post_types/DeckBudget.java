package io.collap.ivp.post_types;

import java.util.Arrays;
import java.util.List;

public enum DeckBudget {

    basic, low, mid, legendary;

    public static final List<DeckBudget> valueList = Arrays.asList (values ());

    @Override
    public String toString () {
        return this.name ();
    }

}
