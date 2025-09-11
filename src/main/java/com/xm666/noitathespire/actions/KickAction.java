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
    private static final float DURATION;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("NoitaTheSpire:KickAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }

    private final AbstractPlayer p;
    public boolean success;

    public KickAction(AbstractCreature target, AbstractCreature source) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.USE;
        this.duration = DURATION;
        this.p = (AbstractPlayer) source;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            ArrayList<KineticCard> cardsToKick = new ArrayList<>();
            for (AbstractCard c : p.hand.group) {
                if (c instanceof KineticCard && c.hasEnoughEnergy()) {
                    cardsToKick.add((KineticCard) c);
                }
            }
            if (cardsToKick.isEmpty()) {
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, true, true);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
                    this.p.hand.addToHand(c);
                    if (c instanceof KineticCard && c.hasEnoughEnergy()) {
                        KineticCard kineticCard = (KineticCard) c;
                        kineticCard.kineticThisPlay = true;
                        this.addToTop(new NewQueueCardAction(c, target));
                        success = true;
                    }
                }
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }
}
