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

import java.util.HashSet;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.sprites.OrcSprite;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.watabou.utils.Random;

public class Orc extends Mob {

	{
		spriteClass = OrcSprite.class;
		state = SLEEPING;

		HP = HT = 200+(Dungeon.depth*10);
		defenseSkill = 18+(Math.round((Dungeon.depth)/2));

		EXP = 22;
		maxLvl = 40;
		
		properties.add(Property.ORC);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(50, 90);
	}

	@Override
	public int attackSkill(Char target) {
		return 35;
	}

	@Override
	protected float attackDelay() {
		return 1.5f;
	}

	@Override
	public int dr() {
		return 32;
	}
	
	@Override
	public void damage(int dmg, Object src) {	
		if (dmg > HT/8){
			GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
			}
		super.damage(dmg, src);
	}	

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(CorruptGas.class);
		IMMUNITIES.add(Vertigo.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
