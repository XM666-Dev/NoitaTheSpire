package com.xm666.noitathespire.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "brokeBlock"
)
public class OnBlockBrokenPatch {
    @SpirePrefixPatch
    public static void onBlockBroken(AbstractCreature __instance) {
        if (__instance instanceof AbstractPlayer) {
            AbstractPlayer p = (AbstractPlayer) __instance;
            for (AbstractCard c : p.hand.group) {
                if (c instanceof OnBlockBrokenCard) {
                    ((OnBlockBrokenCard) c).onBlockBroken();
                }
            }
        }
    }
}
