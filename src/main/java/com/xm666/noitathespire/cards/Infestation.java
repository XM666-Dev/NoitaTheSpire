package com.xm666.noitathespire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.xm666.noitathespire.actions.LimitedBouncyAction;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.powers.Bouncing;
import com.xm666.noitathespire.powers.BouncingCast;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class Infestation extends VariableCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModUtil.getCardImg();
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private final int bouncingCount;

    public Infestation() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 3;
        this.magicNumber = this.baseMagicNumber = 2;
        this.variable = this.baseVariable = 1;
        this.bouncingCount = 2;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeMagicNumber(1);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = baseMagicNumber;
        int count = magicNumber;
        MonsterGroup monsters = AbstractDungeon.getMonsters();
        for (AbstractMonster monster : monsters.monsters) {
            if (!monster.halfDead && !monster.isDying && !monster.isEscaping) {
                count += variable;
            }
        }
        magicNumber = count;
        isMagicNumberModified = count != baseMagicNumber;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NoitaTheSpire.playAudio("bullet_burst_of_air_0");
        MonsterGroup monsters = AbstractDungeon.getMonsters();
        int count = magicNumber;
        for (AbstractMonster monster : monsters.monsters) {
            if (!monster.halfDead && !monster.isDying && !monster.isEscaping) {
                count += variable;
            }
        }
        for (int i = 0; i < count; ++i) {
            AbstractMonster monster = monsters.getRandomMonster(true);
            this.addToBot(
                    new LimitedBouncyAction(
                            monster,
                            new DamageInfo(
                                    p,
                                    damage
                            )
                    )
            );
        }
        for (int i = 0; i < bouncingCount; ++i) {
            BouncingCast bouncingCast = (BouncingCast) p.getPower(BouncingCast.POWER_ID);
            int amount = bouncingCast != null ? bouncingCast.amount : 0;
            this.addToBot(
                    new ApplyPowerAction(
                            p,
                            p,
                            new Bouncing(
                                    p,
                                    damage + amount
                            )
                    )
            );
        }
    }

    @Override
    public int baseValue(char c) {
        if (c == 'C') {
            return bouncingCount;
        }
        return super.baseValue(c);
    }

    @Override
    public int value(char c) {
        if (c == 'C') {
            return bouncingCount;
        }
        return super.value(c);
    }
}
