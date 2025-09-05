package com.xm666.noitathespire.cards;

import basemod.abstracts.CustomCard;
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
import com.xm666.noitathespire.powers.Bouncing;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class Infestation extends CustomCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "NoitaTheSpire/cards/infestation.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Infestation() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 2;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NoitaTheSpire.playAudio("bullet_burst_of_air_0");
        MonsterGroup monsters = AbstractDungeon.getMonsters();
        int count = magicNumber;
        for (AbstractMonster monster : monsters.monsters) {
            if (!monster.halfDead && !monster.isDying && !monster.isEscaping) {
                ++count;
            }
        }
        for (int i = 0; i < count; ++i) {
            AbstractMonster monster = monsters.getRandomMonster(true);
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            monster,
                            p,
                            new Bouncing(
                                    monster,
                                    damage
                            )
                    )
            );
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(
                            monster,
                            new DamageInfo(
                                    p,
                                    damage
                            )
                    )
            );
        }
        for (int i = 0; i < 3; ++i) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            p,
                            p,
                            new Bouncing(
                                    p,
                                    damage
                            )
                    )
            );
        }
    }
}
