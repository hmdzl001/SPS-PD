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
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfBlood extends DamageWand {

	{
		image = ItemSpriteSheet.WAND_BLOOD;
		collisionProperties = Ballistica.PROJECTILE;
	}
	
	public int min(int lvl){
		return lvl;
	}

	public int max(int lvl){
		return 6+2*lvl;
	}	

	@Override
	protected void onZap(Ballistica bolt) {
		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

			processSoulMark(ch, chargesPerCast());
			
			ch.damage((int)( damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill())), this);
			if (curUser.HP < curUser.HT){
			curUser.HP += Math.min(Random.Int(0,damageRoll()),(curUser.HT - curUser.HP));}
		}
		
	    Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {heap.darkhit();}
		
	}


	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.shadow(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	/*public static void appear(Char ch, int pos) {

		ch.sprite.interruptMotion();

		ch.move(pos);
		ch.sprite.place(pos);

		if (ch.invisible == 0) {
			ch.sprite.alpha(0);
			ch.sprite.parent.add(new AlphaTweener(ch.sprite, 1, 0.4f));
		}

		ch.sprite.emitter().start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		Sample.INSTANCE.play(Assets.SND_TELEPORT);
	}*/
}
