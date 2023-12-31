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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.SandStorm;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.AlbinoSprite;
import com.watabou.utils.Random;

public class Albino extends Rat {

	{
		spriteClass = AlbinoSprite.class;

		HP = HT = 10+(Dungeon.dungeondepth *Random.NormalIntRange(1, 3));

		loot = new Meat();
		lootChance = 1f;
		
		properties.add(Property.BEAST);
		properties.add(Property.DEMONIC);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Badges.validateRare(this);
	}

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.HIGHFOOD);
	}

	@Override
	public boolean act() {
		for (int i = 0; i < Floor.NEIGHBOURS9.length; i++) {
			GameScene.add(Blob.seed(pos + Floor.NEIGHBOURS9[i], 2, SandStorm.class));
		}
		return super.act();
	}

	@Override
	public void damage(int dmg, Object src) {
		GameScene.add(Blob.seed(pos, 15, CorruptGas.class));
		super.damage(dmg, src);
	}


	{
		resistances.add(Wand.class);
		immunities.add(Amok.class);
		immunities.add(Terror.class);
		immunities.add(CorruptGas.class);
		immunities.add(Vertigo.class);
		immunities.add(SandStorm.class);
	}
}
