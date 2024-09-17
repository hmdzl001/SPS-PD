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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.damageblobs.FireEffectDamage;
import com.hmdzl.spspd.actors.blobs.damageblobs.ShockEffectDamage;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.blessbuff.FireBless;
import com.hmdzl.spspd.actors.buffs.blessbuff.ShockBless;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfShockElement extends DamageWand {
                   
	{
		image = ItemSpriteSheet.WANDOFE_SHOCK;
		collisionProperties = Ballistica.STOP_TARGET;
	}

	public int min(int lvl){
		return lvl;
	}

	public int max(int lvl){
		return 10+5*lvl;
	}
	
	
	@Override
	protected void onZap(Ballistica bolt) {

		Heap heap = Dungeon.depth.heaps.get(bolt.collisionPos);
		if (heap != null) {
			heap.firehit();
		}

		//Sample.INSTANCE.play(Assets.SND_ROCKS);

		int level = level();

		Char ch = Actor.findChar(bolt.collisionPos);

		int damage = (int) (damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill()));
		Buff.prolong( curUser, ShockBless.class, chargesPerCast());

		if (ch != null) {
			processSoulMark(ch, chargesPerCast());
			ch.damage(damage, this,2);
		}


		for (int n : Floor.NEIGHBOURS9) {
			int c = bolt.collisionPos + n;
			if (c >= 0 && c < Floor.getLength()) {
				 GameScene.add( Blob.seed(c,chargesPerCast()+5, ShockEffectDamage.class ) );
				Char ch2 = Actor.findChar(c);
				if (ch2 != null) {
					processSoulMark(ch2, chargesPerCast());
					//int damage = (int) (damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill()));
					ch2.damage(damage, this,2);
				}
			}
		}
	}

	@Override
	protected int initialCharges() {
		return 3;
	}	
	
	@Override	
	protected int chargesPerCast() {
		return Math.max(1, curCharges);
		//return 1;
	}
	@Override
	protected int mindistance() {
		return 2;
	}
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.blueLight(curUser.sprite.parent, curUser.pos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}
