package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.Collection;

public class DrawToTopAction extends AbstractGameAction {
    private final AbstractCard[] cards;
    private final AbstractPlayer p;

    public DrawToTopAction(AbstractCreature target, AbstractCreature source, Collection<AbstractCard> cards) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = (AbstractPlayer) target;
        this.cards = cards.toArray(new AbstractCard[]{});
    }

    @Override
    public void update() {
        for (int i = cards.length - 1; i >= 0; --i) {
            AbstractCard c = cards[i];
            if (!this.p.drawPile.contains(c))
                continue;
            this.p.drawPile.removeCard(c);
            this.p.drawPile.addToTop(c);
        }
        this.isDone = true;
    }
}
