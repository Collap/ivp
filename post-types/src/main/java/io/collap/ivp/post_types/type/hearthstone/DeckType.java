package io.collap.ivp.post_types.type.hearthstone;

import io.collap.Collap;
import io.collap.bryg.environment.Environment;
import io.collap.bryg.model.BasicModel;
import io.collap.bryg.model.Model;
import io.collap.bryg.template.Template;
import io.collap.controller.communication.Request;
import io.collap.entity.Entity;
import io.collap.ivp.game_data.entity.hearthstone.HearthstoneClass;
import io.collap.ivp.post_types.CardStack;
import io.collap.ivp.post_types.Deck;
import io.collap.ivp.post_types.entity.hearthstone.DeckBudget;
import io.collap.ivp.post_types.entity.hearthstone.DeckData;
import io.collap.std.post.entity.Post;
import io.collap.std.post.type.BasicType;
import org.hibernate.Session;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class DeckType extends BasicType {

    /**
     * The maximum cost to be shown in the mana curve.
     * The current value means that all cards starting
     * at or above a cost of 7 are counted towards the
     * 7+ cost category.
     */
    private static final int MAXIMUM_COST = 7;

    private Environment bryg;

    public DeckType (Collap collap, Environment bryg) {
        super (collap);
        this.bryg = bryg;
    }

    @Override
    protected void update (Entity entity, Request request) {
        DeckData data = (DeckData) entity;

        DeckBudget budget = DeckBudget.valueOf (request.getStringParameter ("budget"));
        data.setBudget (budget);

        HearthstoneClass deckClass = HearthstoneClass.valueOf (request.getStringParameter ("class"));
        if (deckClass == HearthstoneClass.neutral) {
            throw new IllegalArgumentException ("There are no neutral decks.");
        }
        data.setDeckClass (deckClass);

        data.setPlaystyle (request.getStringParameter ("playstyle"));

        data.setDeckUrl (request.getStringParameter ("deckUrl"));
    }

    @Override
    protected String getEditor (@Nullable Entity entity) throws IOException {
        DeckData data = (DeckData) entity;
        if (data == null) {
            data = DeckData.createTransientDeckData ();
        }

        Model model = new BasicModel ();
        model.setVariable ("data", data);
        model.setVariable ("budgets", DeckBudget.valueList);
        model.setVariable ("classes", HearthstoneClass.playerClasses);

        StringWriter writer = new StringWriter ();
        bryg.getTemplate ("hearthstone.deck.Editor").render (writer, model);
        return writer.toString ();
    }

    @Override
    protected void compile (Entity entity, Post post) throws IOException {
        DeckData data = (DeckData) entity;

        Session session = collap.getSessionFactory ().getCurrentSession ();

        Deck deck = new Deck (data, session);

        StringWriter writer = new StringWriter ();

        /* Introduction. */
        writer.write ("<h1>Deck</h1>");

        Model deckModel = new BasicModel ();
        deckModel.setVariable ("deck", deck);

        Template template = bryg.getTemplate ("hearthstone.deck.budget." + data.getBudget ().name ());
        template.render (writer, deckModel);

        /* Card list. */
        bryg.getTemplate ("hearthstone.deck.CardList").render (writer, deckModel);

        /* Mana curve. */
        List<Integer> counts = new ArrayList<> (Collections.nCopies (MAXIMUM_COST + 1, 0));

        for (CardStack stack : deck.getStacks ()) {
            int cost = stack.getCard ().getCost ();
            if (cost > 7) cost = 7;
            int currentCount = counts.get (cost);
            counts.set (cost, currentCount + stack.getAmount ());
        }

        int max = Collections.max (counts);
        Model model = new BasicModel ();
        model.setVariable ("isThumbnail", false);
        model.setVariable ("counts", counts);
        model.setVariable ("max", max);
        bryg.getTemplate ("hearthstone.deck.ManaCurve").render (writer, model);

        post.setContent (writer.toString ());

        post.setTitle (deck.getTitle ());
    }

    @Override
    public String getName () {
        return "hs_deck";
    }

    @Override
    protected Class<? extends Entity> getDataClass () {
        return DeckData.class;
    }

}
