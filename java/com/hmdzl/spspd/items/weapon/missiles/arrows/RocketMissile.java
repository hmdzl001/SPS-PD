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
package com.hmdzl.spspd.items.weapon.missiles.arrows;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class RocketMissile extends Arrows {

	{
		image = ItemSpriteSheet.ROCKET;
	}

	public RocketMissile() {
		this(1);
	}

	public RocketMissile(int number) {
		super(1,5);
		quantity = number;
	}

	@Override
	protected void onThrow(int cell) {
		Char enemy = Actor.findChar(cell);
		if (enemy == null || enemy == curUser) {
			boolean terrainAffected = false;
			for (int n : Level.NEIGHBOURS4OUT) {
				int c = cell + n;
				if (c >= 0 && c < Level.getLength()) {
					if (Dungeon.visible[c]) {
						CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
					}

					if (Level.flamable[c]) {
						Level.set(c, Terrain.EMBERS);
						GameScene.updateMap(c);
						terrainAffected = true;
					}

					if ((Dungeon.level.map[c] == Terrain.WALL || Dungeon.level.map[c] == Terrain.GLASS_WALL )&& Level.insideMap(c)){
						Level.set(c, Terrain.EMPTY);
						GameScene.updateMap(c);
						terrainAffected = true;
					}
					Char ch = Actor.findChar(c);
					if (ch != null) {
							int minDamage = ch.HT/10;
							int maxDamage = ch.HT/5;

							int dmg = Random.NormalIntRange(minDamage, maxDamage)
									- Math.max(ch.drRoll(),0);
							if (dmg > 0) {
								ch.damage(dmg, this);
							}
					}
				}
			}

			if (terrainAffected) {
				Dungeon.observe();
			}
		}else
			super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		boolean terrainAffected = false;
		for (int n : Level.NEIGHBOURS4OUT) {
			int c = defender.pos + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}

				if (Level.flamable[c]) {
					Level.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					terrainAffected = true;
				}

				if ((Dungeon.level.map[c] == Terrain.WALL || Dungeon.level.map[c] == Terrain.GLASS_WALL )&& Level.insideMap(c)){
					Level.set(c, Terrain.EMPTY);
					GameScene.updateMap(c);
					terrainAffected = true;
				}
				Char ch = Actor.findChar(c);
				if (ch != null) {
					int minDamage = ch.HT/10;
					int maxDamage = ch.HT/5;

					int dmg = Random.NormalIntRange(minDamage, maxDamage)
							- Math.max(ch.drRoll(),0);
					if (dmg > 0) {
						ch.damage(dmg, this);
					}
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}


			int minDamage = defender.HT/10;
			int maxDamage = defender.HT/5;

			int dmg = Random.NormalIntRange(minDamage, maxDamage)
					- Math.max(defender.drRoll(),0);
			if (dmg > 0) {
				defender.damage(dmg, this);
			}
		if (enchant != null)
			enchant.proc(this, attacker, defender, damage);
		super.proc(attacker, defender, damage);
	}

	@Override
	public Item random() {
		quantity = Random.Int(1, 2);
		return this;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}
}
