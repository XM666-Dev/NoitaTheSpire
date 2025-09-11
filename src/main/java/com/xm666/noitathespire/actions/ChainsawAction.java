package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.xm666.noitathespire.util.ModUtil;

public class ChainsawAction extends AbstractGameAction {
    public ChainsawAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard randomAttackCard = ModUtil.getRandomAttackCardFromHand();
        if (randomAttackCard != null) {
            AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(randomAttackCard));
        } else {
            this.addToBot(new DrawCardAction(source, amount));
        }
        this.isDone = true;
    }
}
