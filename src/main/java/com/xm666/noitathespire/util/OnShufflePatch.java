package com.xm666.noitathespire.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

@SpirePatch(
        clz = CardGroup.class,
        method = "shuffle",
        paramtypez = {Random.class}
)
public class OnShufflePatch {
    @SpirePostfixPatch
    public static void onShuffle() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnShufflePower) {
                ((OnShufflePower) p).onShuffle();
            }
        }
    }
}
