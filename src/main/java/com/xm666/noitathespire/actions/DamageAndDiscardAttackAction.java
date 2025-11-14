package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DamageAndDiscardAttackAction extends DiscardAttackAction {
    private final int damage;
    private final AbstractGameAction.AttackEffect effect;

    public DamageAndDiscardAttackAction(AbstractCreature source, int amount, int damage, AbstractGameAction.AttackEffect effect) {
        super(source, amount);
        this.anyNumber = true;
        this.canPickZero = true;
        this.damage = damage;
        this.effect = effect;
    }

    protected void apply(AbstractCard c) {
        super.apply(c);
        this.addToBot(
                new DamageRandomEnemyAction(
                        new DamageInfo(
                                source,
                                damage
                        ),
                        effect
                )
        );
    }
}
