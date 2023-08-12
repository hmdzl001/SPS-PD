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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.GoldOrcSprite;
import com.watabou.utils.Random;

public class GoldOrc extends Mob {

	{
		spriteClass = GoldOrcSprite.class;
		state = SLEEPING;

		HP = HT = 500;
		evadeSkill = 35;

		EXP = 25;

		properties.add(Property.ORC);
		properties.add(Property.DEMONIC);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(55, 115);
	}

	@Override
	public int hitSkill(Char target) {
		return 55;
	}

	@Override
	protected float attackDelay() {
		return 1.5f;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(16, 32);
	}

	@Override
	public void damage(int dmg, Object src) {
		if (dmg > HT/8){
			GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
			}
		super.damage(dmg, src);
	}		

	{
		immunities.add(Amok.class);
		immunities.add(Terror.class);
		immunities.add(CorruptGas.class);
		immunities.add(Vertigo.class);

	}

}
