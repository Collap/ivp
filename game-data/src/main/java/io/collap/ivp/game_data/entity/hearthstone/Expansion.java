package io.collap.ivp.game_data.entity.hearthstone;

import io.collap.entity.PersistentEnum;

public enum Expansion implements PersistentEnum {

    vanilla (0), naxxramas (1);

    private int id;

    Expansion (int id) {
        this.id = id;
    }

    @Override
    public int getId () {
        return id;
    }

}
