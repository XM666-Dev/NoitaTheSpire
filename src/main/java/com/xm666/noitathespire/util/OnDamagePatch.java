package com.xm666.noitathespire.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage"
)
public class OnDamagePatch {
    @SpirePrefixPatch
    public static void onDamage(AbstractPlayer __instance, DamageInfo info) {
        for (AbstractCard c : __instance.hand.group) {
            if (c instanceof OnDamageCard) {
                ((OnDamageCard) c).onDamage(info);
            }
        }
    }
}
