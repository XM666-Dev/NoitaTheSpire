package com.xm666.noitathespire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.xm666.noitathespire.actions.ChainsawAction;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.powers.RechargeSpeed;
import com.xm666.noitathespire.powers.Spread;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class Chainsaw extends VariableCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModUtil.getCardImg();
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private final int rechargeAmount;

    public Chainsaw() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 5;
        this.variable = this.baseVariable = 1;
        this.rechargeAmount = 1;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NoitaTheSpire.playAudio("digger_create_0");
        this.addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(
                                p,
                                this.damage
                        ),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                )
        );
        this.addToBot(
                new ChainsawAction(p, magicNumber)
        );
        this.addToBot(
                new ApplyPowerAction(
                        p,
                        p,
                        new Spread(
                                p,
                                variable
                        ),
                        variable
                )
        );
        this.addToBot(
                new ApplyPowerAction(
                        p,
                        p,
                        new RechargeSpeed(
                                p,
                                rechargeAmount
                        )
                )
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
