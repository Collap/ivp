package io.collap.ivp.game_data.entity.hearthstone;

import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "game_data_hs_cards")
public class Card extends io.collap.entity.Entity {

    private static final String CURRENT_PACKAGE = "io.collap.ivp.game_data.entity.hearthstone.";

    private String name;
    private CardClass cardClass;
    private Rarity rarity;
    private Type type;
    private Expansion expansion;

    private Integer cost;
    private Integer attack;
    private Integer health;

    private Boolean isFree;
    private Boolean isToken;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Column(name = "class")
    @org.hibernate.annotations.Type(type = "PersistentEnum",
            parameters = @Parameter(name = "type", value = CURRENT_PACKAGE + "CardClass"))
    public CardClass getCardClass () {
        return cardClass;
    }

    public void setCardClass (CardClass cardClass) {
        this.cardClass = cardClass;
    }

    @org.hibernate.annotations.Type(type = "PersistentEnum",
            parameters = @Parameter(name = "type", value = CURRENT_PACKAGE + "Rarity"))
    public Rarity getRarity () {
        return rarity;
    }

    public void setRarity (Rarity rarity) {
        this.rarity = rarity;
    }

    @org.hibernate.annotations.Type(type = "PersistentEnum",
            parameters = @Parameter(name = "type", value = CURRENT_PACKAGE + "Type"))
    public Type getType () {
        return type;
    }

    public void setType (Type type) {
        this.type = type;
    }

    @org.hibernate.annotations.Type(type = "PersistentEnum",
            parameters = @Parameter(name = "type", value = CURRENT_PACKAGE + "Expansion"))
    public Expansion getExpansion () {
        return expansion;
    }

    public void setExpansion (Expansion expansion) {
        this.expansion = expansion;
    }

    public Integer getCost () {
        return cost;
    }

    public void setCost (Integer cost) {
        this.cost = cost;
    }

    public Integer getAttack () {
        return attack;
    }

    public void setAttack (Integer attack) {
        this.attack = attack;
    }

    public Integer getHealth () {
        return health;
    }

    public void setHealth (Integer health) {
        this.health = health;
    }

    public Boolean getIsFree () {
        return isFree;
    }

    public void setIsFree (Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsToken () {
        return isToken;
    }

    public void setIsToken (Boolean isToken) {
        this.isToken = isToken;
    }

}
