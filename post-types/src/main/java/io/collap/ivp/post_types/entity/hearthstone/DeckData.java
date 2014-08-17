package io.collap.ivp.post_types.entity.hearthstone;

import io.collap.ivp.post_types.DeckBudget;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "post_data_hs_deck")
public class DeckData extends io.collap.entity.Entity {

    private DeckBudget budget;
    private String deckUrl;

    public DeckData () {

    }

    public static DeckData createTransientDeckData () {
        DeckData data = new DeckData ();
        data.setId (-1L);
        data.setBudget (DeckBudget.basic);
        data.setDeckUrl ("");
        return data;
    }

    @Enumerated(EnumType.STRING)
    public DeckBudget getBudget () {
        return budget;
    }

    public void setBudget (DeckBudget budget) {
        this.budget = budget;
    }

    public String getDeckUrl () {
        return deckUrl;
    }

    public void setDeckUrl (String deckUrl) {
        this.deckUrl = deckUrl;
    }

}
