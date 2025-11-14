package com.xm666.noitathespire.mod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.xm666.noitathespire.cards.VariableCard;
import com.xm666.noitathespire.characters.Mina;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA;
import static com.xm666.noitathespire.characters.Mina.PlayerColorEnum.MINA_PURPLE;

@SpireInitializer
public class NoitaTheSpire implements EditCharactersSubscriber, EditStringsSubscriber, EditCardsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, AddAudioSubscriber {
    public static final Color COLOR = new Color(0x9b6f9aff);
    private static final String CHARACTER_BUTTON = "NoitaTheSpire/characters/mina/characterButton.png";
    private static final String CHARACTER_PORTRAIT = "NoitaTheSpire/characters/mina/characterPortrait.jpg";
    private static final String BG_ATTACK_512 = "NoitaTheSpire/characters/mina/cardui/bg_attack_512.png";
    private static final String BG_SKILL_512 = "NoitaTheSpire/characters/mina/cardui/bg_skill_512.png";
    private static final String BG_POWER_512 = "NoitaTheSpire/characters/mina/cardui/bg_power_512.png";
    private static final String CARD_ORB_512 = "NoitaTheSpire/characters/mina/cardui/card_orb_512.png";
    private static final String BG_ATTACK_1024 = "NoitaTheSpire/characters/mina/cardui/bg_attack.png";
    private static final String BG_SKILL_1024 = "NoitaTheSpire/characters/mina/cardui/bg_skill.png";
    private static final String BG_POWER_1024 = "NoitaTheSpire/characters/mina/cardui/bg_power.png";
    private static final String CARD_ORB_1024 = "NoitaTheSpire/characters/mina/cardui/card_orb.png";
    private static final String ENERGY_ORB = "NoitaTheSpire/characters/mina/cardui/energy_orb.png";
    private static final HashMap<String, Integer> audioCounts = new HashMap<>();

    public NoitaTheSpire() {
        BaseMod.subscribe(this);
        BaseMod.addColor(MINA_PURPLE, COLOR, COLOR, COLOR, COLOR, COLOR, COLOR, COLOR, BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, CARD_ORB_512, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, CARD_ORB_1024, ENERGY_ORB);
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

    public static void playAudioOutsideCombat(String key, float pitchAdjust) {
        if (audioCounts.containsKey(key)) {
            key += MathUtils.random(1, audioCounts.get(key));
        }
        CardCrawlGame.sound.playA(key, pitchAdjust);
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
        BaseMod.loadCustomStringsFile(RelicStrings.class, String.format("NoitaTheSpire/localization/%s/%s.json", language, "relics"));
    }

    @Override
    public void receiveEditCards() {
        AutoAdd autoAdd = new AutoAdd(NoitaTheSpire.class.getSimpleName());
        autoAdd.cards();
        BaseMod.addDynamicVariable(new DynamicVariable() {
            @Override
            public String key() {
                return "NoitaTheSpire:V";
            }

            @Override
            public boolean isModified(AbstractCard abstractCard) {
                if (abstractCard instanceof VariableCard) {
                    return ((VariableCard) abstractCard).isVariableModified;
                }
                return false;
            }

            @Override
            public int value(AbstractCard abstractCard) {
                if (abstractCard instanceof VariableCard) {
                    return ((VariableCard) abstractCard).variable;
                }
                return 0;
            }

            @Override
            public int baseValue(AbstractCard abstractCard) {
                if (abstractCard instanceof VariableCard) {
                    return ((VariableCard) abstractCard).baseVariable;
                }
                return 0;
            }

            @Override
            public boolean upgraded(AbstractCard abstractCard) {
                if (abstractCard instanceof VariableCard) {
                    return ((VariableCard) abstractCard).upgradedVariable;
                }
                return false;
            }
        });
        for (char c : new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', /*'V',*/ 'W', 'X', 'Y', 'Z'}) {
            BaseMod.addDynamicVariable(new DynamicVariable() {
                @Override
                public String key() {
                    return "NoitaTheSpire:" + c;
                }

                @Override
                public boolean isModified(AbstractCard abstractCard) {
                    if (abstractCard instanceof VariableCard) {
                        return ((VariableCard) abstractCard).isModified(c);
                    }
                    return false;
                }

                @Override
                public int value(AbstractCard abstractCard) {
                    if (abstractCard instanceof VariableCard) {
                        return ((VariableCard) abstractCard).value(c);
                    }
                    return 0;
                }

                @Override
                public int baseValue(AbstractCard abstractCard) {
                    if (abstractCard instanceof VariableCard) {
                        return ((VariableCard) abstractCard).baseValue(c);
                    }
                    return 0;
                }

                @Override
                public boolean upgraded(AbstractCard abstractCard) {
                    if (abstractCard instanceof VariableCard) {
                        return ((VariableCard) abstractCard).upgraded(c);
                    }
                    return false;
                }
            });
        }
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
    public void receiveEditRelics() {
        AutoAdd autoAdd = new AutoAdd(NoitaTheSpire.class.getSimpleName());
        autoAdd.any(AbstractRelic.class, (info, relic) -> {
            BaseMod.addRelic(relic, RelicType.SHARED);
            if (info.seen) {
                UnlockTracker.unlockCard(relic.relicId);
            }
        });
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
        addAudio("player_throw_0", 4);
        addAudio("bullet_rocket_0", 3);
        addAudio("jump_gravel_0", 3);
        addAudio("land_gravel_0", 3);
        addAudio("land_water_0", 3);
        addAudio("rock_add_0", 4);
        addAudio("start_0", 3);
        addAudio("bomb_0", 3);
        addAudio("bullet_laser_0", 3);
        addAudio("bullet_arrow_0", 3);
        addAudio("shield_activate_0", 3);
        addAudio("shield_block_0", 3);
        addAudio("shield_deactivate");
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
