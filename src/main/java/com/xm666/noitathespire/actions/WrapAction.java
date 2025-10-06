package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.powers.Wrapping;

import java.util.Collection;

public class WrapAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    private static final float DURATION;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("NoitaTheSpire:WrapAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }

    private final AbstractPlayer p;

    public WrapAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.p = (AbstractPlayer) target;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() <= amount) {
                applyWrapping(this.p, this.p, this.p.hand.group);
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false, false, false, true);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                applyWrapping(this.p, this.p, AbstractDungeon.handCardSelectScreen.selectedCards.group);
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    this.p.hand.addToHand(c);
                }
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    private void applyWrapping(AbstractCreature target, AbstractCreature source, Collection<AbstractCard> cards) {
        for (int i = 0; i < cards.size(); ++i) {
            NoitaTheSpire.playAudio("item_move_success");
            this.addToBot(new WaitAction(0.25F));
        }
        Wrapping wrapping = (Wrapping) target.getPower(Wrapping.POWER_ID);
        if (wrapping == null) {
            this.addToTop(new ApplyPowerAction(target, source, new Wrapping(target, -1, cards)));
            return;
        }
        wrapping.flash();
        wrapping.stackPower(cards);
        wrapping.updateDescription();
    }
}
