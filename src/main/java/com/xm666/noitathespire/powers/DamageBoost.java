package com.xm666.noitathespire.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.xm666.noitathespire.util.ModUtil;

public class DamageBoost extends AbstractPower {
    public static final String POWER_ID = ModUtil.getId();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_PATH_128 = ModUtil.getPowerImg();
    private static final String IMG_PATH_48 = ModUtil.getPowerImg48();
    private static final TextureAtlas.AtlasRegion REGION_128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_128), 0, 0, 80, 80);
    private static final TextureAtlas.AtlasRegion REGION_48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_48), 0, 0, 32, 32);

    public DamageBoost(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = amount;
        this.canGoNegative = true;

        this.region128 = REGION_128;
        this.region48 = REGION_48;

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            this.addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount));
        }
    }
}
