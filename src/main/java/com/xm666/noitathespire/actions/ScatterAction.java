package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.xm666.noitathespire.util.ModUtil;

public class ScatterAction extends AbstractGameAction {
    public ScatterAction(int amount) {
        this.setValues(target, source, amount);
    }

    public void update() {
        for (int i = 0; i < amount; ++i) {
            AbstractCard randomAttackCard = ModUtil.getRandomAttackCardFromHand();
            if (randomAttackCard != null)
                AbstractDungeon.actionManager.addToBottom(
                        new DiscardSpecificCardAction(randomAttackCard)
                );
        }
        this.isDone = true;
    }
}
