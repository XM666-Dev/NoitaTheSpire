package com.xm666.noitathespire.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class VariableCard extends CustomCard {
    public int baseVariable;
    public int variable;
    public boolean upgradedVariable;
    public boolean isVariableModified;

    public VariableCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    protected void upgradeVariable(int amount) {
        this.baseVariable += amount;
        this.variable = this.baseVariable;
        this.upgradedVariable = true;
    }

    public boolean isModified(char c) {
        return false;
    }

    public int value(char c) {
        return 0;
    }

    public int baseValue(char c) {
        return 0;
    }

    public boolean upgraded(char c) {
        return false;
    }
}
