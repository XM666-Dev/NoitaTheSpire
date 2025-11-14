package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DeckExhaustAction extends AbstractGameAction {
    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("NoitaTheSpire:DeckExhaustAction").TEXT;
    }

    private final AbstractPlayer player;
    private final int numberOfCards;
    private final boolean optional;

    public DeckExhaustAction(int numberOfCards, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public DeckExhaustAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                if (this.player.drawPile.size() <= this.numberOfCards && !this.optional) {

                    ArrayList<AbstractCard> cardsToMove = new ArrayList<>(this.player.drawPile.group);

                    for (AbstractCard c : cardsToMove) {
                        this.player.drawPile.moveToExhaustPile(c);
                    }

                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                    for (AbstractCard c : this.player.drawPile.group) {
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
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    this.player.drawPile.moveToExhaustPile(c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
