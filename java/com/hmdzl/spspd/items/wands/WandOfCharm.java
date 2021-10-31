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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfCharm extends Wand {

	{
	    image = ItemSpriteSheet.WAND_CHARM;
		collisionProperties = Ballistica.PROJECTILE;
	}

	@Override
	protected void onZap(Ballistica bolt) {
		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

			processSoulMark(ch, chargesPerCast());

			if (ch == Dungeon.hero) {
				Buff.affect(ch, Vertigo.class,5f);
			} else {
				Buff.affect(ch, Amok.class, chargesPerCast() + level());
				Buff.affect(ch, Charm.class, Random.IntRange(chargesPerCast(), 3*level())).object = curUser.id();
			    ch.sprite.centerEmitter().start(Speck.factory(Speck.HEART),
					0.2f, 5);
			    Sample.INSTANCE.play(Assets.SND_CHARMS);
			}
		}
		
	    Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {heap.light();}			
	}

	@Override
	protected int initialCharges() {
		return 1;
	}

	@Override
	protected int chargesPerCast() {
		return Math.max(1, (int)Math.ceil(curCharges*0.5f));
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.purpleLight(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}
