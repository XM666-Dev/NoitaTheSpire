package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.xm666.noitathespire.powers.CastSpeed;

import java.util.ArrayList;

public class HeavySpreadAction extends AbstractGameAction {
    public HeavySpreadAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
    }

    @Override
    public void update() {
        CardGroup attacks = AbstractDungeon.player.hand.getAttacks();
        ArrayList<AbstractCard> cards = new ArrayList<>(attacks.group);
        int attackAmount = attacks.size();
        for (int i = 0; i < attackAmount; ++i) {
            int index = AbstractDungeon.cardRandomRng.random(cards.size() - 1);
            AbstractCard randomAttackCard = cards.remove(index);
            if (randomAttackCard != null)
                this.addToTop(new DiscardSpecificCardAction(randomAttackCard));
            this.addToBot(
                    new ApplyPowerAction(target, source, new CastSpeed(target, amount))
            );
        }
        this.isDone = true;
    }
}
