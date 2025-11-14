package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardAllAction extends AbstractGameAction {
    public DiscardAllAction(AbstractCreature source) {
        this.setValues(AbstractDungeon.player, source, amount);
    }

    @Override
    public void update() {
        int count = AbstractDungeon.player.hand.size();
        this.addToTop(new DiscardAction(target, source, count, false));
        this.isDone = true;
    }
}
