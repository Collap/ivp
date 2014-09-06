package io.collap.ivp.game_data;

import io.collap.controller.Dispatcher;
import io.collap.controller.ProviderControllerFactory;
import io.collap.ivp.game_data.entity.hearthstone.Card;
import io.collap.ivp.game_data.hearthstone.GetCard;
import io.collap.plugin.Module;
import org.hibernate.cfg.Configuration;

public class GameDataModule extends Module {

    public static final String VERSION = "0.1.1";
    public static final String ARTIFACT_NAME = "ivp-game-data-" + VERSION;

    @Override
    public void initialize () {
        Dispatcher gameData = new Dispatcher (collap);

        Dispatcher hs = new Dispatcher (collap);
        hs.registerControllerFactory ("get", new ProviderControllerFactory (GetCard.class, this));
        gameData.registerDispatcher ("hs", hs);

        collap.getRootDispatcher ().registerDispatcher ("game-data", gameData);
    }

    @Override
    public void destroy () {

    }

    @Override
    public void configureHibernate (Configuration configuration) {
        configuration.addAnnotatedClass (Card.class);
    }

}
