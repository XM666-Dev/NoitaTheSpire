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
import com.xm666.noitathespire.actions.DeckToTopAction;
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
    private static final String IMG_PATH_128 = ModUtil.getPowerImg();
    private static final String IMG_PATH_48 = ModUtil.getPowerImg48();
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
        String[] names = cards.stream().map(c -> c.name.replace(" ", "")).toArray(String[]::new);
        String cardsString = String.join(",", names);
        this.description = String.format(DESCRIPTIONS[0], cardsString);
    }

    public void stackPower(Collection<AbstractCard> cards) {
        this.cards.addAll(cards);
    }

    @Override
    public void onShuffle() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(
                new DeckToTopAction(
                        cards
                )
        );
        shuffledThisTurn = true;
        usedCards.clear();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        shuffledThisTurn = false;
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
