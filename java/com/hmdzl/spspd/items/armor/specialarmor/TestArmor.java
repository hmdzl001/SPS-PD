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
package com.hmdzl.spspd.items.armor.specialarmor;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class TestArmor extends NormalArmor {
	public Buff passiveBuff;
	{
		//name = "life armor";
		image = ItemSpriteSheet.TEST_ARMOR;
		MAX = 0;
		MIN = 0;
	}

	public TestArmor() {
		super(1,1,1,1);
	}

	public int type = 0;
	private static final String TYPE = "type";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TYPE, type);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		type = bundle.getInt(TYPE);
	}

	protected TestBuff passiveBuff() {
		return new TestCharge();
	}
	public class TestBuff extends Buff {
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
	
	public class TestCharge extends TestBuff {
		@Override
		public boolean act() {
			spend(TICK);
			return true;
		}
		@Override
		public String toString() {
			return "TestCharge";
		}
		@Override
		public void detach() {
			super.detach();
		}
	}	
	
	@Override
	public Item upgrade(boolean hasglyph) {
        MIN-=1;
        MAX-=3;

		return super.upgrade(hasglyph);
	}


	@Override
	public void proc(Char attacker, Char defender, int damage) {

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}
    }
}
