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
package com.hmdzl.spspd.items.scrolls;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.ItemStatusHandler;
import com.hmdzl.spspd.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Scroll extends Item {

	public static final String AC_READ = "READ";

	protected static final float TIME_TO_READ = 1f;
	
	protected int initials;

	private static final Class<?>[] scrolls = { ScrollOfIdentify.class,
			ScrollOfMagicMapping.class, ScrollOfRecharging.class,
			ScrollOfRemoveCurse.class, ScrollOfTeleportation.class,
			ScrollOfUpgrade.class, ScrollOfRage.class, ScrollOfTerror.class,
			ScrollOfLullaby.class, ScrollOfMagicalInfusion.class,
			ScrollOfPsionicBlast.class, ScrollOfMirrorImage.class, ScrollOfRegrowth.class, ScrollOfDummy.class };
	private static final String[] runes = { "KAUNAN", "SOWILO", "LAGUZ",
			"YNGVI", "GYFU", "RAIDO", "ISAZ", "MANNAZ", "NAUDIZ", "BERKANAN",
			"NCOSRANE", "TIWAZ", "NENDIL", "LIBRA" };
	private static final Integer[] images = { ItemSpriteSheet.SCROLL_KAUNAN,
			ItemSpriteSheet.SCROLL_SOWILO, ItemSpriteSheet.SCROLL_LAGUZ,
			ItemSpriteSheet.SCROLL_YNGVI, ItemSpriteSheet.SCROLL_GYFU,
			ItemSpriteSheet.SCROLL_RAIDO, ItemSpriteSheet.SCROLL_ISAZ,
			ItemSpriteSheet.SCROLL_MANNAZ, ItemSpriteSheet.SCROLL_NAUDIZ,
			ItemSpriteSheet.SCROLL_BERKANAN, ItemSpriteSheet.SCROLL_NCOSRANE,
			ItemSpriteSheet.SCROLL_TIWAZ, ItemSpriteSheet.SCROLL_NENDIL, ItemSpriteSheet.SCROLL_LIBRA };

	private static ItemStatusHandler<Scroll> handler;

	private String rune;

	public boolean ownedByBook = false;

	{
		stackable = true;
		defaultAction = AC_READ;
	}

	@SuppressWarnings("unchecked")
	public static void initLabels() {
		handler = new ItemStatusHandler<Scroll>(
				(Class<? extends Scroll>[]) scrolls, runes, images);
	}

	public static void save(Bundle bundle) {
		handler.save(bundle);
	}

	@SuppressWarnings("unchecked")
	public static void restore(Bundle bundle) {
		handler = new ItemStatusHandler<Scroll>(
				(Class<? extends Scroll>[]) scrolls, runes, images, bundle);
	}

	public Scroll() {
		super();
		sync();
	}

	@Override
	public void sync() {
		super.sync();
		image = handler.image(this);
		rune = handler.label(this);
	}

    @Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_READ);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_READ)) {

			if (hero.buff(Blindness.class) != null ) {
				GLog.w(Messages.get(this, "blinded"));
			} else if (hero.buff(Locked.class) != null ) {
				GLog.w(Messages.get(this, "locked"));
			} else if (hero.buff(Silent.class) != null ) {
				GLog.w(Messages.get(this, "silent"));
			} else if (hero.buff(UnstableSpellbook.bookRecharge.class) != null
					&& hero.buff(UnstableSpellbook.bookRecharge.class)
							.isCursed()
					&& !(this instanceof ScrollOfRemoveCurse)) {
				GLog.n(Messages.get(this, "cursed") );
			}  else {
				curUser = hero;
				if (!(Dungeon.hero.heroClass == HeroClass.FOLLOWER ) ||
				(Dungeon.hero.heroClass == HeroClass.FOLLOWER && Random.Int(10)>=1 ))
				curItem = detach(hero.belongings.backpack);
				doRead();
				//readAnimation();
			}

		} else {

			super.execute(hero, action);

		}
	}

	public abstract void doRead();
	
	//currently only used in scrolls owned by the unstable spellbook
	public abstract void empoweredRead();
	
	protected void readAnimation() {
		//curUser.spend( TIME_TO_READ );
		curUser.busy();
		((HeroSprite)curUser.sprite).read();
		if (Dungeon.isChallenged(Challenges.ITEM_PHOBIA)){
			Buff.affect(curUser, Silent.class, 5f);
			int damage = curUser.HT/10;
			curUser.damage(damage, this);
		}
	}
	
	public boolean isKnown() {
		return handler.isKnown(this);
	}

	public void setKnown() {
		if (!isKnown() && !ownedByBook) {
			handler.know(this);
		}

		Badges.validateAllScrollsIdentified();
	}

	@Override
	public Item identify() {
		setKnown();
		return super.identify();
	}
	
	public String rune() {
		return Messages.get(Scroll.class, rune);
	}	

	@Override
	public String name() {
		return isKnown() ? name : Messages.get(this, "unknown_name", rune());
	}

	@Override
	public String info() {
		return isKnown() ?
			desc() :
			Messages.get(this, "unknown_desc", rune());
	}

	public Integer initials(){
		return isKnown() ? initials : null;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return isKnown();
	}

	public static HashSet<Class<? extends Scroll>> getKnown() {
		return handler.known();
	}

	public static HashSet<Class<? extends Scroll>> getUnknown() {
		return handler.unknown();
	}

	public static boolean allKnown() {
		return handler.known().size() == scrolls.length;
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
}
