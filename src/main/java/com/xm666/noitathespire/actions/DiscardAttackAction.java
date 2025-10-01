package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class DiscardAttackAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    private static final boolean canPickZero = false;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }

    private final AbstractPlayer p;
    private final int selectAmount;
    private final ArrayList<AbstractCard> cannotSelect = new ArrayList<>();

    public DiscardAttackAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DISCARD;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.selectAmount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!this.isSelectable(c)) {
                    this.cannotSelect.add(c);
                }
            }

            if (this.cannotSelect.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (!canPickZero && this.p.hand.group.size() - this.cannotSelect.size() <= selectAmount) {
                for (AbstractCard c : new ArrayList<>(this.p.hand.group)) {
                    if (this.isSelectable(c)) {
                        apply(c);
                    }
                }
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.cannotSelect);
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], selectAmount, canPickZero, canPickZero, false, false);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                apply(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotSelect) {
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    private boolean isSelectable(AbstractCard card) {
        return card.type.equals(AbstractCard.CardType.ATTACK);
    }

    private void apply(AbstractCard c) {
        this.p.hand.moveToDiscardPile(c);
        c.triggerOnManualDiscard();
        GameActionManager.incrementDiscard(false);
    }
}
