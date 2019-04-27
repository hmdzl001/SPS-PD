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
package com.hmdzl.spspd.change.items.rings;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.ItemStatusHandler;
import com.hmdzl.spspd.change.items.KindofMisc;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Ring extends KindofMisc {

	private static final int TICKS_TO_KNOW = 200;

	private static final float TIME_TO_EQUIP = 1f;

	protected Buff buff;
	
    protected Integer initials;

	private static final Class<?>[] rings = { RingOfAccuracy.class,
			RingOfEvasion.class, RingOfElements.class, RingOfForce.class,
			RingOfFuror.class, RingOfHaste.class, RingOfMagic.class,
			RingOfMight.class, RingOfSharpshooting.class, RingOfTenacity.class,
			RingOfEnergy.class, };
	private static final String[] gems = { "diamond", "opal", "garnet", "ruby",
			"amethyst", "topaz", "onyx", "tourmaline", "emerald", "sapphire",
			"quartz", "agate" };
	private static final Integer[] images = { ItemSpriteSheet.RING_DIAMOND,
			ItemSpriteSheet.RING_OPAL, ItemSpriteSheet.RING_GARNET,
			ItemSpriteSheet.RING_RUBY, ItemSpriteSheet.RING_AMETHYST,
			ItemSpriteSheet.RING_TOPAZ, ItemSpriteSheet.RING_ONYX,
			ItemSpriteSheet.RING_TOURMALINE, ItemSpriteSheet.RING_EMERALD,
			ItemSpriteSheet.RING_SAPPHIRE, ItemSpriteSheet.RING_QUARTZ,
			ItemSpriteSheet.RING_AGATE };

	private static ItemStatusHandler<Ring> handler;

	private String gem;

	private int ticksToKnow = TICKS_TO_KNOW;

	@SuppressWarnings("unchecked")
	public static void initGems() {
		handler = new ItemStatusHandler<Ring>((Class<? extends Ring>[]) rings,
				gems, images);
	}

	public static void save(Bundle bundle) {
		handler.save(bundle);
	}

	@SuppressWarnings("unchecked")
	public static void restore(Bundle bundle) {
		handler = new ItemStatusHandler<Ring>((Class<? extends Ring>[]) rings,
				gems, images, bundle);
	}

	public Ring() {
		super();
		sync();
	}

	@Override
	public void sync() {
		super.sync();
		image = handler.image(this);
		gem = handler.label(this);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
		return actions;
	}

	@Override
	public boolean doEquip(Hero hero) {

		if (hero.belongings.misc1 != null && hero.belongings.misc2 != null && hero.belongings.misc3 != null) {

			GLog.w(Messages.get(Ring.class, "toomany"));
			return false;

		} else {

			if (hero.belongings.misc1 == null) {
				hero.belongings.misc1 = this;
			} else if (hero.belongings.misc2 == null){
				hero.belongings.misc2 = this;
			} else {
				hero.belongings.misc3 = this;
			}

			detach(hero.belongings.backpack);

			activate(hero);

			cursedKnown = true;
			if (cursed) {
				equipCursed(hero);
				GLog.n(Messages.get(this, "cursed", this));
			}

			hero.spendAndNext(TIME_TO_EQUIP);
			return true;

		}

	}

	@Override
	public void activate(Char ch) {
		buff = buff();
		buff.attachTo(ch);
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {

			if (hero.belongings.misc1 == this) {
				hero.belongings.misc1 = null;
			} else if (hero.belongings.misc2 == this) {
				hero.belongings.misc2 = null;
			} else {
				hero.belongings.misc3 = null;
			}

			hero.remove(buff);
			buff = null;

			return true;

		} else {

			return false;

		}
	}

	@Override
	public boolean isEquipped(Hero hero) {
		return hero.belongings.misc1 == this || hero.belongings.misc2 == this || hero.belongings.misc3 == this ;
	}

	@Override
	public Item upgrade() {

		super.upgrade();

		if (buff != null) {

			Char owner = buff.target;
			buff.detach();
			if ((buff = buff()) != null) {
				buff.attachTo(owner);
			}
		}

		return this;
	}

	public boolean isKnown() {
		return handler.isKnown(this);
	}

	protected void setKnown() {
		if (!isKnown()) {
			handler.know(this);
		}

		Badges.validateAllRingsIdentified();
	}

	public String gem() {
		return Messages.get(Ring.class, gem);
	}	
	
	@Override
	public String name() {
		return isKnown() ? super.name() : Messages.get(this, "unknown_name", gem());
	}

	@Override
	public String info() {

		String desc = isKnown()? desc() : Messages.get(this, "unknown_desc", gem());

		if (cursed && isEquipped( Dungeon.hero )) {
			
			desc += "\n\n" + Messages.get(Ring.class, "cursed_worn");
			
		} else if (cursed && cursedKnown) {

			desc += "\n\n" + Messages.get(Ring.class, "curse_known", name());

		} 
		
		if (reinforced) {
			desc += "\n\n" + Messages.get(Item.class, "reinforced");
		}

		if (isKnown()) {
			desc += "\n\n" + statsInfo();
		}		
		
		return desc;
	}

	public Integer initials(){
		return isKnown() ? initials : null;
	}	
	
	protected String statsInfo(){
		return "";
	}	
	
	@Override
	public boolean isIdentified() {
		return super.isIdentified() && isKnown();
	}

	@Override
	public Item identify() {
		setKnown();
		return super.identify();
	}

	@Override
	public Item random() {
		if (Random.Float() < 0.3f) {
			level = -Random.Int(1, 3);
			cursed = true;
		} else
			level = Random.Int(1, 2);
		return this;
	}

	public static boolean allKnown() {
		return handler.known().size() == rings.length - 2;
	}

	@Override
	public int price() {
		int price = 75;
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level > 0) {
				price *= (level + 1);
			} else if (level < 0) {
				price /= (1 - level);
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	protected RingBuff buff() {
		return null;
	}

	private static final String UNFAMILIRIARITY = "unfamiliarity";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(UNFAMILIRIARITY, ticksToKnow);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if ((ticksToKnow = bundle.getInt(UNFAMILIRIARITY)) == 0) {
			ticksToKnow = TICKS_TO_KNOW;
		}
	}

	public static int getBonus(Char target, Class<?extends RingBuff> type){
		int bonus = 0;
		for (RingBuff buff : target.buffs(type)) {
			bonus += buff.level;
		}
		return bonus;
	}

	public class RingBuff extends Buff {

		//private static final String TXT_KNOWN = "This is a %s";

		public int level;

		public RingBuff() {
			level = Ring.this.level;
		}

		@Override
		public boolean attachTo(Char target) {

			/*if (target instanceof Hero
					&& ((Hero) target).heroClass == HeroClass.ROGUE
					&& !isKnown()) {
				setKnown();
				GLog.i(Messages.get(Ring.class, "known", name()));
				Badges.validateItemLevelAquired(Ring.this);
			}*/

			return super.attachTo(target);
		}

		@Override
		public boolean act() {

			//if (!isIdentified() && --ticksToKnow <= 0) {
				//String gemName = name();
				//identify();
				//GLog.w(Messages.get(Ring.class, "identify", gemName, Ring.this.toString()));
				//Badges.validateItemLevelAquired(Ring.this);
			//}

			spend(TICK);

			return true;
		}
	}
}
