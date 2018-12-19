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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.effects.Beam;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.DeathRay;
import com.hmdzl.spspd.change.effects.particles.PurpleParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfDisintegration extends DamageWand {

	{

		image = ItemSpriteSheet.WAND_DISINTEGRATION;

		collisionProperties = Ballistica.WONT_STOP;		
	}

	public int min(int lvl){
		return 2+lvl;
	}

	public int max(int lvl){
		return 8+4*lvl;
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
			
			CellEmitter.center( c ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
		}
		
		if (terrainAffected) {
			Dungeon.observe();
		}
		
		int lvl = level + chars.size() + terrainBonus;
		for (Char ch : chars) {
			processSoulMark(ch, chargesPerCast());
			ch.damage( (int)( damageRoll(lvl) * (1 + 0.1 * Dungeon.hero.magicSkill())), this );
			ch.sprite.centerEmitter().burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
			ch.sprite.flash();
		}
	}

	private int distance() {
		return level() + 2;
	}

	@Override
	protected void fx( Ballistica beam, Callback callback ) {
		
		int cell = beam.path.get(Math.min(beam.dist, distance()));
		curUser.sprite.parent.add(new Beam.DeathRay(curUser.sprite.center(), DungeonTilemap.tileCenterToWorld( cell )));
		callback.call();
	}
}
