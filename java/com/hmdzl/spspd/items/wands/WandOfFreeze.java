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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;
import com.hmdzl.spspd.items.Heap;

public class WandOfFreeze extends DamageWand {

	{
	    image = ItemSpriteSheet.WAND_FREEZE;
		collisionProperties = Ballistica.PROJECTILE;
	}

	public int min(int lvl){
		return 5+lvl*2;
	}

	public int max(int lvl){
		return 10+lvl*4;
	}	
	
	@Override
	protected void onZap(Ballistica bolt) {
		
		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

		    int damage = damageRoll();

			if (ch.buff(Frost.class) != null){
				return; //do nothing, can't affect a frozen target
			}
			if (ch.buff(Chill.class) != null){
				float chill = ch.buff(Chill.class).cooldown();
				damage = (int)(Math.round(damage * Math.pow(0.9f, chill)) * (1 + 0.1 * Dungeon.hero.magicSkill()));
			} else {
				ch.sprite.burst( 0xFF99CCFF, level() / 2 + 2 );
			}

			processSoulMark(ch, chargesPerCast());
			ch.damage(damage, this);

			if (ch.isAlive()){
				if (Level.water[ch.pos]){
					//20+(10*level)% chance
					if (Random.Int(10) >= 8-level() )
						Buff.affect(ch, Frost.class, Frost.duration(ch)*Random.Float(2f, 4f));
					else
						Buff.prolong(ch, Chill.class, 6+level());
				} else {
					Buff.prolong(ch, Chill.class, 4+level());
				}
			}
			
		}
		
		Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {
			heap.freeze();
		}	
		
	}
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.coldLight(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
	
}
