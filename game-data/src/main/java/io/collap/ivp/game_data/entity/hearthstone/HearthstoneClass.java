package io.collap.ivp.game_data.entity.hearthstone;

import io.collap.entity.PersistentEnum;

import java.util.Arrays;
import java.util.List;

public enum HearthstoneClass implements PersistentEnum {

    neutral (0), druid (1), hunter (2), mage (3), paladin (4),
    priest (5), rogue (6), shaman (7), warlock (8), warrior (9);

    public static final List<HearthstoneClass> valueList = Arrays.asList (values ());
    public static final List<HearthstoneClass> playerClasses = valueList.subList (1, valueList.size ());

    private int id;

    HearthstoneClass (int id) {
        this.id = id;
    }

    @Override
    public int getId () {
        return id;
    }

}