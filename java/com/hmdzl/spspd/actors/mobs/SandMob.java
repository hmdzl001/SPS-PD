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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dry;
import com.hmdzl.spspd.actors.buffs.HiddenShadow;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.Taunt;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.WaterItem;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SandmobSprite;
import com.watabou.utils.Random;

public class SandMob extends Mob {

	{
		spriteClass = SandmobSprite.class;

		HP = HT = 90+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 5+adj(0);
		baseSpeed = 0.5f;

		EXP = 10;
		maxLvl = 25;

		loot = new WaterItem();
		//loot = new PotionOfMending(); potential nerf
		lootChance = 0.5f; // by default, see die()
		
		properties.add(Property.ELEMENT);
	}

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.GOLD);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(17, 25+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 10+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 10);
	}


	@Override
	public int attackProc(Char enemy, int damage) {
		if (this.buff(Taunt.class)== null && enemy == Dungeon.hero) {
			Buff.affect(this, Taunt.class);
			Buff.affect(this,HiddenShadow.class,6f);
		}

		if (Random.Int(5) == 0) {
			Buff.prolong(enemy, Dry.class, 10f);
		}
			
		if (Random.Int(5) == 0) {
			Buff.prolong(enemy, Slow.class, 10f);
		}			

		return damage;
	}

	
	@Override
	public void add(Buff buff) {
		if (buff instanceof Dry ) {
			if (HP < HT) {
				HP+=HT/10;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}
		} else if (buff instanceof Vertigo) {
			if (Level.water[this.pos])
				damage(Random.NormalIntRange(HT / 2, HT), buff);
			else
				damage(Random.NormalIntRange(1, HT * 2 / 3), buff);
		} else {
			super.add(buff);
		}
	}

	{
		resistances.add(DamageType.ShockDamage.class);
		resistances.add(WandOfLightning.class);
	}
	
	@Override
	public void die(Object cause) {
        MiniSand.spawnAround(this.pos);
		super.die(cause);
	}

    public static class MiniSand extends Mob {

		protected static final float SPAWN_DELAY = 1f;
		{
			spriteClass = SandmobSprite.class;

			HP = HT = 45+(adj(0)*Random.NormalIntRange(2, 5));
			evadeSkill = 25;


			EXP = 0;

			state = WANDERING;
		}
		
		int generation = 0;

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(15, 20+adj(0));
		}

		@Override
		public int hitSkill(Char target) {
			return 30+adj(0);
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(5) == 0) {
				Buff.prolong(enemy, Dry.class, 10f);
			}
			
			if (Random.Int(5) == 0) {
				Buff.prolong(enemy, Slow.class, 10f);
			}			

			return damage;
		}
		
	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static MiniSand spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			MiniSand w = new MiniSand();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);
			return w;
  			
		} else {
			return null;
		}
	}

		@Override
		public void add(Buff buff) {
			 if (buff instanceof Vertigo) {
				if (Level.water[this.pos])
					damage(Random.NormalIntRange(HT / 2, HT), buff);
				else
					damage(Random.NormalIntRange(1, HT * 2 / 3), buff);
			} else {
				super.add(buff);
			}
		}

	}	

}
