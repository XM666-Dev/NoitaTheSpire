package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.xm666.noitathespire.cards.KineticCard;

import java.util.ArrayList;

public class KickAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("NoitaTheSpire:KickAction");
        TEXT = uiStrings.TEXT;
    }

    private final AbstractPlayer p;
    private final int selectAmount;
    private final ArrayList<AbstractCard> cannotSelect = new ArrayList<>();
    public boolean success;
    protected boolean anyNumber;
    protected boolean canPickZero;

    public KickAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.USE;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.selectAmount = amount;
        this.anyNumber = false;
        this.canPickZero = true;
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
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], selectAmount, anyNumber, canPickZero, false, false, canPickZero);
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

    private boolean isSelectable(AbstractCard c) {
        return c instanceof KineticCard && c.hasEnoughEnergy();
    }

    protected void apply(AbstractCard c) {
        KineticCard kineticCard = (KineticCard) c;
        kineticCard.kineticThisPlay = true;
        this.addToTop(new NewQueueCardAction(c, target));
        success = true;
    }
}
