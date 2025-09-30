package com.xm666.noitathespire.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.xm666.noitathespire.cards.Water;
import com.xm666.noitathespire.util.ModUtil;

public class WaterPotion extends CustomRelic {
    public static final String ID = ModUtil.getId();
    private static final String IMG_PATH = ModUtil.getRelicImg();
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;

    public WaterPotion() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new WaterPotion();
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        this.addToBot(
                new MakeTempCardInHandAction(
                        new Water()
                )
        );
    }
}

