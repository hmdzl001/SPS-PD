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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.EnergyParticle;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.food.meatfood.Meat;
import com.hmdzl.spspd.change.items.wands.WandOfLightning;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.MrDestructoSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class PatrolUAV extends Mob {
	

	{
		spriteClass = MrDestructoSprite.class;

		HP = HT = 50+(Dungeon.depth*Random.NormalIntRange(1, 3));
		evadeSkill = adj(1);
		
		EXP = 5;
		maxLvl = 10;
		
		state = WANDERING;
		
		flying = true;
		
		loot = new StoneOre();
		lootChance = 0.4f;
		
		properties.add(Property.MECH);
	}


	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(4, 7);
	}

	@Override
	public int hitSkill(Char target) {
		return 5;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}
	
	@Override
	public void die(Object cause) {

		super.die(cause);
		for (int i : Level.NEIGHBOURS9) {
			if (Level.insideMap(pos+i) && !Level.solid[pos+i]) {
				GameScene.add(Blob.seed(pos + i, 3, ElectriShock.class));
				CellEmitter.get(pos + i).burst(EnergyParticle.FACTORY, 5);
			}
		}

	}

	
		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Burning.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ElectriShock.class);
			IMMUNITIES.add(WandOfLightning.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	
}
