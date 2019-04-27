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

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.TarGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Tar;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.GolemSprite;

import com.watabou.utils.Random;

public class Golem extends Mob {

	{
		spriteClass = GolemSprite.class;

		HP = HT = 180+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 18+adj(1);

		EXP = 12;
		maxLvl = 22;
		
		loot = new StoneOre();
		lootChance = 0.5f;
		
		properties.add(Property.MECH);
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
		return 1.5f;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 15);
	}

	@Override
	public void damage(int dmg, Object src) {	
		if (dmg > HT/8){
			GameScene.add(Blob.seed(pos, 30, TarGas.class));
			}
		super.damage(dmg, src);
	}		
	
	@Override
	public void die(Object cause) {
		
		Imp.Quest.process(this);
		if (Dungeon.limitedDrops.nornstones.count<6 
				&& Random.Int(6)<3
				){
			Dungeon.level.drop(Generator.random(Generator.Category.NORNSTONE), pos).sprite.drop();
			Dungeon.limitedDrops.nornstones.count++;
		}		
		super.die(cause);
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
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(TarGas.class);
		IMMUNITIES.add(Tar.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
