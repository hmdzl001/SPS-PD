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

import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.GraveProtectorSprite;
import com.watabou.utils.Random;

public class GraveProtector extends Mob{
	{
		spriteClass = GraveProtectorSprite.class;

		EXP = 1;
		state = HUNTING;
		//flying = true;
		
		HP = HT = 350;
		evadeSkill = 15;
		
		loot = new VioletDewdrop();
		lootChance = 1f;
		
		properties.add(Property.TROLL);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(8+Math.round(Statistics.skeletonsKilled/10), 15+Math.round(Statistics.skeletonsKilled/5));
	}

	@Override
	public int hitSkill(Char target) {
		return 20;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 8);
	}

	@Override
	protected boolean canAttack(Char enemy) {		if (buff(Locked.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Level.adjacent(pos, enemy.pos)) {
			damage = damage / 2;
		} else {
			Buff.prolong(enemy,Slow.class,5f);
			Buff.affect(enemy,ArmorBreak.class,5f).level(20);
		}

		return damage;
	}

	{
		weakness.add(Blindness.class);
	}


}
