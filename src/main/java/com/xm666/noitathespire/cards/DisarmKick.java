package com.xm666.noitathespire.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.xm666.noitathespire.actions.KickAction;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class DisarmKick extends CustomCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "NoitaTheSpire/cards/disarmKick.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public DisarmKick() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 4;
        this.magicNumber = this.baseMagicNumber = 1;
        this.block = this.baseBlock = 2;
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(1);
        this.upgradeBlock(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NoitaTheSpire.playAudio("player_kick_0");
        this.addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(
                                p,
                                damage
                        ),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                )
        );
        this.addToBot(
                new ApplyPowerAction(
                        m,
                        p,
                        new WeakPower(
                                m,
                                magicNumber,
                                false
                        )
                )
        );
        this.addToBot(
                new RemoveSpecificPowerAction(
                        m,
                        p,
                        FlightPower.POWER_ID
                )
        );
        StrengthPower strengthPower = (StrengthPower) m.getPower(StrengthPower.POWER_ID);
        if (strengthPower == null || strengthPower.amount > 0) {
            int amount = magicNumber + 1;
            this.addToBot(
                    new ApplyPowerAction(
                            m,
                            p,
                            new StrengthPower(
                                    m,
                                    -amount
                            ),
                            -amount
                    )
            );
        }
        this.addToBot(
                new KickAction(m, p)
        );
    }
}
