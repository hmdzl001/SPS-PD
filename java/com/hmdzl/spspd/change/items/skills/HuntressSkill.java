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
package com.hmdzl.spspd.change.items.skills;

import java.util.HashMap;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.FireImbue;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.MindVision;
import com.hmdzl.spspd.change.actors.buffs.EarthImbue;
import com.hmdzl.spspd.change.actors.buffs.Awareness;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.missiles.Shuriken;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.MissileSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Callback;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.watabou.utils.Random;
import com.watabou.noosa.audio.Sample;

public class HuntressSkill extends ClassSkill {

	{
		//name = "huntress cloak";
		image = ItemSpriteSheet.ARMOR_HUNTRESS;
	}

	@Override
	public void doSpecial() {

		/*Item proto = new Shuriken();

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {

				Callback callback = new Callback() {
					@Override
					public void call() {
						curUser.attack(targets.get(this));
						targets.remove(this);
						if (targets.isEmpty()) {
							curUser.spendAndNext(curUser.attackDelay());
						}
					}
				};

				((MissileSprite) curUser.sprite.parent
						.recycle(MissileSprite.class)).reset(curUser.pos,
						mob.pos, proto, callback);

				targets.put(callback, mob);
			}
		}

		if (targets.size() == 0) {
			GLog.w(TXT_NO_ENEMIES);
			return;
		}*/

		curUser.HP -= (curUser.HP / 2);

        curUser.spend(Actor.TICK);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		
		switch(Random.Int(4)){
			case 0:
		    Buff.affect(curUser, FireImbue.class).set(10f);
			break;
			case 1:
			Buff.affect(curUser, EarthImbue.class,10);
			break;
			case 2:
		    Buff.affect(curUser, Awareness.class,10);
			break;
			case 3:
	        Buff.affect(curUser, BerryRegeneration.class);
			break;
		}
	}

}