package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BlockAndDiscardAttackAction extends DiscardAttackAction {
    private final int block;

    public BlockAndDiscardAttackAction(AbstractCreature source, int amount, int block) {
        super(source, amount);
        this.anyNumber = true;
        this.canPickZero = true;
        this.block = block;
    }

    protected void apply(AbstractCard c) {
        super.apply(c);
        this.addToBot(new GainBlockAction(
                target,
                block
        ));
    }
}
