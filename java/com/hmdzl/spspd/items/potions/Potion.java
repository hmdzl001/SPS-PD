/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.items.potions;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.ItemStatusHandler;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class Potion extends Item {

	public static final String AC_DRINK = "DRINK";
	
	private static final float TIME_TO_DRINK = Dungeon.isChallenged(Challenges.ITEM_PHOBIA)? 5f : 1f;
	
	protected Integer initials;

	private static final Class<?>[] potions = { PotionOfHealing.class,
			PotionOfExperience.class, PotionOfToxicGas.class,
			PotionOfLiquidFlame.class, PotionOfStrength.class,
			PotionOfParalyticGas.class, PotionOfLevitation.class,
			PotionOfMindVision.class, PotionOfPurity.class,
			PotionOfInvisibility.class, PotionOfMight.class,
			PotionOfFrost.class, PotionOfMending.class,
			PotionOfOverHealing.class, PotionOfShield.class, PotionOfMixing.class};
	
	private static final String[] colors = { "turquoise", "crimson", "azure",
			"jade", "golden", "magenta", "charcoal", "ivory", "amber",
			"bistre", "indigo", "silver", "aqua", "violet", "mihuang"
			,""};
	private static final Integer[] images = { ItemSpriteSheet.POTION_TURQUOISE,
			ItemSpriteSheet.POTION_CRIMSON, ItemSpriteSheet.POTION_AZURE,
			ItemSpriteSheet.POTION_JADE, ItemSpriteSheet.POTION_GOLDEN,
			ItemSpriteSheet.POTION_MAGENTA, ItemSpriteSheet.POTION_CHARCOAL,
			ItemSpriteSheet.POTION_IVORY, ItemSpriteSheet.POTION_AMBER,
			ItemSpriteSheet.POTION_BISTRE, ItemSpriteSheet.POTION_INDIGO,
			ItemSpriteSheet.POTION_SILVER, ItemSpriteSheet.POTION_AQUA,
		    ItemSpriteSheet.POTION_VIOLET, ItemSpriteSheet.POTION_MIHUANG,
			ItemSpriteSheet.POTION_MIX};

	private static ItemStatusHandler<Potion> handler;

	private String color;

	//public boolean ownedByFruit = false;

	{
		stackable = true;
		defaultAction = AC_DRINK;
	}

	@SuppressWarnings("unchecked")
	public static void initColors() {
		handler = new ItemStatusHandler<Potion>(
				(Class<? extends Potion>[]) potions, colors, images, 1);
	}

	public static void save(Bundle bundle) {
		handler.save(bundle);
	}

	@SuppressWarnings("unchecked")
	public static void restore(Bundle bundle) {
		handler = new ItemStatusHandler<Potion>(
				(Class<? extends Potion>[]) potions, colors, images, bundle);
	}

	public Potion() {
		super();
		sync();
	}

	@Override
	public void sync() {
		super.sync();
		image = handler.image(this);
		color = handler.label(this);
	}

    @Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_DRINK);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_DRINK)) {

			if (hero.buff(Locked.class) != null) {
				GLog.w(Messages.get(Potion.class, "locked"));
			} else if (isKnown()
					&& (this instanceof PotionOfLiquidFlame
					|| this instanceof PotionOfToxicGas
					|| this instanceof PotionOfParalyticGas)) {

				GameScene.show(new WndOptions(Messages.get(Potion.class, "harmful"),
						Messages.get(Potion.class, "sure_drink"),
						Messages.get(Potion.class, "yes"), Messages.get(Potion.class, "no")) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							drink(hero);
						}
					}

                });

			} else {
				drink(hero);
			}

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public void doThrow(final Hero hero) {

		if (isKnown()
				&& (this instanceof PotionOfExperience
						|| this instanceof PotionOfHealing
						|| this instanceof PotionOfMindVision
						|| this instanceof PotionOfStrength
						|| this instanceof PotionOfInvisibility 
						|| this instanceof PotionOfMight
						|| this instanceof PotionOfOverHealing
						|| this instanceof PotionOfMending
						|| this instanceof PotionOfShield)) {

			GameScene.show(new WndOptions( Messages.get(Potion.class, "beneficial"),
						Messages.get(Potion.class, "sure_throw"),
						Messages.get(Potion.class, "yes"), Messages.get(Potion.class, "no")) {
				@Override
				protected void onSelect(int index) {
					if (index == 0) {
						Potion.super.doThrow(hero);
					}
				}
            });

		} else {
			super.doThrow(hero);
		}
	}

	protected void drink(Hero hero) {
    if (!(Dungeon.hero.heroClass == HeroClass.FOLLOWER) || Random.Int(10)>=1 ){
		detach(hero.belongings.backpack);
	}
		hero.spend(TIME_TO_DRINK);
		hero.busy();
		apply(hero);

		Sample.INSTANCE.play(Assets.SND_DRINK);

		hero.sprite.operate(hero.pos);
	}

	@Override
	protected void onThrow(int cell) {
		if (Dungeon.depth.map[cell] == Terrain.WELL || Floor.pit[cell] ) {

			super.onThrow(cell);

		} else {

			shatter(cell);

		}
	}

	public void apply(Hero hero) {
		shatter(hero.pos);
	}

	public void shatter(int cell) {
		if (Dungeon.visible[cell]) {
			GLog.i(Messages.get(Potion.class, "shatter", color()));
			Sample.INSTANCE.play(Assets.SND_SHATTER);
			splash(cell);
		}
	}

	@Override
	public void cast(final Hero user, int dst) {
		super.cast(user, dst);
	}

	public boolean isKnown() {
		return handler.isKnown(this);
	}

	public void setKnown() {
		
		if (!isKnown()) {
			handler.know(this);
		}
		Badges.validateAllPotionsIdentified();
		
	}
	
	public Integer initials(){
		return isKnown() ? initials : null;
	}

	protected String color() {
		return Messages.get(Potion.class, color);
	}	
	
	@Override
	public Item identify() {

		setKnown();
		return this;
	}

	@Override
	public String name() {
		return isKnown() ? super.name() : Messages.get(Potion.class, "unknown_name", color());
	}

	@Override
	public String info() {
		return isKnown() ?
			desc() :
			Messages.get(Potion.class, "unknown_desc", color());
	}

	@Override
	public boolean isIdentified() {
		return isKnown();
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	public static HashSet<Class<? extends Potion>> getKnown() {
		return handler.known();
	}

	public static HashSet<Class<? extends Potion>> getUnknown() {
		return handler.unknown();
	}

	public static boolean allKnown() {
		return handler.known().size() == potions.length;
	}

	protected void splash(int cell) {
		final int color = ItemSprite.pick(image, 8, 10);
		Splash.at(cell, color, 5);

		Fire fire = (Fire) Dungeon.depth.blobs.get(Fire.class);
		if (fire != null)
			fire.clear(cell);

		Char ch = Actor.findChar(cell);
		if (ch != null)
			Buff.detach(ch, Burning.class);
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
}
