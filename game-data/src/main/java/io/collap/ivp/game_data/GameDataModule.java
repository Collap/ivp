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
        Dispatcher hs = new Dispatcher (collap);
        hs.registerControllerFactory ("card", new ProviderControllerFactory (GetCard.class, this));
        collap.getRootDispatcher ().registerDispatcher ("hs", hs);
    }

    @Override
    public void destroy () {

    }

    @Override
    public void configureHibernate (Configuration configuration) {
        configuration.addAnnotatedClass (Card.class);
    }

}
