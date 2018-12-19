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
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.SmokeParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.BArray;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
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
		return 6+3*lvl;
	}
	
	
	@Override
	protected void onZap(Ballistica bolt) {

		Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {
			heap.burn();
		}

		Sample.INSTANCE.play(Assets.SND_ROCKS);

		int level = level();

		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

			ch.sprite.flash();

			processSoulMark(ch, chargesPerCast());

			int damage = (int) (damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill()));

			if (Dungeon.hero.buff(Strength.class) != null) {
				damage *= (int) 4f;
				Buff.detach(Dungeon.hero, Strength.class);
			}
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

				if (Level.flamable[c]) {
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
				Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
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
