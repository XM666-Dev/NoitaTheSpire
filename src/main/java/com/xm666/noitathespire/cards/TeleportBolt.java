package com.xm666.noitathespire.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.xm666.noitathespire.util.ModUtil;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

public class TeleportBolt extends CustomCard {
    public static final String ID = ModUtil.getId();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModUtil.getCardImg();
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MINA_PURPLE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TeleportBolt() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 15;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeBlock(5);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
                new GainBlockAction(
                        p,
                        p,
                        block
                )
        );
        this.addToBot(new PutOnDeckAction(p, p, 1, false));
    }
}
