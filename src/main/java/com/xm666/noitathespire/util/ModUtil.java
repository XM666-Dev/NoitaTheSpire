package com.xm666.noitathespire.util;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.xm666.noitathespire.mod.NoitaTheSpire;

import java.util.ArrayList;

public class ModUtil {
    public static String getId() {
        return NoitaTheSpire.class.getSimpleName() + ":" + getSimpleName(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public static String getSimpleName(String className) {
        if (className == null) {
            return "";
        }
        int index = className.lastIndexOf('$');
        index = Math.max(className.lastIndexOf('.', index == -1 ? className.length() - 1 : index), index);
        return className.substring(index + 1);
    }

    public static AbstractCard getRandomAttackCardFromHand() {
        return getRandomAttackCardFromHand(null);
    }

    public static AbstractCard getRandomAttackCardFromHand(AbstractCard exception) {
        ArrayList<AbstractCard> attacks = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.ATTACK && c != exception) {
                attacks.add(c);
            }
        }
        if (attacks.isEmpty()) {
            return null;
        }
        return attacks.get(AbstractDungeon.cardRandomRng.random(attacks.size() - 1));
    }

    public static AbstractMonster getRandomMonster(MonsterGroup monsters, AbstractMonster exception, boolean aliveOnly) {
        if (monsters.areMonstersBasicallyDead()) {
            return null;
        } else if (exception == null) {
            if (aliveOnly) {
                ArrayList<AbstractMonster> tmp = new ArrayList<>();

                for (AbstractMonster m : monsters.monsters) {
                    if (!m.halfDead && !m.isDying && !m.isEscaping) {
                        tmp.add(m);
                    }
                }

                if (tmp.isEmpty()) {
                    return null;
                } else {
                    return tmp.get(MathUtils.random(0, tmp.size() - 1));
                }
            } else {
                return monsters.monsters.get(MathUtils.random(0, monsters.monsters.size() - 1));
            }
        } else if (monsters.monsters.size() == 1) {
            return monsters.monsters.get(0);
        } else if (aliveOnly) {
            ArrayList<AbstractMonster> aliveMonsters = new ArrayList<>();

            for (AbstractMonster m : monsters.monsters) {
                if (!m.halfDead && !m.isDying && !m.isEscaping) {
                    aliveMonsters.add(m);
                }
            }

            ArrayList<AbstractMonster> tmp = new ArrayList<>();
            for (AbstractMonster m : aliveMonsters) {
                if (!exception.equals(m) || aliveMonsters.size() == 1) {
                    tmp.add(m);
                }
            }

            if (tmp.isEmpty()) {
                return null;
            } else {
                return tmp.get(MathUtils.random(0, tmp.size() - 1));
            }
        } else {
            ArrayList<AbstractMonster> tmp = new ArrayList<>();

            for (AbstractMonster m : monsters.monsters) {
                if (!exception.equals(m)) {
                    tmp.add(m);
                }
            }

            return tmp.get(MathUtils.random(0, tmp.size() - 1));
        }
    }
}
