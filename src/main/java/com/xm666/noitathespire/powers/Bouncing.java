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
    private static final String IMG_PATH_128 = "NoitaTheSpire/powers/bouncing.png";
    private static final String IMG_PATH_48 = "NoitaTheSpire/powers/bouncing_48.png";
    private static final TextureAtlas.AtlasRegion REGION_128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_128), 0, 0, 80, 80);
    private static final TextureAtlas.AtlasRegion REGION_48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_48), 0, 0, 32, 32);
    private static int idOffset;

    public Bouncing(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID+idOffset++;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = amount;

        this.region128 = REGION_128;
        this.region48 = REGION_48;

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], getApplyAmount(), this.amount);
    }

    //@Override
    //public void onInitialApplication() {
    //    super.onInitialApplication();
    //    this.ID = POWER_ID;
    //}

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
        this.flash();
        NoitaTheSpire.playAudio("bullet_bounce_0");
        AbstractCreature originalOwner = owner;
        AbstractMonster exception = originalOwner instanceof AbstractMonster ? (AbstractMonster) originalOwner : null;
        AbstractMonster m = ModUtil.getRandomMonster(AbstractDungeon.getMonsters(), exception, true);
        int originalAmount = amount;
        int applyAmount = getApplyAmount();
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(
                        originalOwner,
                        m,
                        this
                )
        );
        if (applyAmount > 0) {
            owner = m;
            amount = applyAmount;
            //ID = "";
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            m,
                            originalOwner,
                            this
                    )
            );
        }
        AbstractDungeon.actionManager.addToBottom(
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
        return (int) (amount * 0.75);
    }
}
