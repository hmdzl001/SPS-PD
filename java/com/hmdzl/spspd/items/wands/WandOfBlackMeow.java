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
package com.hmdzl.spspd.items.wands;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CatSheepSprite;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;

public class WandOfBlackMeow extends Wand {
	
    private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing(	0xFFFFFF);
	
	
	@Override
	public ItemSprite.Glowing glowing() {
		return WHITE;
	}


	{
	    image = ItemSpriteSheet.WAND_FLOCK;
		collisionProperties = Ballistica.PROJECTILE;
	}

	@Override
	protected void onZap(Ballistica bolt) {

		int level = level();

		int n = 1;

		if (Actor.findChar( bolt.collisionPos) != null && Ballistica.distance > 2) {
			bolt.sourcePos = Ballistica.trace[Ballistica.distance - 2];
		}

		boolean[] passable = BArray.or(Level.passable, Level.avoid, null);
		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				passable[((Char) actor).pos] = false;
			}
		}

		PathFinder.buildDistanceMap(bolt.collisionPos, passable, n);
		int dist = 0;

		if (Actor.findChar(bolt.collisionPos) != null) {
			PathFinder.distance[bolt.collisionPos] = Integer.MAX_VALUE;
			dist = 1;
		}

		float lifespan = 2 + level;

		sheepLabel: for (int i = 0; i < n; i++) {
			do {
				for (int j = 0; j < Level.getLength(); j++) {
					if (PathFinder.distance[j] == dist) {

						
						MagicMeow sheep = new MagicMeow();
						sheep.lifespan = lifespan;
						sheep.pos = j;
						GameScene.add(sheep);
						Dungeon.level.mobPress(sheep);
						


						CellEmitter.get(j).burst(Speck.factory(Speck.WOOL), 4);

						PathFinder.distance[j] = Integer.MAX_VALUE;

						continue sheepLabel;
					}
				}
				dist++;
			} while (dist < n);
		}
			
	    Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {heap.lighthit();}
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.wool(curUser.sprite.parent, curUser.pos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	public static class MagicMeow extends NPC {

		{
			spriteClass = CatSheepSprite.class;
			properties.add(Property.UNKNOW);
			flying = true;
			ally=true;
		}

		public float lifespan;

		private boolean initialized = false;

		@Override
		protected boolean act() {
			if (initialized) {
				HP = 0;

				destroy();
				sprite.die();

			} else {
				initialized = true;
			/*for (int n : Level.NEIGHBOURS8DIST2) {
				Char ch = Actor.findChar(n);
				if (ch != null && ch != this && ch.isAlive()) {
					Buff.affect(ch, SkillUse.class,2f).object = id();
				}
			}*/
				spend( lifespan + Random.Float(2) );
			}
			return true;
		}

		@Override
		public void damage( int dmg, Object src ) {
		}

		@Override
		public int defenseProc(Char enemy, int damage) {

			int dmg = Random.IntRange(0, hero.lvl*6);
			if (dmg > 0) {
				enemy.damage(dmg, LIGHT_DAMAGE);
			}

			return super.defenseProc(enemy, damage);
		}

		@Override
		public boolean interact() {
			return false;
		}
	}
}
