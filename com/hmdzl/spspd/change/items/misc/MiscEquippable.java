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
package com.hmdzl.spspd.change.items.misc;

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
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class MiscEquippable extends KindofMisc {


	private static final float TIME_TO_EQUIP = 1f;
	
	public String cursedDesc(){
		return "your " + this  + " is cursed";		
	}

	protected Buff buff;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
		return actions;
	}

	@Override
	public boolean doEquip(Hero hero) {

		if (hero.belongings.misc1 != null && hero.belongings.misc2 != null && hero.belongings.misc3 != null) {

			GLog.w("you can only wear 3 misc items at a time");
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
				GLog.n(cursedDesc());
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
			} else if (hero.belongings.misc2 == this){
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



	@Override
	public String name() {
		return name ;
	}

	@Override
	public String desc() {
		return "This is a misc item ";
	}

	@Override
	public String info() {
		if (isEquipped(Dungeon.hero)) {

			return desc();
				

		} else if (cursed && cursedKnown) {

			return desc();
					
		} else {

			return desc() ;

		}
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

	protected MiscBuff buff() {
		return null;
	}

	

	public class MiscBuff extends Buff {

		public int level;

		public MiscBuff() {
			level = MiscEquippable.this.level;
		}

		@Override
		public boolean attachTo(Char target) {
		
			return super.attachTo(target);
		}

		@Override
		public boolean act() {		

			spend(TICK);

			return true;
		}
	}
}
