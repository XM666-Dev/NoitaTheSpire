package com.xm666.noitathespire.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.xm666.noitathespire.powers.MeleeCounter;

@AutoAdd.Ignore
public class KineticCard extends CustomCard {
    public boolean kineticThisPlay = false;

    public KineticCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        kineticThisPlay = false;
        if (p.hasPower(MeleeCounter.POWER_ID)) {
            this.addToBot(new PressEndTurnButtonAction());
        }
    }

    public void onKineticPlay() {
    }
}
