package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.xm666.noitathespire.powers.Bouncing;

public class LimitedBouncyAction extends AbstractGameAction {
    private final DamageInfo info;

    public LimitedBouncyAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                this.target.damage(this.info);
                int amount = this.info.output - this.target.lastDamageTaken;
                if (amount > 0) {
                    Bouncing bouncing = new Bouncing(this.target, amount);
                    if (this.target.isDying) {
                        bouncing.bounce();
                    } else {
                        this.addToTop(new ApplyPowerAction(this.target, this.source, bouncing));
                    }
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                } else {
                    this.addToTop(new WaitAction(0.1F));
                }
            }
        }
    }
}
