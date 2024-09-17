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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanBlack;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanStop;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.effects.Beam;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class WandOfFlow extends DamageWand {

	{
		image = ItemSpriteSheet.WAND_FLOW;
		//collisionProperties = Ballistica.PROJECTILE;
	}
	
	public int min(int lvl){
		return 1+lvl;
	}

	public int max(int lvl){
		return 5+3*lvl;
	}	
	
	@Override
	protected void onZap(Ballistica beam) {

		ArrayList<Char> chars = new ArrayList<>();

		int damage = (int)( damageRoll() * (1 + 0.1 * hero.magicSkill()));
				
		Char ch = Actor.findChar(beam.collisionPos);
		if (ch != null){
			processSoulMark(ch, chargesPerCast());
			ch.damage(damage, this,2);
			Buff.affect(ch,Wet.class,5f);
			if (ch.isAlive() && beam.path.size() > beam.dist+1) {
				Ballistica trajectory = new Ballistica(ch.pos, beam.path.get(beam.dist + 1), Ballistica.MAGIC_BOLT);
				int strength = Math.min( level() + 3, 5 );
				throwChar(ch, trajectory, strength);
			}
		}	
		Heap heap = Dungeon.depth.heaps.get(beam.collisionPos);
		if (heap != null) {
			heap.icehit();
		}			
		
	}
	
	public static void throwChar(final Char ch, final Ballistica trajectory, int power){
		int dist = Math.min(trajectory.dist, power);

		if (ch.properties().contains(Char.Property.BOSS))
			dist /= 2;

		if (dist == 0 ) return;

		if (Actor.findChar(trajectory.path.get(dist)) != null){
			dist--;
		}
		if (ch instanceof SheepSokoban ||
				ch instanceof SheepSokobanCorner ||
				ch instanceof SheepSokobanStop ||
				ch instanceof SheepSokobanSwitch ||
				ch instanceof SheepSokobanBlack) {
			dist=1;
		}
		final int newPos = trajectory.path.get(dist);

		if (newPos == ch.pos) return;

		final int finalDist = dist;
		final int initialpos = ch.pos;

		Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
			public void call() {
				if (initialpos != ch.pos) {
					//something cased movement before pushing resolved, cancel to be safe.
					ch.sprite.place(ch.pos);
					return;
				}
				ch.pos = newPos;
				if (ch instanceof SheepSokoban || 
						ch instanceof SheepSokobanCorner ||
						ch instanceof SheepSokobanStop ||
						ch instanceof SheepSokobanSwitch ||
						ch instanceof SheepSokobanBlack) {
					Dungeon.depth.mobPress((NPC) ch);
				} else {if (ch.pos == trajectory.collisionPos ) {
					ch.damage(Random.NormalIntRange((finalDist + 1) / 2, finalDist), this,2);
					Paralysis.prolong(ch, Paralysis.class, Random.NormalIntRange((finalDist + 1) / 2, finalDist));
				} Dungeon.depth.press(ch.pos, ch);}
				if (ch == hero){
					Dungeon.observe();
				}
			}
		}), -1);
	}

	public static void pushChar(final Char ch, final Ballistica trajectory, int power){
		int dist = Math.min(trajectory.dist, power);

		if (ch.properties().contains(Char.Property.BOSS))
			dist /= 2;

		if (dist == 0 ) return;

		if (Actor.findChar(trajectory.path.get(dist)) != null){
			dist--;
		}
		if (ch instanceof SheepSokoban ||
				ch instanceof SheepSokobanCorner ||
				ch instanceof SheepSokobanStop ||
				ch instanceof SheepSokobanSwitch ||
				ch instanceof SheepSokobanBlack) {
			dist=1;
		}
		final int newPos = trajectory.path.get(dist);

		if (newPos == ch.pos) return;

		final int finalDist = dist;
		final int initialpos = ch.pos;

		Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
			public void call() {
				if (initialpos != ch.pos) {
					//something cased movement before pushing resolved, cancel to be safe.
					ch.sprite.place(ch.pos);
					return;
				}
				ch.pos = newPos;
				if (ch instanceof SheepSokoban ||
						ch instanceof SheepSokobanCorner ||
						ch instanceof SheepSokobanStop ||
						ch instanceof SheepSokobanSwitch ||
						ch instanceof SheepSokobanBlack) {
					Dungeon.depth.mobPress((NPC) ch);
				} else { Dungeon.depth.press(ch.pos, ch);}
				if (ch == hero){
					Dungeon.observe();
				}
			}
		}), -1);
	}

		/*Char ch;

		for (int i = 1; i < Ballistica.distance; i++) {

			int c = Ballistica.trace[i];

			int before = Dungeon.level.map[c];

			if ((ch = Actor.findChar(c)) != null) {

				if (i == Ballistica.distance - 1) {

					int level = level();
					int damage= Random.Int(level+3, 6 + level * 3);
					Buff.prolong(ch, Wet.class, 5f);
					if (Dungeon.hero.buff(MoonFury.class) != null){ damage *= (int) 4f; Buff.detach(Dungeon.hero, MoonFury.class);}
					ch.damage(damage, this);

				} else {

					int next = Ballistica.trace[i + 1];
					if ((Level.passable[next] || Level.avoid[next])
							&& Actor.findChar(next) == null 

							&& !(ch instanceof SheepSokoban || 
							     ch instanceof SheepSokobanCorner ||
							     ch instanceof SheepSokobanStop ||
								 ch instanceof SheepSokobanSwitch ||
								 ch instanceof SheepSokobanBlack)

							) {

						if ((ch instanceof SheepSokoban || 
							     ch instanceof SheepSokobanCorner ||
							     ch instanceof SheepSokobanStop ||
								 ch instanceof SheepSokobanSwitch ||
								 ch instanceof SheepSokobanBlack)
								 && (Dungeon.level.map[next]==Terrain.FLEECING_TRAP ||
										 Dungeon.level.map[next]==Terrain.CHANGE_SHEEP_TRAP)){							
							
						} else {
							processSoulMark(ch, chargesPerCast());
							Buff.prolong(ch, Wet.class, 5f);
							Actor.addDelayed(new Pushing(ch, ch.pos, next), -1);
						}

						ch.pos = next;
						Actor.freeCell(next);

						if (ch instanceof Shopkeeper)
							ch.damage(0, this);

						// FIXME
						
						if (ch instanceof SheepSokoban || 
							     ch instanceof SheepSokobanCorner ||
							     ch instanceof SheepSokobanStop ||
								 ch instanceof SheepSokobanSwitch ||
								 ch instanceof SheepSokobanBlack){
							Dungeon.level.mobPress((NPC) ch);						
						}	else if (ch instanceof Mob && !(ch.properties().contains(Char.Property.IMMOVABLE))){
							Dungeon.level.mobPress((Mob) ch);
						
						} else {
							Dungeon.level.press(ch.pos, ch);
						}

					} else {
						int level = level();
						int damage= Random.Int(level+3, 6 + level * 3);
						Buff.prolong(ch, Wet.class, 5f);
						if (Dungeon.hero.buff(MoonFury.class) != null){ damage *= (int) 4f; Buff.detach(Dungeon.hero, MoonFury.class);}
						ch.damage(damage, this);

					}
				}
			}

		}

	}*/


	@Override
	protected void fx( Ballistica beam, Callback callback ) {
		
		curUser.sprite.parent.add(new Beam.WaterRay(curUser.sprite.center(), DungeonTilemap.tileCenterToWorld(beam.collisionPos )));
		callback.call();
	}

}
