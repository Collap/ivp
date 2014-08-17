package io.collap.ivp.post_types.type.hearthstone;

import io.collap.Collap;
import io.collap.controller.communication.Request;
import io.collap.entity.Entity;
import io.collap.ivp.post_types.DeckBudget;
import io.collap.ivp.post_types.entity.hearthstone.DeckData;
import io.collap.std.post.entity.Post;
import io.collap.std.post.type.BasicType;
import io.collap.template.TemplateRenderer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

public class DeckType extends BasicType {

    private TemplateRenderer renderer;

    public DeckType (Collap collap, TemplateRenderer renderer) {
        super (collap);
        this.renderer = renderer;
    }

    @Override
    protected void update (Entity entity, Request request) {
        DeckData data = (DeckData) entity;
        data.setBudget (DeckBudget.valueOf (request.getStringParameter ("budget")));
        data.setDeckUrl (request.getStringParameter ("deckUrl"));
    }

    @Override
    protected String getEditor (@Nullable Entity entity) {
        DeckData data = (DeckData) entity;
        if (data == null) {
            data = DeckData.createTransientDeckData ();
        }

        Map<String, Object> model = new HashMap<> ();
        model.put ("data", data);
        model.put ("budgets", DeckBudget.valueList);
        try {
            return renderer.renderTemplate ("hearthstone/deck/Editor.jade", model);
        } catch (IOException e) {
            throw new RuntimeException ("Deck/Editor template not found!", e);
        }
    }

    @Override
    protected void compile (Entity entity, Post post) {
        DeckData data = (DeckData) entity;

        /* Mana curve. */
        // TODO: Parse the card ids.
        List<Integer> counts = new ArrayList<> ();
        counts.add (0);
        counts.add (4);
        counts.add (6);
        counts.add (4);
        counts.add (10);
        counts.add (2);
        counts.add (1);
        counts.add (3);
        int max = Collections.max (counts);
        Map<String, Object> model = new HashMap<> ();
        model.put ("isThumbnail", false);
        model.put ("counts", counts);
        model.put ("max", (float) max);
        try {
            post.setContent (renderer.renderTemplate ("hearthstone/deck/ManaCurve", model));
        } catch (IOException e) {
            post.setContent ("Mana curve template not found!");
        }
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
