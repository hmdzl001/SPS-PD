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
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.weapon.melee.start.XSaber;
import com.hmdzl.spspd.items.weapon.missiles.MegaCannon;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.EARTH_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.FIRE_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ICE_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.SHOCK_DAMAGE;

public class Mlaser extends RockCode {

	{
	   // image = ItemSpriteSheet.POCKETBALL_EMPTY;
		collisionProperties = Ballistica.PROJECTILE;
		sname = "M.l";
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
			int mixdamage = (int)Math.max(damageRoll/6,1);
			ch.damage( mixdamage,ENERGY_DAMAGE );
			ch.damage( mixdamage,EARTH_DAMAGE );
			ch.damage( mixdamage,FIRE_DAMAGE );
			ch.damage( mixdamage,ICE_DAMAGE );
			ch.damage( mixdamage,SHOCK_DAMAGE );
			ch.damage( mixdamage,LIGHT_DAMAGE );
			ch.damage( mixdamage,DARK_DAMAGE );
		}
	}
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.rainbow(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
	
	@Override
	public void onHit(XSaber megaCannon, Char attacker, Char defender, int damage) {

		if ( Random.Int(10) == 1 ) {
			defender.damage(Random.Int( 1,megaCannon.damageRoll(hero)/6 ), ENERGY_DAMAGE);
			defender.damage(Random.Int( 1,megaCannon.damageRoll(hero)/6 ), EARTH_DAMAGE);
			defender.damage(Random.Int( 1,megaCannon.damageRoll(hero)/6 ), FIRE_DAMAGE);
			defender.damage(Random.Int( 1,megaCannon.damageRoll(hero)/6 ), ICE_DAMAGE);
			defender.damage(Random.Int( 1,megaCannon.damageRoll(hero)/6 ), SHOCK_DAMAGE);
			defender.damage(Random.Int( 1,megaCannon.damageRoll(hero)/6 ), LIGHT_DAMAGE);
			defender.damage(Random.Int( 1,megaCannon.damageRoll(hero)/6 ), DARK_DAMAGE);

		}
	}

	
}
