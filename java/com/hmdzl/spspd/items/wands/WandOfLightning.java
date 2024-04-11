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
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Lightning;
import com.hmdzl.spspd.effects.particles.SparkParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WandOfLightning extends DamageWand {

	{
	    image = ItemSpriteSheet.WAND_LIGHTNING;
		collisionProperties = Ballistica.PROJECTILE;
	}

	private ArrayList<Char> affected = new ArrayList<>();

	ArrayList<Lightning.Arc> arcs = new ArrayList<>();

	public int min(int lvl){
		return 5+lvl;
	}

	public int max(int lvl){
		return Math.round(10 + (lvl * lvl / 4f));
	}	
	
	@Override
	protected void onZap( Ballistica bolt ) {

		float multipler = 0.4f + (0.6f/affected.size());
		if (Floor.water[bolt.collisionPos]) multipler *= 1.5f;

		int min = 5+level();
		int max = Math.round(10 + (level() * level() / 4f));

		for (Char ch : affected){
			processSoulMark(ch, chargesPerCast());
			ch.damage((int)((1 + 0.1 * Dungeon.hero.magicSkill()) * Math.round(Random.NormalIntRange(min, max) * multipler)), this);

			if (ch == Dungeon.hero) Camera.main.shake( 2, 0.3f );
			ch.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
			ch.sprite.flash();
		}
		// Everything is processed in fx() method
		if (!curUser.isAlive()) {
			Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
			//GLog.n("You killed yourself with your own Wand of Lightning...");
		}


		Heap heap = Dungeon.depth.heaps.get(bolt.collisionPos);
		if (heap != null) {heap.shockhit();}
	}

	private void arc( Char ch ) {
		
		affected.add( ch );

		for (int i : Floor.NEIGHBOURS4) {
			int cell = ch.pos + i;

			Char n = Actor.findChar( cell );
			if (n != null && !affected.contains( n )) {
				arcs.add(new Lightning.Arc(ch.pos, n.pos));
				arc(n);
			}
		}

		if (Floor.water[ch.pos] && !ch.flying){
			PathFinder.buildDistanceMap( ch.pos, BArray.not( Floor.solid, null ), 2 );
			for (int i = 0; i < PathFinder.distance.length; i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE) {
					//player can only be hit by lightning from an adjacent enemy.
					if (!Floor.insideMap(i) || Actor.findChar(i) == Dungeon.hero) continue;
					Char n = Actor.findChar(i);
					if (n != null && !affected.contains(n)) {
						arcs.add(new Lightning.Arc(ch.pos, n.pos));
						arc(n);
					}
				}
			}
		}
	}
	
	@Override
	protected void fx( Ballistica bolt, Callback callback ) {

		affected.clear();
		arcs.clear();
		arcs.add( new Lightning.Arc(bolt.sourcePos, bolt.collisionPos));

		int cell = bolt.collisionPos;

		Char ch = Actor.findChar( cell );
		if (ch != null) {
			arc(ch);
		} else {
			CellEmitter.center( cell ).burst( SparkParticle.FACTORY, 3 );
		}

		//don't want to wait for the effect before processing damage.
		curUser.sprite.parent.add( new Lightning( arcs, null ) );
		callback.call();
	}

}
