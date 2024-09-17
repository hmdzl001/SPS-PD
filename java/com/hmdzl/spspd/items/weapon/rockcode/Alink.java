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
package com.hmdzl.spspd.items.weapon.rockcode;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.npcs.MirrorImage;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.scrolls.ScrollOfMirrorImage;
import com.hmdzl.spspd.items.weapon.melee.start.XSaber;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Alink extends RockCode {

	{
	    //image = ItemSpriteSheet.POCKETBALL_EMPTY;
		collisionProperties = Ballistica.PROJECTILE;
		sname = "A.l";
	}

	@Override
	protected void onZap( Ballistica bolt ) {

		//Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		//if (heap != null) {
		//	heap.earthhit();
		//}

		int n = 2;

		if (Actor.findChar(bolt.collisionPos) != null && Ballistica.distance > 2) {
			bolt.sourcePos = Ballistica.trace[Ballistica.distance - 2];
		}

		boolean[] passable = BArray.or(Floor.passable, Floor.avoid, null);
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

		copyLabel: for (int i = 0; i < n; i++) {
				do {
					for (int j = 0; j < Floor.getLength(); j++) {
						if (PathFinder.distance[j] == dist) {

							MirrorImage mob = new MirrorImage();
							mob.duplicate(curUser);
							mob.pos = j;
							GameScene.add(mob);

							CellEmitter.get(j).burst(Speck.factory(Speck.WOOL), 4);
							PathFinder.distance[j] = Integer.MAX_VALUE;
							continue copyLabel;
						}
					}
					dist++;
				} while (dist < n);
			}
	}
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.slowness(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
	
	@Override
	public void onHit(XSaber megaCannon, Char attacker, Char defender, int damage) {

		if (Random.Int(10) == 1) {
			new ScrollOfMirrorImage.DelayedImageSpawner(1, 1, 1).attachTo(curUser);
		}

	}

	
}
