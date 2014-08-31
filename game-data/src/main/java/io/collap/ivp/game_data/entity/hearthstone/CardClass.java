package io.collap.ivp.game_data.entity.hearthstone;

import io.collap.entity.PersistentEnum;

public enum CardClass implements PersistentEnum {

    neutral (0), druid (1), hunter (2), mage (3), paladin (4),
    priest (5), rogue (6), shaman (7), warlock (8), warrior (9);

    private int id;

    CardClass (int id) {
        this.id = id;
    }

    @Override
    public int getId () {
        return id;
    }

}