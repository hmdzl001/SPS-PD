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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.damageblobs.IceEffectDamage;
import com.hmdzl.spspd.actors.blobs.effectblobs.ElectriShock;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.EnergyParticle;
import com.hmdzl.spspd.effects.particles.RainParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.RedDewdrop;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.hmdzl.spspd.sprites.LivePhotoSprite;
import com.hmdzl.spspd.sprites.ThiefSprite;
import com.watabou.utils.Random;

public class GhostPhoto extends Mob {

	public Item item;

	{
		spriteClass = LivePhotoSprite.class;

		HP = HT = 100+(adj(0)*Random.NormalIntRange(3, 5));
		evadeSkill = 5+adj(0);

		flying = true;

		EXP = 3;
        maxLvl = 20;
		
		loot =  new RedDewdrop();
		lootChance = 0.1f;
		
		properties.add(Property.UNKNOW);
	}

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.SEED);
	}

	@Override
	public int damageRoll() {
		return 0;
	}

	@Override
	protected float attackDelay() {
		return 0.5f;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		for (int i : Floor.NEIGHBOURS9) {
			if (Floor.insideMap(pos+i) && !Floor.solid[pos+i]) {
				GameScene.add(Blob.seed(pos + i, 8, IceEffectDamage.class));
			}
		}
	}

	@Override
	protected Item createLoot() {
			return super.createLoot();
	}

	@Override
	public int hitSkill(Char target) {
		return 25;
	}

	@Override
	public int drRoll() {
		return 0;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		Buff.affect(enemy, Wet.class,10f);
		return damage;
	}

}
