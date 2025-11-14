package com.xm666.noitathespire.powers;

//public class FireballOrbiting extends AbstractPower {
//    public static final String POWER_ID = ModUtil.getId();
//    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
//    private static final String NAME = powerStrings.NAME;
//    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
//    private static final String IMG_PATH_128 = ModUtil.getPowerImg();
//    private static final String IMG_PATH_48 = ModUtil.getPowerImg48();
//    private static final TextureAtlas.AtlasRegion REGION_128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_128), 0, 0, 80, 80);
//    private static final TextureAtlas.AtlasRegion REGION_48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_PATH_48), 0, 0, 32, 32);
//
//    public FireballOrbiting(AbstractCreature owner, int amount) {
//        this.name = NAME;
//        this.ID = POWER_ID;
//        this.owner = owner;
//        this.type = PowerType.BUFF;
//
//        this.amount = amount;
//
//        this.region128 = REGION_128;
//        this.region48 = REGION_48;
//
//        this.updateDescription();
//    }
//
//    public void updateDescription() {
//        this.description = String.format(DESCRIPTIONS[0], this.amount, 6);
//    }
//
//    @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if (card.type != AbstractCard.CardType.ATTACK) return;
//        ArrayList<Fireball> fireballs = AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof Fireball).map(c -> (Fireball) c).collect(Collectors.toCollection(ArrayList::new));
//        int amount = Math.min(this.amount, fireballs.size());
//        for (int i = 0; i < amount; ++i) {
//            int index = AbstractDungeon.cardRandomRng.random(fireballs.size() - 1);
//            Fireball fireball = fireballs.remove(index);
//            this.addToBot(
//                    new ExhaustSpecificCardAction(
//                            fireball,
//                            AbstractDungeon.player.hand
//                    )
//            );
//            this.addToBot(
//                    new DamageRandomEnemyAction(
//                            new DamageInfo(
//                                    AbstractDungeon.player,
//                                    fireball.baseDamage,
//                                    DamageInfo.DamageType.THORNS
//                            ),
//                            AbstractGameAction.AttackEffect.FIRE
//                    )
//            );
//        }
//    }
//
//    @Override
//    public void atEndOfTurn(boolean isPlayer) {
//        super.atEndOfTurn(isPlayer);
//        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
//    }
//}
