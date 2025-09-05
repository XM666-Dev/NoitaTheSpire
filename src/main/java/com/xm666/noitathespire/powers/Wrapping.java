package com.xm666.noitathespire.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.xm666.noitathespire.actions.DrawToTopAction;
import com.xm666.noitathespire.util.ModUtil;
import com.xm666.noitathespire.util.OnShufflePower;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Wrapping extends AbstractPower implements OnShufflePower {
    public static final String POWER_ID = ModUtil.getId();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_PATH_128 = "NoitaTheSpire/powers/wrapping.png";
    private static final String IMG_PATH_48 = "NoitaTheSpire/powers/wrapping_48.png";
    private static final TextureAtlas.AtlasRegion REGION_128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_128), 0, 0, 80, 80);
    private static final TextureAtlas.AtlasRegion REGION_48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_48), 0, 0, 32, 32);
    private final LinkedHashSet<AbstractCard> cards;
    private final HashSet<AbstractCard> usedCards;
    private boolean shuffledThisTurn;
    private AbstractCard returnedCard;

    public Wrapping(AbstractCreature owner, int amount, Collection<AbstractCard> cards) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = amount;
        this.cards = new LinkedHashSet<>(cards);
        this.usedCards = new HashSet<>();

        this.region128 = REGION_128;
        this.region48 = REGION_48;

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], String.join(",", cards.stream().map(c -> c.name).toArray(String[]::new)));
    }

    public void stackPower(Collection<AbstractCard> cards) {
        this.cards.addAll(cards);
    }

    @Override
    public void onShuffle() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(
                new DrawToTopAction(
                        owner,
                        owner,
                        cards
                )
        );
        shuffledThisTurn = true;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        shuffledThisTurn = false;
        usedCards.clear();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if (usedCards.contains(card) || !cards.contains(card) || !shuffledThisTurn) {
            return;
        }
        usedCards.add(card);
        this.flash();
        if (!card.returnToHand) {
            card.returnToHand = true;
            returnedCard = card;
        }
    }

    @Override
    public void onDrawOrDiscard() {
        super.onDrawOrDiscard();
        if (returnedCard != null) {
            returnedCard.returnToHand = false;
            returnedCard = null;
        }
    }
}
