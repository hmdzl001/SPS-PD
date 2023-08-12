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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SnowballSprite;
import com.watabou.utils.Callback;


public class IceBall extends Mob {
	{
		spriteClass = SnowballSprite.class;
		baseSpeed = 0.5f;

		HP = HT = 10;
		evadeSkill = 0;
		EXP = 1;
		maxLvl = 1;

		properties.add(Property.ELEMENT);
		properties.add(Property.MECH);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return false;
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
	}	
	
	@Override
	public int damageRoll() {
		return 100;
	}

	@Override
	public int hitSkill(Char target) {
		return 1000;
	}

	@Override
	public int drRoll() {
		return 0;
	}
	
	@Override
	public void add(Buff buff) {
	}

	@Override
	public void die(Object cause) {
		final int spos = pos;
		for (int n : Level.NEIGHBOURS4OUT) {
			final int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				Mob mob = Bestiary.mob(86);
				mob.pos = cell;
				mob.state = mob.HUNTING;
				GameScene.add(mob,1f);
				mob.sprite.jump(spos,cell, new Callback() {
					@Override
					public void call() {
						move(cell);
					}
				});
			}
		}



		   super.die(cause);

	}
	

}
