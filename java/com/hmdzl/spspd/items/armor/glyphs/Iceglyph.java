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
package com.hmdzl.spspd.items.armor.glyphs;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphDark;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphEarth;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphElectricity;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphFire;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphIce;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphLight;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SnowParticle;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Iceglyph extends Glyph {

	private static ItemSprite.Glowing BLUE = new ItemSprite.Glowing(0x0000FF);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

	    GlyphDark gdark = defender.buff(GlyphDark.class);
		GlyphIce gice = defender.buff(GlyphIce.class);
	    GlyphLight glight = defender.buff(GlyphLight.class);
	    GlyphFire gfire = defender.buff(GlyphFire.class);
		GlyphEarth gearth = defender.buff(GlyphEarth.class);
		GlyphElectricity gelect = defender.buff(GlyphElectricity.class);	
		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);
	
		if (defender.isAlive() && gice == null)
		{
			Buff.detach(defender,GlyphDark.class);
			Buff.detach(defender,GlyphLight.class);
			Buff.detach(defender,GlyphFire.class);
			Buff.detach(defender,GlyphEarth.class);
			Buff.detach(defender,GlyphElectricity.class);
			Buff.affect(defender,GlyphIce.class);
		}	
	
		if (damage == 0) {
			return 0;
		}

		int level = Math.max(0, armor.level);
		
		if (Random.Int(level + 6) >= 5 || (fcb != null && Random.Int(level + 6) >= 3)) {
            Buff.prolong(attacker, Frost.class, Frost.duration(attacker)* Random.Float(1f, 1.5f));
			CellEmitter.get(attacker.pos).start(SnowParticle.FACTORY, 0.2f, 6);
		}

		if (Random.Int(level + 7) >= 6) {
			DeferedDamage debuff = defender.buff(DeferedDamage.class);
			if (debuff == null) {
				debuff = new DeferedDamage();
				debuff.attachTo(defender);
			}
			debuff.prolong(damage);

			defender.sprite.showStatus(CharSprite.WARNING, Messages.get(this, "deferred", damage));

			return 0;			

		} else {
			return damage;
		}
		
	}

	//private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	//private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	//static {
		//RESISTANCES.add( Frost.class );
		//RESISTANCES.add( Cold.class );
		//RESISTANCES.add( Chill.class );
		//IMMUNITIES.add(WeatherOfSnow.class );

		//REDUCE.add( DisintegrationTrap.class );
		//REDUCE.add( GrimTrap.class );

		//REDUCE.add( Shaman.class );
		//REDUCE.add( Warlock.class );
		//REDUCE.add( Eye.class );
		//REDUCE.add( Yog.BurningFist.class );
	//}


	@Override
	public Glowing glowing() {
		return BLUE;
	}

	public static class DeferedDamage extends Buff {

		protected int damage = 0;

		private static final String DAMAGE = "damage";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(DAMAGE, damage);

		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			damage = bundle.getInt(DAMAGE);
		}

		@Override
		public boolean attachTo(Char target) {
			if (super.attachTo(target)) {
				postpone(TICK);
				return true;
			} else {
				return false;
			}
		}

		public void prolong(int damage) {
			this.damage += damage;
		}

        @Override
		public int icon() {
			return BuffIndicator.DEFERRED;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}		
		
		@Override
		public boolean act() {
			if (target.isAlive()) {

				int damageThisTick = Math.max(1, (int)(damage*0.1f));
				target.damage( damageThisTick, this );
				if (target == Dungeon.hero && !target.isAlive()) {

					Glyph glyph = new Iceglyph();
					Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
					//GLog.n("%s killed you...", glyph.name());

					Badges.validateDeathFromGlyph();
				}
				spend(TICK);

				damage -= damageThisTick;
				if (--damage <= 0) {
					detach();
				}

			} else {

				detach();

			}

			return true;
		}
		@Override
		public String desc() {
			return Messages.get(this, "desc", damage);
		}		
		
	}
}
