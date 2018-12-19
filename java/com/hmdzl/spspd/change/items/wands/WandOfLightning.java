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
package com.hmdzl.spspd.change.items.wands;

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Lightning;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

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
		if (Level.water[bolt.collisionPos]) multipler *= 1.5f;

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
			Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
			//GLog.n("You killed yourself with your own Wand of Lightning...");
		}


		Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {heap.lit();}
	}

	private void arc( Char ch ) {
		
		affected.add( ch );

		for (int i : Level.NEIGHBOURS8) {
			int cell = ch.pos + i;

			Char n = Actor.findChar( cell );
			if (n != null && !affected.contains( n )) {
				arcs.add(new Lightning.Arc(ch.pos, n.pos));
				arc(n);
			}
		}

		if (Level.water[ch.pos] && !ch.flying){
			for (int i : Level.NEIGHBOURS8DIST2) {
				int cell = ch.pos + i;
				//player can only be hit by lightning from an adjacent enemy.
				if (!Level.insideMap(cell) || Actor.findChar(cell) == Dungeon.hero) continue;

				Char n = Actor.findChar( ch.pos + i );
				if (n != null && !affected.contains( n )) {
					arcs.add(new Lightning.Arc(ch.pos, n.pos));
					arc(n);
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
