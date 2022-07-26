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
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfMeteorite extends DamageWand {
                   
	{
		image = ItemSpriteSheet.WAND_METEORITE;
		collisionProperties = Ballistica.PROJECTILE;
	}

	public int min(int lvl){
		return lvl;
	}

	public int max(int lvl){
		return 12+6*lvl;
	}
	
	
	@Override
	protected void onZap(Ballistica bolt) {

		Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {
			heap.firehit();
		}

		Sample.INSTANCE.play(Assets.SND_ROCKS);

		int level = level();

		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

			ch.sprite.flash();

			processSoulMark(ch, chargesPerCast());

			int damage = (int) (damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill()));

			ch.damage(damage, this);

			if (ch.isAlive() && Random.Int(2) == 0) {
				Buff.prolong(ch, Paralysis.class, Random.IntRange(5, level));
			}
			CellEmitter.get(bolt.collisionPos).start(Speck.factory(Speck.ROCK), 0.07f, 5);
			Camera.main.shake(3, 0.07f * 3);
		}


		for (int n : Level.NEIGHBOURS9) {
			int c = bolt.collisionPos + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 2);
				}

				if ((Level.flamable[c] ||  Dungeon.level.map[c] == Terrain.GLASS_WALL ) && Level.insideMap(c)) {
					Level.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
				}

				// destroys items / triggers bombs caught in the blast.
				Char ch2 = Actor.findChar(c);
				if (ch2 != null) {
					int dmg = (int) (damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill())* chargesPerCast()/9) ;
					if (dmg > 0) {
						ch2.damage(dmg, this);
					}
				}
			}


			if (!curUser.isAlive()) {
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n("You killed yourself with your own Wand of Avalanche...");
			}
		}
	}

	@Override
	protected int initialCharges() {
		return 1;
	}	
	
	@Override	
	protected int chargesPerCast() {
		return Math.max(1, curCharges);
	}	
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.earth(curUser.sprite.parent, curUser.pos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}
