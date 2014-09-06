package io.collap.ivp.post_types.entity.hearthstone;

import io.collap.entity.PersistentEnum;

import java.util.Arrays;
import java.util.List;

public enum DeckBudget implements PersistentEnum {

    basic (0), low (1), mid (2), legendary (3);

    public static final List<DeckBudget> valueList = Arrays.asList (values ());

    private int id;

    DeckBudget (int id) {
        this.id = id;
    }

    @Override
    public int getId () {
        return id;
    }

}
