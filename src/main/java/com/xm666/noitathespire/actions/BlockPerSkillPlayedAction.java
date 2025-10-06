package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BlockPerSkillPlayedAction extends AbstractGameAction {

    public BlockPerSkillPlayedAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
    }

    public void update() {
        this.isDone = true;
        if (this.target != null && this.target.currentHealth > 0) {
            int count = 0;

            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                if (c.type == AbstractCard.CardType.SKILL) {
                    ++count;
                }
            }

            --count;

            for (int i = 0; i < count; ++i) {
                this.addToTop(new GainBlockAction(this.target, this.source, this.amount));
            }
        }

    }
}
