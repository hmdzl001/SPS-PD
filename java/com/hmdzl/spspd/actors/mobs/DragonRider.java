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
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.eggs.RandomEgg;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.DragonRiderSprite;
import com.watabou.utils.Random;


public class DragonRider extends Mob {
	{
		spriteClass = DragonRiderSprite.class;
		baseSpeed = 1.5f;

		HP = HT = 140+(adj(0)*Random.NormalIntRange(7, 5));
		evadeSkill = 30+adj(1);
		EXP = 14;
		maxLvl = 30;

		properties.add(Property.DRAGON);
	}

	@Override
	public Item SupercreateLoot(){
		return new RandomEgg();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(40, 50);
	}

	@Override
	public int hitSkill(Char target) {
		return 36+adj(1);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(20, 40);
	}

	@Override
	public void die(Object cause) {

			Mob mob = Bestiary.mob(84);
			mob.pos = pos;
			mob.state = mob.HUNTING;
		    GameScene.add(mob,1f);
		super.die(cause);

	}
	
	
	@Override
	protected boolean doAttack(Char enemy) {

		return super.doAttack(enemy);

	}

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		
	}
}
