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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.FlyingProtector;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BaBaSprite;
import com.hmdzl.spspd.sprites.SheepSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.BArray;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class WandOfFlock extends Wand {

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

						if (Dungeon.hero.subClass == HeroSubClass.LEADER && (Dungeon.depth < 51 || Dungeon.depth > 54)){
							MagicBombSheep bsheep = new MagicBombSheep();
							bsheep.pos = j;
							GameScene.add(bsheep);
						} else {
							MagicSheep sheep = new MagicSheep();
							sheep.lifespan = lifespan;
							sheep.pos = j;
							GameScene.add(sheep);
							Dungeon.level.mobPress(sheep);
						}


						CellEmitter.get(j).burst(Speck.factory(Speck.WOOL), 4);

						PathFinder.distance[j] = Integer.MAX_VALUE;

						continue sheepLabel;
					}
				}
				dist++;
			} while (dist < n);
		}
		
		if (Dungeon.depth>50 && Dungeon.depth<55){
			int spawnCell = Dungeon.level.randomRespawnCellMob();
			if (spawnCell>0){
			   FlyingProtector.spawnAt(spawnCell);
			   //GLog.w("How dare you violate the magic of this place! ");
			   GLog.w(Messages.get(this,"guard"));
			}
		}
		
	    Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {heap.summon();}
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.wool(curUser.sprite.parent, curUser.pos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	public static class MagicSheep extends NPC {

		{
			spriteClass = SheepSprite.class;
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
					Buff.affect(ch, Taunt.class,2f).object = id();
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
		public boolean interact() {
			return false;
		}
	}
	public static class MagicBombSheep extends NPC {

		{
			spriteClass = BaBaSprite.class;
			HP = HT = 20;
			state = HUNTING;
			properties.add(Property.UNKNOW);
			evadeSkill = 10;
			ally=true;
		}

		@Override
		protected boolean act() {
			damage(1,this);
			return  super.act();
		}
		
	@Override
	public int hitSkill(Char target) {
		return 100;
	}
		
		
	@Override
	public int damageRoll() {
	    return Random.NormalIntRange(Dungeon.depth+10, Dungeon.depth+20);
	}
	
	@Override
	protected Char chooseEnemy() {

		if (enemy == null || !enemy.isAlive()) {
			HashSet<Mob> enemies = new HashSet<Mob>();
			for (Mob mob : Dungeon.level.mobs) {
				if (mob.hostile && Level.fieldOfView[mob.pos]) {
					enemies.add(mob);
				}
			}

			enemy = enemies.size() > 0 ? Random.element(enemies) : null;
		}

		return enemy;
	}		

		@Override
		public boolean interact() {

		if (state == SLEEPING) {
			state = HUNTING;
		}
		
		int curPos = pos;

		moveSprite(pos, Dungeon.hero.pos);
		move(Dungeon.hero.pos);

		Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
		Dungeon.hero.move(curPos);

		Dungeon.hero.spend(1 / Dungeon.hero.speed());
		Dungeon.hero.busy();
		return true;
		}
	}
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}	
}
