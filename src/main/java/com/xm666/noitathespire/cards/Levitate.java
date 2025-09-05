package com.xm666.noitathespire.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.xm666.noitathespire.mod.NoitaTheSpire;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class Levitate extends CustomCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "NoitaTheSpire/cards/levitate.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Levitate() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NoitaTheSpire.playAudio("digger_create_0");
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(
                        p,
                        block
                )
        );
    }
}
