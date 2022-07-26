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
import com.hmdzl.spspd.actors.blobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.scenes.GameScene;

import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class WandOfFirebolt extends DamageWand {

	{
	    image = ItemSpriteSheet.WAND_FIREBOLT;
		collisionProperties = Ballistica.PROJECTILE;
	}

	public int min(int lvl){
		return lvl;
	}

	public int max(int lvl){
		return 10+6*lvl;
	}	
	
	@Override
	protected void onZap( Ballistica bolt) {
		
		Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {
			heap.firehit();
		}		
		
		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {

			processSoulMark(ch, chargesPerCast());

		    ch.damage((int)( damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill())), this);

			
			Buff.affect(ch, Burning.class).set(5f);

			ch.sprite.emitter().burst(FlameParticle.FACTORY, 5);

		}
		
		GameScene.add( Blob.seed(bolt.collisionPos, 1, Fire.class ) );


	}
	
	/*public void explode(int cell, int damage) {
		// We're blowing up, so no need for a fuse anymore.
		
		Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		if (Dungeon.visible[cell]) {
			CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
		}

		boolean terrainAffected = false;
		for (int n : Level.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}

				if (Level.flamable[c]) {
					Level.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					terrainAffected = true;
				}

				// destroys items / triggers bombs caught in the blast.
				Heap heap = Dungeon.level.heaps.get(c);
				if (heap != null)
					heap.explode();

				Char ch = Actor.findChar(c);
				if (ch != null) {
					// those not at the center of the blast take damage less
					// consistently.
					int minDamage = c == cell ? Math.round(damage/10) : 1;
					int maxDamage = c == cell ? Math.round(damage/4) : Math.round(damage/10);

					int dmg = Random.NormalIntRange(minDamage, maxDamage)
							- Math.max(ch.drRoll(),0);
					if (dmg > 0) {
						ch.damage(dmg, this);
					}

					if (ch == Dungeon.hero && !ch.isAlive())
						// constant is used here in the rare instance a player
						// is killed by a double bomb.
						Dungeon.fail(Messages.format(ResultDescriptions.ITEM,"wand of firebolt"));
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}
	}*/
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.fire(curUser.sprite.parent, curUser.pos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}
