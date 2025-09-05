package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardDrawBottomAction extends AbstractGameAction {

    public DiscardDrawBottomAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        for (int i = 0; i < amount; i++) {
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard c = AbstractDungeon.player.drawPile.getBottomCard();
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
            }
        }
        this.isDone = true;
    }
}
