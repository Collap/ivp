package io.collap.ivp.game_data.entity.hearthstone;

import io.collap.entity.PersistentEnum;

public enum Rarity implements PersistentEnum {

    basic (0), common (1), rare (2), epic (3), legendary (4);

    private int id;

    Rarity (int id) {
        this.id = id;
    }

    @Override
    public int getId () {
        return id;
    }

}
