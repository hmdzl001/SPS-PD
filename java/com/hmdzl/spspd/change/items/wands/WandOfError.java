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
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.change.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.change.actors.blobs.Regrowth;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.SpellSprite;
import com.hmdzl.spspd.change.items.bombs.Bomb;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.watabou.noosa.tweeners.AlphaTweener;

public class WandOfError extends Wand {

	{
	    image = ItemSpriteSheet.ERROR_WAND;
		
	}	
	
   protected void onZap(Ballistica bolt) {	
        Char ch = Actor.findChar(bolt.collisionPos);
        switch (Random.Int(10)){
			case 0:
			if (ch == curUser) {
			ScrollOfTeleportation.teleportHero(curUser);

		    } else if (ch != null && !(ch instanceof NPC)) {

			int count = 10;
			int pos;
			do {
				pos = Dungeon.level.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (pos == -1);

			if (pos == -1) {

				GLog.w("In a blink of an eye you were teleported to another location of the level.");

			} else {

				ch.pos = pos;
				ch.sprite.place(ch.pos);
				ch.sprite.visible = Dungeon.visible[pos];
				GLog.i(curUser.name + " teleported " + ch.name
						+ " to somewhere");}
		    }
			break;
			case 1:
			if (ch != null) {
			int damage = (ch.HT/4) ;
			if (Dungeon.hero.buff(Strength.class) != null)
			{damage *= (int) 4f; Buff.detach(Dungeon.hero, Strength.class);}
				ch.damage(damage, this);
			}
			break;
			case 2:
			if (ch != null) {Buff.affect(ch, Slow.class,10);}
			break;
			case 3:
			  if (Ballistica.distance > 9) {
				  bolt.sourcePos = Ballistica.trace[8];
		      } else if (Actor.findChar(bolt.sourcePos) != null && Ballistica.distance > 1) {
				  bolt.sourcePos = Ballistica.trace[Ballistica.distance - 2];
		      }
            curUser.sprite.visible = true;
		    appear(Dungeon.hero, bolt.sourcePos);
		    Dungeon.observe();
			break;
			case 4:
				for (int i = 1; i < Ballistica.distance - 1; i++) {
					int p = Ballistica.trace[i];
					int c = Dungeon.level.map[p];
					if (c == Terrain.EMPTY || c == Terrain.EMBERS
							|| c == Terrain.EMPTY_DECO) {

						Level.set(p, Terrain.GRASS);

					}
				}
				int c = Dungeon.level.map[bolt.collisionPos];
				if (c == Terrain.EMPTY || c == Terrain.EMBERS
						|| c == Terrain.EMPTY_DECO || c == Terrain.GRASS
						|| c == Terrain.HIGH_GRASS) {
					GameScene.add(Blob.seed(bolt.collisionPos, (level() + 2) * 20, Regrowth.class));
				}
			break;
			case 5:
				switch (Random.Int(3)) {
					case 0:
						GameScene.add( Blob.seed( bolt.collisionPos, 800, ConfusionGas.class ) );
						break;
					case 1:
						GameScene.add( Blob.seed( bolt.collisionPos, 500, ToxicGas.class ) );
						break;
					case 2:
						GameScene.add( Blob.seed( bolt.collisionPos, 200, ParalyticGas.class ) );
						break;
				}
			break;
			case 6:
				new Bomb().explode(bolt.collisionPos);
			break;
			case 7:
				new LightningTrap().set( curUser.pos ).activate(curUser);
				Buff.prolong(curUser, Recharging.class, 20f);
				ScrollOfRecharging.charge(curUser);
				SpellSprite.show(curUser, SpellSprite.CHARGE);
			break;
			case 8:
				switch (Random.Int(2)){
					case 0:
						if (ch != null)
							Buff.affect(ch, Burning.class).reignite(ch);
						break;
					case 1:
						if (ch!= null)
							Buff.affect(ch, Frost.class, Frost.duration(ch) * Random.Float(3f, 5f));
						break;
				}
			break;
			default:
				GLog.i("nothing happened");
			break;
		} 
	}
	
	public static void appear(Char ch, int pos) {

		ch.sprite.interruptMotion();

		ch.move(pos);
		ch.sprite.place(pos);

		if (ch.invisible == 0) {
			ch.sprite.alpha(0);
			ch.sprite.parent.add(new AlphaTweener(ch.sprite, 1, 0.4f));
		}

		ch.sprite.emitter().start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		Sample.INSTANCE.play(Assets.SND_TELEPORT);
	}
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.rainbow(curUser.sprite.parent, bolt.sourcePos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}	
	
}
