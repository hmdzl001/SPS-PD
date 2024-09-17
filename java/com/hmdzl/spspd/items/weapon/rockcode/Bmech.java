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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.bombs.MiniBomb;
import com.hmdzl.spspd.items.weapon.melee.start.XSaber;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

public class Bmech extends RockCode {

	{
	   //image = ItemSpriteSheet.WAND_MAGIC_MISSILE;
		collisionProperties = Ballistica.PROJECTILE;
		sname = "B.m";
	}

	@Override
	protected void onZap( Ballistica bolt ) {
		
		//Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		//if (heap != null) {
		//	heap.earthhit();
		//}
		new DungeonBomb().explode(bolt.collisionPos);
		
	}
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.firelarge(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
	
	@Override
	public void onHit(XSaber megaCannon, Char attacker, Char defender, int damage) {

		if ( Random.Int(10) == 1) {
			new MiniBomb().explode(defender.pos);
		}

		defender.damage(Random.Int( megaCannon.damageRoll(hero) ), ENERGY_DAMAGE,2);
	}

	
}
