package com.xm666.noitathespire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawPower;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.powers.DamageBoost;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class ConcentratedSpells extends VariableCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModUtil.getCardImg();
    private static final int COST = 2;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ConcentratedSpells() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.variable = this.baseVariable = 1;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NoitaTheSpire.playAudio("perk_misc");
        this.addToBot(
                new ApplyPowerAction(p, p, new DamageBoost(p, magicNumber))
        );
        AbstractPower draw = new DrawPower(p, -variable);
        draw.canGoNegative = true;
        this.addToBot(
                new ApplyPowerAction(p, p, draw)
        );
    }
}
