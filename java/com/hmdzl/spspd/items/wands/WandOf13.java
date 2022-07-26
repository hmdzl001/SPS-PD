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
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.effects.Beam;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WandOf13 extends DamageWand {

	{

		image = ItemSpriteSheet.WAND_DISINTEGRATION;

		collisionProperties = Ballistica.WONT_STOP;		
	}

	public int min(int lvl){
		return lvl;
	}

	public int max(int lvl){
		return 1+2*lvl;
	}	
	
	@Override
	protected void onZap( Ballistica beam) {

		boolean terrainAffected = false;

		int level = level();

        int maxDistance = Math.min(distance(), beam.dist); 
		
		ArrayList<Char> chars = new ArrayList<>();

		int terrainPassed = 2, terrainBonus = 0;
		for (int c : beam.subPath(1, maxDistance)) {
			
			Char ch;
			if ((ch = Actor.findChar( c )) != null) {

				//we don't want to count passed terrain after the last enemy hit. That would be a lot of bonus levels.
				//terrainPassed starts at 2, equivalent of rounding up when /3 for integer arithmetic.
				terrainBonus += terrainPassed/3;
				terrainPassed = terrainPassed%3;

				chars.add( ch );
			}

			if (Level.solid[c])
				terrainPassed++;
			Heap heap = Dungeon.level.heaps.get(c);
			if (heap != null) {heap.darkhit();}
			
			CellEmitter.center( c ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
		}
		
		if (terrainAffected) {

			Dungeon.observe();
		}
		
		int lvl = Math.max(level - chars.size() , 1);
		for (Char ch : chars) {
			processSoulMark(ch, chargesPerCast());
			Buff.affect(ch,Bleeding.class).set(damageRoll());
			Buff.affect(ch,ArmorBreak.class,5f).level(20);
			ch.damage( (int)( damageRoll(lvl) * (1 + 0.1 * Dungeon.hero.magicSkill())), this );
			ch.sprite.centerEmitter().burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
			ch.sprite.flash();
		}
	}

	private int distance() {
		return Math.min(10,level()+1);
	}

	@Override
	protected void fx( Ballistica beam, Callback callback ) {
		
		int cell = beam.path.get(Math.min(beam.dist, distance()));
		curUser.sprite.parent.add(new Beam.DeathRay(curUser.sprite.center(), DungeonTilemap.tileCenterToWorld( cell )));
		callback.call();
	}
}
