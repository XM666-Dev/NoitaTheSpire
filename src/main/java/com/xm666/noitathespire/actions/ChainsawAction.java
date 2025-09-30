package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ChainsawAction extends AbstractGameAction {
    private final int discardAmount;

    public ChainsawAction(AbstractCreature source, int drawAmount, int discardAmount) {
        this.setValues(AbstractDungeon.player, source, drawAmount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.discardAmount = discardAmount;
    }

    public void update() {
        CardGroup attacks = AbstractDungeon.player.hand.getAttacks();
        ArrayList<AbstractCard> cards = new ArrayList<>(attacks.group);
        int amount = Math.min(this.discardAmount, attacks.size());
        if (amount > 0) {
            for (int i = 0; i < amount; ++i) {
                int index = AbstractDungeon.cardRandomRng.random(cards.size() - 1);
                AbstractCard randomAttackCard = cards.remove(index);
                if (randomAttackCard != null)
                    this.addToTop(new DiscardSpecificCardAction(randomAttackCard));
            }
        } else {
            this.addToBot(new DrawCardAction(source, amount));
        }
        this.isDone = true;
    }
}
