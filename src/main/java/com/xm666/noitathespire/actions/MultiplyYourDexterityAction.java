package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class MultiplyYourDexterityAction extends AbstractGameAction {
    private final float multiplier;

    public MultiplyYourDexterityAction(AbstractCreature target, float multiplier) {
        this.duration = 0.5F;
        this.actionType = ActionType.POWER;
        this.target = target;
        this.multiplier = multiplier;
    }

    public void update() {
        if (this.duration == 0.5F && this.target != null) {
            DexterityPower dexterity = (DexterityPower) target.getPower(DexterityPower.POWER_ID);
            if (dexterity != null && dexterity.amount > 0) {
                this.addToBot(
                        new ApplyPowerAction(target, target, new DexterityPower(target, (int) (dexterity.amount * multiplier)))
                );
            }
        }

        this.tickDuration();
    }
}
