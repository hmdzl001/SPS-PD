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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.items.artifacts.ChaliceOfBlood;

public class Regeneration extends Buff {

	private static final float REGENERATION_DELAY = 25;

	@Override
	public boolean act() {
		if (target.isAlive()) {
  			if (!((Hero) target).isStarving()){
				if (Dungeon.hero.subClass == HeroSubClass.PASTOR && target.HP < target.HT*1.5){
				    target.HP += 2*Math.max(1,(int)(Dungeon.hero.lvl/5));
				} else if (target.HP < target.HT) {
					target.HP += Math.min((target.HT - target.HP),Math.max(1,(int)(Dungeon.hero.lvl/5)));
				}
			}

			ChaliceOfBlood.chaliceRegen regenBuff = Dungeon.hero
					.buff(ChaliceOfBlood.chaliceRegen.class);

			if (regenBuff != null) {
				if (regenBuff.isCursed()) {
					spend(REGENERATION_DELAY * 2f);
				} else {
					spend(Math.max(REGENERATION_DELAY - 2*regenBuff.level(), 5f));
				}
			} else if (Dungeon.hero.heroClass== HeroClass.PERFORMER && Dungeon.skins == 2) {
				spend(REGENERATION_DELAY*0.6f);
			} else	{ spend(REGENERATION_DELAY);}

		} else {

			diactivate();

		}

		return true;
	}
}
