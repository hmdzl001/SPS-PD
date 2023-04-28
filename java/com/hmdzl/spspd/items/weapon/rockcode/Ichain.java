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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Shieldblock;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.weapon.missiles.MegaCannon;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class Ichain extends RockCode {

	{
	    //image = ItemSpriteSheet.POCKETBALL_EMPTY;
		collisionProperties = Ballistica.PROJECTILE;
		sname = "I.c";
	}

	@Override
	protected void onZap( Ballistica bolt ) {
		
		//Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		//if (heap != null) {
		//	heap.earthhit();
		//}
		int damageRoll = Random.Int(Dungeon.hero.lvl,Dungeon.hero.lvl*3);

		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {
			ch.damage( 2 * damageRoll, Dungeon.hero );
			if (ch.isAlive() && Random.Int(4) == 0){
				Buff.affect(ch, Shieldblock.class,3f);
			}
		}
	}
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.wool(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
	
	@Override
	public void onHit(MegaCannon megaCannon, Char attacker, Char defender, int damage) {

		if ( megaCannon.charge > 2 ) {
			Buff.affect(defender, Paralysis.class,3f);
		}

		defender.damage(Random.Int( megaCannon.damageRoll(hero) ), Dungeon.hero);
	}

	
}
