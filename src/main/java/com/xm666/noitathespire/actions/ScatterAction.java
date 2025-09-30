package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ScatterAction extends AbstractGameAction {
    public ScatterAction(int amount) {
        this.setValues(target, source, amount);
    }

    public void update() {
        CardGroup attacks = AbstractDungeon.player.hand.getAttacks();
        ArrayList<AbstractCard> cards = new ArrayList<>(attacks.group);
        int amount = Math.min(this.amount, attacks.size());
        for (int i = 0; i < amount; ++i) {
            int index = AbstractDungeon.cardRandomRng.random(cards.size() - 1);
            AbstractCard randomAttackCard = cards.remove(index);
            if (randomAttackCard != null)
                this.addToTop(new DiscardSpecificCardAction(randomAttackCard));
        }
        this.addToTop(new WaitAction(0.4F));
        this.isDone = true;
    }
}
