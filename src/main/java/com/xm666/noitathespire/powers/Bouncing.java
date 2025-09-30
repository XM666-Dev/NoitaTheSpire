package com.xm666.noitathespire.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.util.ModUtil;

public class Bouncing extends AbstractPower {
    public static final String POWER_ID = ModUtil.getId();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_PATH_128 = ModUtil.getPowerImg();
    private static final String IMG_PATH_48 = ModUtil.getPowerImg48();
    private static final TextureAtlas.AtlasRegion REGION_128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_128), 0, 0, 80, 80);
    private static final TextureAtlas.AtlasRegion REGION_48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_48), 0, 0, 32, 32);
    private static int idOffset;

    public Bouncing(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID + idOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = amount;

        this.region128 = REGION_128;
        this.region48 = REGION_48;

        this.updateDescription();
    }

    public void updateDescription() {
        int applyAmount = getApplyAmount();
        this.description = String.format(DESCRIPTIONS[applyAmount > 0 ? 0 : 1], this.amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        bounce();
    }

    @Override
    public void onDeath() {
        super.onDeath();
        bounce();
    }

    public void bounce() {
        if (owner == null)
            return;
        this.flash();
        NoitaTheSpire.playAudio("bullet_bounce_0");
        AbstractCreature originalOwner = owner;
        AbstractMonster exception = originalOwner instanceof AbstractMonster ? (AbstractMonster) originalOwner : null;
        AbstractMonster m = ModUtil.getRandomMonster(AbstractDungeon.getMonsters(), exception, true);
        int originalAmount = amount;
        int applyAmount = getApplyAmount();
        this.addToBot(
                new RemoveSpecificPowerAction(
                        originalOwner,
                        m,
                        this
                )
        );
        if (applyAmount > 0) {
            owner = m;
            amount = applyAmount;
            this.addToBot(
                    new ApplyPowerAction(
                            m,
                            originalOwner,
                            this
                    )
            );
        }
        this.addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(
                                m,
                                originalAmount
                        )
                )
        );
        updateDescription();
    }

    private int getApplyAmount() {
        return amount - 2;
    }
}
