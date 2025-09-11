package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.xm666.noitathespire.powers.MeleeCounter;

public class RevengeKickAction extends AbstractGameAction {
    private final KickAction kickAction;

    public RevengeKickAction(AbstractCreature source, int amount, KickAction kickAction) {
        setValues(target, source, amount);
        this.kickAction = kickAction;
    }

    public void update() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == amount) {
            if (!kickAction.success) {
                this.addToTop(new PressEndTurnButtonAction());
            }
            this.addToTop(
                    new ApplyPowerAction(
                            source,
                            source,
                            new MeleeCounter(
                                    source,
                                    0
                            )
                    )
            );
        }

        this.isDone = true;
    }
}
