package com.xm666.noitathespire.mod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.xm666.noitathespire.characters.Mina;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA;
import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

@SpireInitializer
public class NoitaTheSpire implements EditCharactersSubscriber, EditStringsSubscriber, EditCardsSubscriber, EditKeywordsSubscriber, AddAudioSubscriber {
    public static final Color COLOR = new Color(0x9b6f9a);
    private static final String CHARACTER_BUTTON = "NoitaTheSpire/characters/mina/characterButton.png";
    private static final String CHARACTER_PORTRAIT = "NoitaTheSpire/characters/mina/characterPortrait.jpg";
    private static final String BG_ATTACK_512 = "NoitaTheSpire/characters/mina/cardui/bg_attack_512.png";
    private static final String BG_POWER_512 = "NoitaTheSpire/characters/mina/cardui/bg_power_512.png";
    private static final String BG_SKILL_512 = "NoitaTheSpire/characters/mina/cardui/bg_skill_512.png";
    private static final String CARD_ORB_512 = "NoitaTheSpire/characters/mina/cardui/card_orb_512.png";
    private static final String BG_ATTACK_1024 = "NoitaTheSpire/characters/mina/cardui/bg_attack.png";
    private static final String BG_POWER_1024 = "NoitaTheSpire/characters/mina/cardui/bg_power.png";
    private static final String BG_SKILL_1024 = "NoitaTheSpire/characters/mina/cardui/bg_skill.png";
    private static final String CARD_ORB_1024 = "NoitaTheSpire/characters/mina/cardui/card_orb.png";
    private static final String ENERGY_ORB = "NoitaTheSpire/characters/mina/cardui/energy_orb.png";
    private static final HashMap<String, Integer> audioCounts = new HashMap<>();

    public NoitaTheSpire() {
        BaseMod.subscribe(this);
        BaseMod.addColor(MINA_PURPLE, COLOR, COLOR, COLOR, COLOR, COLOR, COLOR, COLOR, BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENERGY_ORB, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, CARD_ORB_1024, CARD_ORB_512);
    }

    public static void initialize() {
        new NoitaTheSpire();
    }

    public static void playAudio(String key) {
        if (audioCounts.containsKey(key)) {
            key += MathUtils.random(1, audioCounts.get(key));
        }
        AbstractDungeon.actionManager.addToBottom(new SFXAction(key));
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Mina(CardCrawlGame.playerName), CHARACTER_BUTTON, CHARACTER_PORTRAIT, MINA);
    }

    @Override
    public void receiveEditStrings() {
        String language = Settings.language.name().toLowerCase(Locale.ROOT);
        BaseMod.loadCustomStringsFile(CharacterStrings.class, String.format("NoitaTheSpire/localization/%s/%s.json", language, "characters"));
        BaseMod.loadCustomStringsFile(CardStrings.class, String.format("NoitaTheSpire/localization/%s/%s.json", language, "cards"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, String.format("NoitaTheSpire/localization/%s/%s.json", language, "powers"));
        BaseMod.loadCustomStringsFile(UIStrings.class, String.format("NoitaTheSpire/localization/%s/%s.json", language, "ui"));
    }

    @Override
    public void receiveEditCards() {
        AutoAdd autoAdd = new AutoAdd(NoitaTheSpire.class.getSimpleName());
        autoAdd.cards();
    }

    @Override
    public void receiveEditKeywords() {
        String id = NoitaTheSpire.class.getSimpleName().toLowerCase(Locale.ROOT);
        String language = Settings.language.name().toLowerCase(Locale.ROOT);
        String filepath = String.format("NoitaTheSpire/localization/%s/keywords.json", language);
        Map<String, String> keywords = loadKeywords(filepath);
        for (String key : keywords.keySet()) {
            BaseMod.addKeyword(id, key, new String[]{key}, keywords.get(key));
        }
    }

    @Override
    public void receiveAddAudio() {
        addAudio("spell_shoot_ver2_", 3);
        addAudio("bullet_bounce_0", 4);
        addAudio("perk_misc");
        addAudio("digger_create_0", 6);
        addAudio("spell_shoot_general_ver6_0", 3);
        addAudio("bullet_burst_of_air_0", 2);
        addAudio("player_kick_0", 4);
        addAudio("spell_shoot_general_ver4_0", 3);
        addAudio("item_move_success");
    }

    private Map<String, String> loadKeywords(String filepath) {
        String jsonString = Gdx.files.internal(filepath).readString(String.valueOf(StandardCharsets.UTF_8));
        Gson gson = new Gson();
        return gson.fromJson(jsonString, HashMap.class);
    }

    private void addAudio(String key, int count) {
        for (int i = 1; i <= count; ++i) {
            String audioKey = key + i;
            BaseMod.addAudio(audioKey, String.format("NoitaTheSpire/audio/sound/%s.wav", audioKey));
        }
        audioCounts.put(key, count);
    }

    private void addAudio(String key) {
        BaseMod.addAudio(key, String.format("NoitaTheSpire/audio/sound/%s.wav", key));
    }
}
