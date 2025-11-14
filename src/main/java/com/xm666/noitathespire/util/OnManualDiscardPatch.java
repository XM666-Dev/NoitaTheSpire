package com.xm666.noitathespire.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(
        clz = AbstractCard.class,
        method = "triggerOnManualDiscard"
)
public class OnManualDiscardPatch {
    @SpirePrefixPatch
    public static void onManualDiscard(AbstractCard __instance) {
        //目前实现存在缺陷
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnManualDiscardPower) {
                ((OnManualDiscardPower) p).onManualDiscard(__instance);
            }
        }
    }
}
