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

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.KindOfArmor;
import com.hmdzl.spspd.change.items.armor.glyphs.AdaptGlyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Changeglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Crystalglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Darkglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Earthglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Electricityglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Fireglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Iceglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Lightglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.RecoilGlyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Revivalglyph;
import com.hmdzl.spspd.change.items.armor.glyphs.Testglyph;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.items.rings.RingOfEvasion;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.EquipableItem;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
 
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Armor extends KindOfArmor {

	private static final String TXT_TO_STRING = "%s :%d";

	public int tier;

    public int STR = 10;
	public float DEX = 1f; // dexterity modifier
	public float STE = 1f; // stealth modifier
	public int   ENG = 1;  // energy modifier

	public Glyph glyph;

	@Override
	public boolean doEquip(Hero hero) {

		detachAll(hero.belongings.backpack);
		if (hero.belongings.armor == null
				|| hero.belongings.armor.doUnequip(hero, true)) {

			hero.belongings.armor = this;
			activate(hero);
			((HeroSprite) hero.sprite).updateArmor();
			hero.spendAndNext(TIME_TO_EQUIP);
			return true;

	   } else {

		collect(hero.belongings.backpack);
		return false;
	   }

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
	public void  proc(Char attacker, Char defender, int damage) {

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);	
		}

	}	
	
	private static final String GLYPH = "glyph";

	public int STR()
	{
		if(Dungeon.hero != null && Dungeon.hero.heroClass == HeroClass.WARRIOR &&  Dungeon.hero.belongings.armor == this && STR > 2)
			return STR - 2;
		else return STR;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(GLYPH, glyph);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		//glyph = (Glyph) bundle.get(GLYPH);
		glyph = ((Glyph) bundle.get(GLYPH));
	}

	@Override
	public float dexterityFactor(Hero hero) {

		int encumbrance = STR() - hero.STR();

		float DEX = this.DEX;
		
		int bonus = 0;
		for (Buff buff : hero.buffs(RingOfEvasion.Evasion.class)) {
			bonus += ((RingOfEvasion.Evasion) buff).level;
		}
		
		 DEX+= (float) (Math.min(2,bonus/15));

		return encumbrance > 0 ? (float) (DEX / Math.pow(1.5, encumbrance)) : DEX;
	}	
	
	
	@Override
	public float stealthFactor(Hero hero) {

		int encumbrance = STR() - hero.STR();

		float STE = this.STE;

		return encumbrance > 0 ? (float) (STE / Math.pow(1.5, encumbrance)) : STE;
	}		
	
	
	@Override
	public int energyFactor(Hero hero) {
	    int ENG = this.ENG;
        return ENG;
	}

	/*public int tier() {
		int tier = this.tier();
		return tier;
	}*/

	@Override
	public int drRoll(Hero hero) {
		int encumbrance = STR() - hero.STR();
		int dr = super.drRoll(hero);
		return encumbrance > 0 ?  0 :Math.round(dr);
	}	
	

	public Item upgrade(boolean hasglyph) {
	
		if (hasglyph) {
		   if (glyph != null) {
				hasglyphAdv();
		   } else {
				hasglyph();
		   }			
			
		}

		return super.upgrade();
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
		
		return this;
	}

	public Armor hasglyph( Glyph gph ) {
		glyph = gph;
		return this;
	}

	public Armor hasglyph() {

		Class<? extends Glyph> oldGlyphClass = glyph != null ? glyph.getClass()
				: null;
		Glyph gph = Glyph.random();
		while (gph.getClass() == oldGlyphClass) {
			gph = Glyph.random();
		}

		return hasglyph(gph);
	}

	public Armor hasglyphAdv() {

		Class<? extends Glyph> oldGlyphClass = glyph != null ? glyph.getClass()
				: null;
		Glyph gph = Glyph.randomAdv();
		while (gph.getClass() == oldGlyphClass) {
			gph = Glyph.randomAdv();
		}

		return hasglyph(gph);
	}	
	
	public boolean isGlyphed() {
		return glyph != null;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return glyph != null ? glyph.glowing() : null;
	}

	public static abstract class Glyph implements Bundlable {

		private static final Class<?>[] glyphs = new Class<?>[] { Changeglyph.class,
				Crystalglyph.class, Darkglyph.class, Earthglyph.class,
				Electricityglyph.class, Fireglyph.class, Iceglyph.class,
				Lightglyph.class, Revivalglyph.class, Testglyph.class,
		        AdaptGlyph.class, RecoilGlyph.class};

		private static final float[] chances = new float[] { 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1,1,1 };

		private static final float[] chancesAdv = new float[] { 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1,1,1 };
		
		public abstract int proc(Armor armor, Char attacker, Char defender,
				int damage);

		public String name(String armorName) {
			return Messages.get(this, "name", armorName);
		}

		public String desc() {
			return Messages.get(this, "desc");
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
