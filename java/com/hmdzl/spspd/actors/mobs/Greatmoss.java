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
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.plants.Sungrass;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.GreatMossSprite;
import com.watabou.utils.Random;

public class Greatmoss extends Mob {

	{
		spriteClass = GreatMossSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(5, 7));
		evadeSkill = adj(0);

		EXP = 11;
		maxLvl = 30;

		baseSpeed = 0.5f;

		loot = Generator.Category.MUSHROOM;
		lootChance = 0.3f;

		state = PASSIVE;
		properties.add(Property.PLANT);
	}

	@Override
	public Item SupercreateLoot(){
		return new Sungrass.Seed();
	}

	@Override
	public boolean act() {
        //GameScene.add(Blob.seed(pos, 30, TarGas.class));
		return super.act();
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20+adj(0), 60+adj(1));
	}

	@Override
	public int hitSkill(Char target) {
		return 28+adj(1);
	}

	@Override
	protected float attackDelay() {
		return 2f;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(20, 25);
	}

	@Override
	public void damage(int dmg, Object src, int type) {

		if (state == PASSIVE) {
			state = HUNTING;
		}

		super.damage(dmg, src,type);
	}


	@Override
	public int defenseProc(Char enemy, int damage) {

        switch (Random.Int (4)) {
				case 0:
					GameScene.add(Blob.seed(pos, 25, ToxicGas.class));
					break;
				case 1:
					GameScene.add(Blob.seed(pos, 25, ConfusionGas.class));
					break;
				case 2:
					GameScene.add(Blob.seed(pos, 25, ParalyticGas.class));
					break;
				case 3:
					GameScene.add(Blob.seed(pos, 25, DarkGas.class));
					break;
				default:
					break;
		}

		return super.defenseProc(enemy, damage);
	}

	@Override
	public void beckon(int cell) {
		// Do nothing
	}


	@Override
	public void die(Object cause) {
	
		super.die(cause);
	}


	{
		immunities.add(Amok.class);
		immunities.add(Terror.class);
		immunities.add(Sleep.class);
		immunities.add(TarGas.class);
		immunities.add(Tar.class);

	}


}
