package com.xm666.noitathespire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.xm666.noitathespire.util.ModUtil;

public class MeleeCounter extends BufferPower {
    public static final String POWER_ID = ModUtil.getId();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MeleeCounter(AbstractCreature owner, int amount) {
        super(owner, amount);
        this.name = NAME;
        this.ID = POWER_ID;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner != owner && info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(info.owner.hb.cX, info.owner.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
            info.owner.damage(info);
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            return 0;
        }
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
