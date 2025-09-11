package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.xm666.noitathespire.powers.Bouncing;
import com.xm666.noitathespire.powers.BouncingCast;

public class BouncyAction extends AbstractGameAction {
    public static boolean isBouncy = false;
    public final DamageInfo info;

    public BouncyAction(AbstractCreature target, DamageInfo info) {
        this(target, info, AttackEffect.NONE);
    }

    public BouncyAction(AbstractCreature target, DamageInfo info, AttackEffect attackEffect) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.attackEffect = attackEffect;
    }

    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
                isBouncy = true;
                this.target.damage(this.info);
                isBouncy = false;
                BouncingCast bouncingCast = (BouncingCast) source.getPower(BouncingCast.POWER_ID);
                int amount = getAmount() + (bouncingCast != null ? bouncingCast.amount : 0);
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
                }
            }
        }
    }

    public int getAmount() {
        return this.info.output;
    }
}
