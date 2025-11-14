package com.xm666.noitathespire.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.xm666.noitathespire.util.ModUtil;
import com.xm666.noitathespire.util.OnManualDiscardPower;

import java.util.ArrayList;

public class BoomerangCast extends AbstractPower implements OnManualDiscardPower {
    public static final String POWER_ID = ModUtil.getId();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_PATH_128 = ModUtil.getPowerImg();
    private static final String IMG_PATH_48 = ModUtil.getPowerImg48();
    private static final TextureAtlas.AtlasRegion REGION_128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_128), 0, 0, 80, 80);
    private static final TextureAtlas.AtlasRegion REGION_48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_48), 0, 0, 32, 32);
    private static final ArrayList<AbstractCard> cardsDiscardedLastTurn = new ArrayList<>();

    public BoomerangCast(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = amount;

        this.region128 = REGION_128;
        this.region48 = REGION_48;

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.addToBot(
                new ApplyPowerAction(
                        owner,
                        owner,
                        new Spread(
                                owner,
                                amount
                        ),
                        amount
                )
        );
        for (AbstractCard c : cardsDiscardedLastTurn) {
            this.addToBot(new DiscardToHandAction(c));
        }
        cardsDiscardedLastTurn.clear();
    }

    @Override
    public void onManualDiscard(AbstractCard c) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            cardsDiscardedLastTurn.add(c);
        }
    }
}
