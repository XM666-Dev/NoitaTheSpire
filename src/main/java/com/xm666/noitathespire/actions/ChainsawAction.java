package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChainsawAction extends AbstractGameAction {
    public ChainsawAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
    }

    public void update() {
        CardGroup attacks = AbstractDungeon.player.hand.getAttacks();
        if (attacks.isEmpty())
            this.addToBot(new DrawCardAction(source, amount));
        this.isDone = true;
    }
}
