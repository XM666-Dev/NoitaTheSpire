package com.xm666.noitathespire.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.powers.CastSpeed;
import com.xm666.noitathespire.util.ModUtil;

import java.util.ArrayList;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class ChainBolt extends VariableCard {
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

    public ChainBolt() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 16;
        this.magicNumber = this.baseMagicNumber = 4;
        this.variable = this.baseVariable = 1;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeDamage(4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MonsterGroup monsters = AbstractDungeon.getMonsters();
        ArrayList<AbstractMonster> aliveMonsters = new ArrayList<>();

        for (AbstractMonster monster : monsters.monsters) {
            if (!monster.halfDead && !monster.isDying && !monster.isEscaping) {
                aliveMonsters.add(monster);
            }
        }
        int count = Math.min(magicNumber, aliveMonsters.size());
        for (int i = 0; i < count; ++i) {
            NoitaTheSpire.playAudio("bullet_laser_0");
            AbstractMonster monster = aliveMonsters.remove(MathUtils.random(0, aliveMonsters.size() - 1));
            this.addToBot(
                    new DamageAction(
                            monster,
                            new DamageInfo(
                                    p,
                                    multiDamage[monsters.monsters.indexOf(monster)]
                            )
                    )
            );
        }
        this.addToBot(
                new ApplyPowerAction(
                        p,
                        p,
                        new CastSpeed(
                                p,
                                -variable
                        ),
                        -variable
                )
        );
    }
}
