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
package com.hmdzl.spspd.change.items.armor;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.EquipableItem;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.armor.glyphs.Affection;
import com.hmdzl.spspd.change.items.armor.glyphs.AntiEntropy;
import com.hmdzl.spspd.change.items.armor.glyphs.Bounce;
import com.hmdzl.spspd.change.items.armor.glyphs.Displacement;
import com.hmdzl.spspd.change.items.armor.glyphs.Entanglement;
import com.hmdzl.spspd.change.items.armor.glyphs.Metabolism;
import com.hmdzl.spspd.change.items.armor.glyphs.Multiplicity;
import com.hmdzl.spspd.change.items.armor.glyphs.Potential;
import com.hmdzl.spspd.change.items.armor.glyphs.Stench;
import com.hmdzl.spspd.change.items.armor.glyphs.Viscosity;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
 
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Armor extends EquipableItem {

	private static final int HITS_TO_KNOW = 15;
	
	private static final float TIME_TO_EQUIP = 1f;

	private static final String TXT_TO_STRING = "%s :%d";

	public int tier;

	public int STR;
	public int DR;

	private int hitsToKnow = HITS_TO_KNOW;

	public Glyph glyph;

	public Armor(int tier) {

		this.tier = tier;

		STR = typicalSTR();
		DR = typicalDR();
	}

	private static final String UNFAMILIRIARITY = "unfamiliarity";
	private static final String GLYPH = "glyph";

	public int STR()
	{
		if(Dungeon.hero != null && Dungeon.hero.heroClass == HeroClass.WARRIOR &&  Dungeon.hero.belongings.armor == this && STR > 2)
			return STR - 2;
		return STR;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(UNFAMILIRIARITY, hitsToKnow);
		bundle.put(GLYPH, glyph);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		glyph = (Glyph) bundle.get(GLYPH);
		if ((hitsToKnow = bundle.getInt(UNFAMILIRIARITY)) == 0) {
			hitsToKnow = HITS_TO_KNOW;
		}
		inscribe((Glyph) bundle.get(GLYPH));
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
		return actions;
	}

	@Override
	public boolean doEquip(Hero hero) {

		detach(hero.belongings.backpack);

		if (hero.belongings.armor == null
				|| hero.belongings.armor.doUnequip(hero, true, false)) {

			hero.belongings.armor = this;

			cursedKnown = true;
			if (cursed) {
				equipCursed(hero);
				GLog.n(Messages.get(Armor.class, "equip_cursed", toString()));
			}

			((HeroSprite) hero.sprite).updateArmor();

			hero.spendAndNext(2 * TIME_TO_EQUIP);
			return true;

		} else {

			collect(hero.belongings.backpack);
			return false;

		}
	}

	@Override
	protected float time2equip(Hero hero) {
		return hero.speed();
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {

			hero.belongings.armor = null;
			((HeroSprite) hero.sprite).updateArmor();

			return true;

		} else {

			return false;

		}
	}

	@Override
	public boolean isEquipped(Hero hero) {
		return hero.belongings.armor == this;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	public Item upgrade(boolean inscribe) {
	
		if (inscribe) {
			inscribe(Glyph.random());
		}
	    
		DR += 3;


		return super.upgrade();
	}

	@Override
	public Item degrade() {
		DR -= 1;
		return super.degrade();
	}

	public int proc(Char attacker, Char defender, int damage) {

		if (glyph != null) {
			damage = glyph.proc(this, attacker, defender, damage);
		}

		if (!levelKnown) {
			if (--hitsToKnow <= 0) {
				levelKnown = true;
				GLog.w(Messages.get(Armor.class, "identify", name(), toString()));
				Badges.validateItemLevelAquired(this);
			}
		}

		return damage;
	}

	@Override
	public String toString() {
		return levelKnown ? Messages.format(TXT_TO_STRING, super.toString(), STR())
				: super.toString();
	}

	@Override
	public String name() {
		return glyph == null ? super.name() : glyph.name(super.name());
	}

	@Override
	public String info() {
        String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(Armor.class, "curr_absorb", Math.max( DR, 0 ));

			if (STR > Dungeon.hero.STR()) {
				info += "\n\n" + Messages.get(Armor.class, "too_heavy");
			}
		} else {
			info += "\n\n" + Messages.get(Armor.class, "avg_absorb", typicalDR(), typicalSTR());
			if (typicalSTR() > Dungeon.hero.STR()) {
				info += "\n\n" + Messages.get(Armor.class, "probably_too_heavy");
			}
		}

		if (glyph != null) {
			info += "\n\n" +  Messages.get(Armor.class, "inscribed", glyph.name());
		}
		
		if (reinforced) {
			info += "\n\n" +  Messages.get(Armor.class, "reinforced");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Armor.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Armor.class, "cursed");
		}

		return info;
	}

	@Override
	public Item random() {
		if (Random.Float() < 0.4) {
			int n = 1;
			if (Random.Int(3) == 0) {
				n++;
				if (Random.Int(3) == 0) {
					n++;
				}
			}
			if (Random.Int(2) == 0) {
				upgrade(n);
			} else {
				degrade(n);
				cursed = true;
			}
		}

		if (Random.Int(10) == 0) {
			inscribe();
		}

		return this;
	}

	public int typicalSTR() {
		return 7 + tier * 2;
	}

	public int typicalDR() {
		return (tier * 10 - 5);
	}

	@Override
	public int price() {
		int price = 10 * (1 << (tier - 1));
		if (glyph != null) {
			price *= 1.5;
		}
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

	public Armor inscribe(Glyph glyph) {

		if (glyph != null && this.glyph == null) {
			DR += tier;
		} else if (glyph == null && this.glyph != null) {
			DR -= tier;
		}

		this.glyph = glyph;

		return this;
	}

	public Armor inscribe() {

		Class<? extends Glyph> oldGlyphClass = glyph != null ? glyph.getClass()
				: null;
		Glyph gl = Glyph.random();
		while (gl.getClass() == oldGlyphClass) {
			gl = Armor.Glyph.random();
		}

		return inscribe(gl);
	}

	public boolean isInscribed() {
		return glyph != null;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return glyph != null ? glyph.glowing() : null;
	}

	public static abstract class Glyph implements Bundlable {

		private static final Class<?>[] glyphs = new Class<?>[] { Bounce.class,
				Affection.class, AntiEntropy.class, Multiplicity.class,
				Potential.class, Metabolism.class, Stench.class,
				Viscosity.class, Displacement.class, Entanglement.class };

		private static final float[] chances = new float[] { 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1 };

		private static final float[] chancesAdv = new float[] { 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1 };
		
		public abstract int proc(Armor armor, Char attacker, Char defender,
				int damage);

		public String name() {
			return name(Messages.get(this, "glyph") );
		}

		public String name(String armorName) {
			return Messages.get(this, "name", armorName);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
		}

		@Override
		public void storeInBundle(Bundle bundle) {
		}

		public ItemSprite.Glowing glowing() {
			return ItemSprite.Glowing.WHITE;
		}

		public boolean checkOwner(Char owner) {
			if (!owner.isAlive() && owner instanceof Hero) {

				Badges.validateDeathFromGlyph();
				return true;

			} else {
				return false;
			}
		}

		@SuppressWarnings("unchecked")
		public static Glyph random() {
			try {
				return ((Class<Glyph>) glyphs[Random.chances(chances)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Glyph randomAdv() {
			try {
				return ((Class<Glyph>) glyphs[Random.chances(chancesAdv)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}

	}
}
