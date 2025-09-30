package com.xm666.noitathespire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.xm666.noitathespire.powers.FasterRecharge;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class BloodMagic extends VariableCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModUtil.getCardImg();
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private final int rechargeAmount;

    public BloodMagic() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.variable = this.baseVariable = 3;
        this.rechargeAmount = 3;
        this.damage = this.baseDamage = 4;
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(-1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
                new GainEnergyAction(magicNumber)
        );
        this.addToBot(
                new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, variable))
        );
        this.addToBot(
                new ApplyPowerAction(p, p, new FasterRecharge(p, rechargeAmount))
        );
        this.addToBot(
                new LoseHPAction(p, p, damage)
        );
    }

    @Override
    public int baseValue(char c) {
        if (c == 'C') {
            return rechargeAmount;
        }
        return super.baseValue(c);
    }

    @Override
    public int value(char c) {
        if (c == 'C') {
            return rechargeAmount;
        }
        return super.value(c);
    }
}
