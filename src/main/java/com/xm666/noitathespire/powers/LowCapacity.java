package com.xm666.noitathespire.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.xm666.noitathespire.actions.DiscardDrawBottomAction;
import com.xm666.noitathespire.util.ModUtil;
import com.xm666.noitathespire.util.OnShufflePower;

public class LowCapacity extends AbstractPower implements OnShufflePower {
    public static final String POWER_ID = ModUtil.getId();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_PATH_128 = ModUtil.getPowerImg();
    private static final String IMG_PATH_48 = ModUtil.getPowerImg48();
    private static final TextureAtlas.AtlasRegion REGION_128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_128), 0, 0, 80, 80);
    private static final TextureAtlas.AtlasRegion REGION_48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_48), 0, 0, 32, 32);

    public LowCapacity(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;

        this.amount = amount;

        this.region128 = REGION_128;
        this.region48 = REGION_48;

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void onShuffle() {
        this.flash();
        this.addToBot(
                new DiscardDrawBottomAction(
                        amount
                )
        );
    }
}
