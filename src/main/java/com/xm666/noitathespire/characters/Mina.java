package com.xm666.noitathespire.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.xm666.noitathespire.cards.*;
import com.xm666.noitathespire.mod.NoitaTheSpire;

import java.util.ArrayList;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA;

public class Mina extends CustomPlayer {
    private static final String CHARACTER_SHOULDER_1 = "NoitaTheSpire/characters/mina/shoulder.png";
    private static final String CHARACTER_SHOULDER_2 = "NoitaTheSpire/characters/mina/shoulder2.png";
    private static final String CORPSE_IMAGE = "NoitaTheSpire/characters/mina/corpse.png";
    private static final String[] ORB_TEXTURES = new String[]{
            "NoitaTheSpire/characters/mina/topPanel/l1.png",
            "NoitaTheSpire/characters/mina/topPanel/border.png",
            "NoitaTheSpire/characters/mina/topPanel/l2.png"
    };
    private static final float[] LAYER_SPEED = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("NoitaTheSpire:Mina");

    public Mina(String name) {
        super(name, MINA, ORB_TEXTURES, "NoitaTheSpire/characters/mina/topPanel/energyVFX.png", LAYER_SPEED, null, null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

        this.initializeClass(
                "NoitaTheSpire/characters/mina/character.png",
                CHARACTER_SHOULDER_2, CHARACTER_SHOULDER_1,
                CORPSE_IMAGE,
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F,
                new EnergyManager(3)
        );
    }

    public ArrayList<String> getStartingDeck() {
        class Spell {
            private final String id;
            private final int count;

            public Spell(String id, int count) {
                this.id = id;
                this.count = count;
            }
        }
        ArrayList<String> deck = new ArrayList<>();
        Spell[] spells = {
                new Spell(Strike.ID, 4),
                new Spell(RubberBall.ID, 4),
                new Spell(SpitterBolt.ID, 4),
                new Spell(EnergySphere.ID, 2)
        };
        Spell spell = spells[0];
        for (int i = 0; i < spell.count; ++i) {
            deck.add(spell.id);
        }
        for (int i = 0; i < 4; ++i) {
            deck.add(Defend.ID);
        }
        deck.add(Kick.ID);
        deck.add(Bomb.ID);
        return deck;
    }

    public ArrayList<String> getStartingRelics() {
        return new ArrayList<>();
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0],
                characterStrings.TEXT[0],
                100,
                100,
                0,
                0,
                5,
                this,
                this.getStartingRelics(),
                this.getStartingDeck(),
                false
        );
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return PlayerColorEnum.MINA_PURPLE;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public Color getCardTrailColor() {
        return NoitaTheSpire.COLOR;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        NoitaTheSpire.playAudioOutsideCombat("bullet_rocket_0", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("NoitaTheSpire/characters/mina/scenes/1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("NoitaTheSpire/characters/mina/scenes/2.png"));
        panels.add(new CutscenePanel("NoitaTheSpire/characters/mina/scenes/3.png"));
        return panels;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Mina(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return NoitaTheSpire.COLOR;
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public Color getCardRenderColor() {
        return NoitaTheSpire.COLOR;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.BLUNT_LIGHT, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_LIGHT, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_LIGHT};
    }

    public static class PlayerColorEnum {
        @SpireEnum
        public static PlayerClass MINA;

        @SpireEnum
        public static AbstractCard.CardColor MINA_PURPLE;
    }

    public static class PlayerLibraryEnum {
        @SpireEnum
        public static CardLibrary.LibraryType MINA_PURPLE;
    }
}