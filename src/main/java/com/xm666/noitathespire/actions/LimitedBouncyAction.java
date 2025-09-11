package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LimitedBouncyAction extends BouncyAction {
    public LimitedBouncyAction(AbstractCreature target, DamageInfo info) {
        this(target, info, AttackEffect.NONE);
    }

    public LimitedBouncyAction(AbstractCreature target, DamageInfo info, AttackEffect attackEffect) {
        super(target, info, attackEffect);
    }

    public int getAmount() {
        return super.getAmount() - target.lastDamageTaken;
    }
}
