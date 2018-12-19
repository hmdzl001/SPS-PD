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

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class WandOfPoison extends Wand {

	{
		image = ItemSpriteSheet.WAND_POISON;
		collisionProperties = Ballistica.MAGIC_BOLT;
	}

	

	@Override
	protected void onZap(Ballistica bolt) {
		
		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

			processSoulMark(ch, chargesPerCast());
			
			int poisonbase=5;
			
			if (Dungeon.hero.buff(Strength.class) != null){ poisonbase *= (int) 4f; Buff.detach(Dungeon.hero, Strength.class);}
			
			Buff.affect(ch, Poison.class).set(
					Poison.durationFactor(ch) * (poisonbase + level()*2));

		}	   
		
		Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {
			heap.poison();
		}
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.poison(curUser.sprite.parent, curUser.pos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

}
