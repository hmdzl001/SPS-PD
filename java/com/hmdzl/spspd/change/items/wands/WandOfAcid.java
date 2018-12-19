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
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class WandOfAcid extends DamageWand {

	{
	    image = ItemSpriteSheet.WAND_ACID;
		collisionProperties = Ballistica.PROJECTILE;
	}

	public int min(int lvl){
		return 2+lvl;
	}

	public int max(int lvl){
		return 6+4*lvl;
	}		
	
	@Override
	protected void onZap( Ballistica bolt ) {
		
		Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {
			heap.poison();
		}
		
		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

			processSoulMark(ch, chargesPerCast());
			if (ch.isAlive() && Random.Int(2) == 0){
				Buff.affect(ch, Ooze.class);
			}
			ch.damage((int)( damageRoll() * (1 + 0.1 * hero.magicSkill())), this);


		}
	}
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.foliage(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}
