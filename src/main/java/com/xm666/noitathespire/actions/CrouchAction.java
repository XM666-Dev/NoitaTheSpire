package com.xm666.noitathespire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CrouchAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public CrouchAction(AbstractCreature source, int amount) {
        setValues(null, source, amount);
        p = (AbstractPlayer) source;
    }

    public void update() {
        this.isDone = true;
        if (p.hand.getSkills().isEmpty()) {
            this.addToTop(new GainBlockAction(p, amount));
        }
    }
}
