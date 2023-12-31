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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.StenchGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.GreyRatSprite;
import com.watabou.utils.Random;

public class GreyRat extends Mob {
	

	private static final float SPAWN_DELAY = 2f;

	{
		spriteClass = GreyRatSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(3, 5));
		evadeSkill = 8+adj(0);
		
		EXP = 5;	
		
		loot = new Meat();
		lootChance = 0.5f;
		
		lootOther = Generator.Category.MUSHROOM;
		lootChanceOther = 0.25f;

		properties.add(Property.BEAST);
		
	}


	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1, 7+adj(0));
	}

	@Override
	protected float attackDelay() {
		return 0.8f;
	}


	@Override
	public int hitSkill(Char target) {
		return 6 + Dungeon.dungeondepth;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 3);
	}
	
	public static void spawnAround(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}
	
	public static GreyRat spawnAt(int pos) {
		
        GreyRat b = new GreyRat();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	
	{
		resistances.add(ToxicGas.class);
		//resistances.add(EnchantmentDark.class);

		immunities.add(Amok.class);
		immunities.add(Sleep.class);
		immunities.add(Terror.class);
		immunities.add(Burning.class);
		immunities.add(ScrollOfPsionicBlast.class);
		immunities.add(Vertigo.class);
		immunities.add(Poison.class);
		immunities.add(StenchGas.class);
	}


	
}
