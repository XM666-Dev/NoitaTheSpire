package com.xm666.noitathespire.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.powers.Spread;
import com.xm666.noitathespire.util.ModUtil;
import com.xm666.noitathespire.util.OnDrawCard;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class Arrow extends CustomCard implements OnDrawCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModUtil.getCardImg();
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int drawCountThisTurn;

    public Arrow() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NoitaTheSpire.playAudio("bullet_arrow_0");
        this.addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(
                                p,
                                damage
                        ),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                )
        );
        this.addToBot(
                new ApplyPowerAction(
                        p,
                        p,
                        new Spread(
                                p,
                                -magicNumber
                        )
                )
        );
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        drawCountThisTurn = 0;
    }

    @Override
    public void onDraw(int amount) {
        drawCountThisTurn += amount;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        damage += (int) (damage * drawCountThisTurn * 0.1);
        if (this.baseDamage != damage) {
            isDamageModified = true;
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = drawCountThisTurn;
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.rawDescription = this.rawDescription + CARD_STRINGS.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription = this.rawDescription + CARD_STRINGS.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = this.rawDescription + CARD_STRINGS.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();

        damage += (int) (damage * drawCountThisTurn * 0.1);
        if (this.baseDamage != damage) {
            isDamageModified = true;
        }
    }
}
