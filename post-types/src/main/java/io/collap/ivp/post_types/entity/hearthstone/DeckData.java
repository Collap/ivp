package io.collap.ivp.post_types.entity.hearthstone;

import io.collap.ivp.game_data.entity.hearthstone.HearthstoneClass;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "post_data_hs_deck")
public class DeckData extends io.collap.entity.Entity {

    private DeckBudget budget;
    private HearthstoneClass deckClass;
    private String playstyle;
    private String deckUrl;

    public DeckData () {

    }

    public static DeckData createTransientDeckData () {
        DeckData data = new DeckData ();
        data.setId (-1L);
        data.setBudget (DeckBudget.basic);
        data.setDeckClass (HearthstoneClass.neutral);
        data.setPlaystyle ("");
        data.setDeckUrl ("");
        return data;
    }

    @Type(type = "PersistentEnum",
          parameters = @Parameter(name = "type", value = "io.collap.ivp.post_types.entity.hearthstone.DeckBudget"))
    public DeckBudget getBudget () {
        return budget;
    }

    public void setBudget (DeckBudget budget) {
        this.budget = budget;
    }

    @Type(type = "PersistentEnum",
            parameters = @Parameter(name = "type", value = "io.collap.ivp.game_data.entity.hearthstone.HearthstoneClass"))
    public HearthstoneClass getDeckClass () {
        return deckClass;
    }

    public void setDeckClass (HearthstoneClass deckClass) {
        this.deckClass = deckClass;
    }

    public String getPlaystyle () {
        return playstyle;
    }

    public void setPlaystyle (String playstyle) {
        this.playstyle = playstyle;
    }

    public String getDeckUrl () {
        return deckUrl;
    }

    public void setDeckUrl (String deckUrl) {
        this.deckUrl = deckUrl;
    }

}
