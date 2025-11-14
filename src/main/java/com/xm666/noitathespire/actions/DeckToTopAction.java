package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collection;

public class DeckToTopAction extends AbstractGameAction {
    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("NoitaTheSpire:DeckToTopAction").TEXT;
    }

    private final AbstractPlayer p;
    private final AbstractCard[] cards;
    private final int numberOfCards;
    private final boolean optional;

    public DeckToTopAction(Collection<AbstractCard> cards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;

        this.cards = cards.toArray(new AbstractCard[]{});
        this.numberOfCards = 0;
        this.optional = false;
    }

    public DeckToTopAction(int numberOfCards, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;

        this.cards = null;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.p.drawPile.isEmpty() && this.numberOfCards > 0) {
                if (this.p.drawPile.size() <= this.numberOfCards && !this.optional) {

                    ArrayList<AbstractCard> cardsToMove = new ArrayList<>(this.p.drawPile.group);

                    for (AbstractCard c : cardsToMove) {
                        this.p.drawPile.removeCard(c);
                        this.p.drawPile.addToTop(c);
                    }

                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                    for (AbstractCard c : this.p.drawPile.group) {
                        temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else if (cards != null) {
                for (int i = cards.length - 1; i >= 0; --i) {
                    AbstractCard c = cards[i];
                    if (!this.p.drawPile.contains(c))
                        continue;
                    this.p.drawPile.removeCard(c);
                    this.p.drawPile.addToTop(c);
                }
                this.isDone = true;
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    this.p.drawPile.removeCard(c);
                    this.p.drawPile.addToTop(c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
