/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.items.bombs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.PlantPotBlock;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class DungeonBomb extends Bomb {

	{
		//name = "bomb";
		image = ItemSpriteSheet.BOMB;
	}

	@Override
	public void explode(int cell) {
		super.explode(cell);

		boolean terrainAffected = false;
		for (int n : Floor.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Floor.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}

				if (Floor.flamable[c]) {
					Floor.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					terrainAffected = true;
				}

				if ((Dungeon.depth.map[c] == Terrain.WALL || Dungeon.depth.map[c] == Terrain.GLASS_WALL )&& Floor.insideMap(c)){
					Floor.set(c, Terrain.EMPTY);
					GameScene.updateMap(c);
					terrainAffected = true;
				}
				if ((Dungeon.depth.map[c] == Terrain.FLOWER_POT)&& Floor.insideMap(c)){
					Floor.set(c, Terrain.EMPTY);
					GameScene.updateMap(c);
					terrainAffected = true;
					Dungeon.depth.drop(new PlantPotBlock(), c).sprite.drop();
				}

				Char ch = Actor.findChar(c);
				if (ch != null) {
					// those not at the center of the blast take damage less
					// consistently.
					if (ch.properties().contains(Char.Property.BOSS) || ch.properties().contains(Char.Property.MINIBOSS)){
					int minDamage = ch.HT/10;
					int maxDamage = ch.HT/5;

					int dmg = Random.NormalIntRange(minDamage, maxDamage)
							- Math.max(ch.drRoll(),0);
					if (dmg > 0) {
						ch.damage(dmg, this);
					}
					
					} else {
						
				
					int minDamage = ch.HT/5;
					int maxDamage = ch.HT/4;

					int dmg = Random.NormalIntRange(minDamage, maxDamage);
							//- Math.max(ch.drRoll(),0);
					if (dmg > 0) {
						ch.damage(dmg, this);
					}
				  }
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}
		
	}


	@Override
	public Item random() {
		switch (Random.Int(2)) {
		case 0:
		default:
			return this;
		case 1:
			return new DoubleBomb();
		}
	}


	@Override
	public int price() {
		return 10 * quantity;
	}

	public static class DoubleBomb extends DungeonBomb {

		{
			//name = "two bombs";
			image = ItemSpriteSheet.DBL_BOMB;
			stackable = false;
		}

		@Override
		public boolean doPickUp(Hero hero) {
			DungeonBomb bomb = new DungeonBomb();
			bomb.quantity(2);
			if (bomb.doPickUp(hero)) {
				// isaaaaac....
				hero.sprite.showStatus(CharSprite.NEUTRAL, "1+1 free!");
				return true;
			}
			return false;
		}
	}
}
