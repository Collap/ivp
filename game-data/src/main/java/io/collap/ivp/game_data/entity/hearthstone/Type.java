package io.collap.ivp.game_data.entity.hearthstone;

import io.collap.entity.PersistentEnum;

public enum Type implements PersistentEnum {

    minion (0), spell (1), weapon (2), hero (3), hero_power (4);

    private int id;

    Type (int id) {
        this.id = id;
    }

    @Override
    public int getId () {
        return id;
    }

}