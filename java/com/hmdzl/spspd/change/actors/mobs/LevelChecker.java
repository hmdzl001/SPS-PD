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
package com.hmdzl.spspd.change.actors.mobs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Shocked;
import com.hmdzl.spspd.change.actors.buffs.Taunt;
import com.hmdzl.spspd.change.items.food.meatfood.Meat;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.RatSprite;
import com.watabou.utils.Random;

public class LevelChecker extends Mob {

	{
		spriteClass = RatSprite.class;

		HP = HT = Dungeon.hero.lvl*1000;
		evadeSkill = Dungeon.hero.lvl*1000;
		baseSpeed = 3f;
		flying = true;

		state = WANDERING;
		
		properties.add(Property.BOSS);
	}

	@Override
	protected boolean getCloser(int target) {
		return super.getCloser(Dungeon.hero.pos);
	}

	@Override
	public int damageRoll() {
		return Dungeon.hero.lvl*1000;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (enemy.buff(Taunt.class)== null && enemy == Dungeon.hero) {
			Buff.affect(enemy, Taunt.class);
			Dungeon.hero.exp=0;
			Dungeon.hero.lvl=1;
			
			//Dungeon.hero.HT=30;
			//Dungeon.hero.hitSkill=10;
			//Dungeon.hero.evadeSkill=5;
			damage = 0;
			this.damage(this.HT*2,this);
			return damage;
		} else return damage;
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.lvl*1000;
	}

	@Override
	public int drRoll() {
		return Dungeon.hero.lvl*1000;
	}	
	
	@Override
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}	
}
