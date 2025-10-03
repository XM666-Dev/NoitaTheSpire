package com.xm666.noitathespire.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

@SpirePatch(
        clz = DrawCardAction.class,
        method = "update"
)
public class OnDrawPatch {
    public static ArrayList<WeakReference<DrawCardAction>> updatedActions = new ArrayList<>();

    @SpirePostfixPatch
    public static void onDraw(DrawCardAction __instance) {
        ArrayList<WeakReference<DrawCardAction>> newUpdatedActions = new ArrayList<>();
        for (WeakReference<DrawCardAction> action : updatedActions) {
            if (action.get() != null) {
                newUpdatedActions.add(action);
            }
        }
        updatedActions = newUpdatedActions;
        for (WeakReference<DrawCardAction> action : newUpdatedActions) {
            if (action.get() == __instance) {
                return;
            }
        }
        newUpdatedActions.add(new WeakReference<>(__instance));
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof OnDrawCard) {
                ((OnDrawCard) c).OnDraw(__instance.amount);
            }
        }
    }
}
