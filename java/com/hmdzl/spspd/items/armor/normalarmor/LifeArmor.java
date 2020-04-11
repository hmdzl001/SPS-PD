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
package com.hmdzl.spspd.items.armor.normalarmor;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class LifeArmor extends NormalArmor {
 
    public Buff passiveBuff;
	{
		//name = "life armor";
		image = ItemSpriteSheet.LIVE_ARMOR;
		MAX = 0;
		MIN = 0;
	}

	public LifeArmor() {
		super(1,2,5,3);
	}

	public int charge = 0;
	public int time = 0;
	private static final String CHARGE = "charge";	
	private static final String TIME = "time";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
		bundle.put(TIME, time);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		time = bundle.getInt(TIME);
	}

	protected LifeBuff passiveBuff() {
		return new LifeCharge();
	}
	public class LifeBuff extends Buff {
		public int level() {
			return level;
		}
		public boolean isCursed() {
			return cursed;
		}
	}
	@Override
	public boolean doEquip(Hero hero) {
		activate(hero);
		return super.doEquip(hero);
	}
	@Override
	public void activate(Hero hero) {
		passiveBuff = passiveBuff();
		passiveBuff.attachTo(hero);
	}	
	
	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {
			if (passiveBuff != null){
				passiveBuff.detach();
				passiveBuff = null;
			}
			hero.belongings.armor = null;
			return true;
		} else {
			return false;
		}
	}
	
	public class LifeCharge extends LifeBuff {
		@Override
		public boolean act() {
			if (time > 1) {
				time--;
			} else {
				Dungeon.hero.HP += charge;
				charge = 0;
			}
			
			if (charge>=LifeArmor.this.MAX){
				LifeArmor.this.MAX = charge;
			} else LifeArmor.this.MAX = 0;
			spend(TICK);
			return true;
		}
		@Override
		public String toString() {
			return "LifeCharge";
		}
		@Override
		public void detach() {
			charge = 0;
			time = 0;
			super.detach();
		}
	}	
	
	@Override
	public Item upgrade(boolean hasglyph) {

        MAX-=2;

		return super.upgrade(hasglyph);
	}


	@Override
	public void proc(Char attacker, Char defender, int damage) {
  
        charge += damage;
	    time = 20;
	
		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}	;
	};
}
