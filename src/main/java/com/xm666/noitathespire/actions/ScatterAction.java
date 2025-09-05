package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ScatterAction extends AbstractGameAction {
    public int amount;

    public ScatterAction(int amount) {
        this.duration = 0.001F;
        this.amount = amount;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {
            int count = 0;
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    if (count >= amount) {
                        break;
                    }
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                    ++count;
                }
            }
        }
    }
}
