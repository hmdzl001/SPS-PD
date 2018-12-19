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
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.Regrowth;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.effects.Beam;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.RainbowParticle;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfLight extends DamageWand {

	{
		image = ItemSpriteSheet.WAND_REGROWTH;
		collisionProperties = Ballistica.PROJECTILE;
	}

	public int min(int lvl){
		return 3+lvl;
	}

	public int max(int lvl){
		return 6+4*lvl;
	}	

	@Override
	protected void onZap(Ballistica beam) {
		Char ch = Actor.findChar(beam.collisionPos);
		if (ch != null) {

		    processSoulMark(ch, chargesPerCast());
			affectTarget(ch);

		if (Random.Int(5+level()) >= 3) {
			Buff.prolong(ch, Blindness.class, 2f + (level() * 0.34f));
			ch.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6 );
		}
		Buff.prolong( curUser, Light.class, 5f+level());
		}
		
	    Heap heap = Dungeon.level.heaps.get(beam.collisionPos);
		if (heap != null) {heap.light();}		
	}
	
	private void affectTarget(Char ch){
		int dmg = (int)( damageRoll() * (1 + 0.1 * Dungeon.hero.magicSkill()));

		//three in (5+lvl) chance of failing
		if (Random.Int(5+level()) >= 3) {
			Buff.prolong(ch, Blindness.class, 2f + (level() * 0.333f));
			ch.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6 );
		}

		if (ch.properties().contains(Char.Property.DEMONIC) || ch.properties().contains(Char.Property.UNDEAD)){
			ch.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10+level() );
			Sample.INSTANCE.play(Assets.SND_BURNING);

			ch.damage(Math.round(dmg*1.333f), this);
		} else {
			ch.sprite.centerEmitter().burst( RainbowParticle.BURST, 10+level() );

			ch.damage(dmg, this);
		}

	}	

	@Override
	protected void fx(Ballistica beam, Callback callback) {
		curUser.sprite.parent.add(
				new Beam.LightRay(curUser.sprite.center(), DungeonTilemap.tileCenterToWorld(beam.collisionPos)));
		callback.call();
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
	
}
