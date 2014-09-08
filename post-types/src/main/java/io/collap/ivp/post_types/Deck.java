package io.collap.ivp.post_types;

import io.collap.ivp.game_data.entity.hearthstone.Card;
import io.collap.ivp.post_types.entity.hearthstone.DeckBudget;
import io.collap.ivp.post_types.entity.hearthstone.DeckData;
import io.collap.std.format.Format;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private DeckData data;
    private String className;
    private List<CardStack> stacks = new ArrayList<> ();
    private String title;
    private String introductionTitle;
    private int dustCost;

    public Deck (DeckData data, Session session) {
        this.data = data;

        className = new Format ().capitalize (data.getDeckClass ().name ());
        fetchStacks (session);
        generateTitle ();
        generateIntroductionTitle ();
        calculateDustCost (session);
    }

    private void fetchStacks (Session session) {
        String url = data.getDeckUrl ();
        String[] stackDefinitions = url.substring (url.lastIndexOf ('#') + 1).split (";");
        for (String stackDefinition : stackDefinitions) {
            String[] tokens = stackDefinition.split (":");
            if (tokens.length != 2) continue;

            long cardId = Long.parseLong (tokens[0]);
            Card card = (Card) session.get (Card.class, cardId);
            int amount = Integer.parseInt (tokens[1]);
            stacks.add (new CardStack (card, amount));
        }

        Collections.sort (stacks, new CardStackComparator ());
    }

    /**
     * The format is like this:
     * [Budget] [Class] [Playstyle] Deck
     */
    private void generateTitle () {
        Format format = new Format ();
        title = format.capitalize (data.getBudget ().name ()) + " ";
        title += className + " ";
        title += format.capitalize (data.getPlaystyle ());
        title += " Deck";
    }

    /**
     * The format is like this:
     * [budget] budget [Class] [Playstyle] deck
     */
    private void generateIntroductionTitle () {
        introductionTitle = "";

        DeckBudget budget = data.getBudget ();
        if (budget == DeckBudget.basic) {
            introductionTitle += "basic ";
        }else {
            introductionTitle += budget.name () + " budget ";
        }

        Format format = new Format ();
        introductionTitle += className + " ";
        introductionTitle += format.capitalize (data.getPlaystyle ());
        introductionTitle += " deck";
    }

    private void calculateDustCost (Session session) {
        dustCost = 0;
        for (CardStack stack : stacks) {
            Card card = stack.getCard ();

            /* Check quests. */
            if (card.getId ().equals (217L)) { /* Old Murk-Eye. */
                long[] ids = new long[] {
                        55,     /* Murloc Raider */
                        88,     /* Coldlight Oracle */
                        222,    /* Murloc Warleader */
                        289,    /* Bluegill Warrior */
                        357,    /* Murloc Tidehunter */
                        420,    /* Murloc Tidecaller */
                        424,    /* Coldlight Seer */
                        510     /* Grimscale Oracle */
                };
                for (long id : ids) {
                    dustCost += getPrerequisiteCardDustCost (id, session);
                }
            }else {
                dustCost += stack.getCard ().getDustCost () * stack.getAmount ();
            }
        }
    }

    /**
     * Returns the additional dust amount that is needed to get the
     * prerequisite card, if it is <b>not</b> already included in
     * the deck.
     */
    private int getPrerequisiteCardDustCost (long id, Session session) {
        for (CardStack stack : stacks) {
            if (stack.getCard ().getId ().equals (id)) {
                return 0;
            }
        }

        Card card = (Card) session.get (Card.class, id);
        return card.getDustCost ();
    }

    public DeckData getData () {
        return data;
    }

    public List<CardStack> getStacks () {
        return stacks;
    }

    public String getTitle () {
        return title;
    }

    public String getIntroductionTitle () {
        return introductionTitle;
    }

    public int getDustCost () {
        return dustCost;
    }

}
