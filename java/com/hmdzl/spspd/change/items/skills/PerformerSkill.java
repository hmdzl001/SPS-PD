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
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.FireImbue;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroAction;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.MindVision;
import com.hmdzl.spspd.change.actors.buffs.EarthImbue;
import com.hmdzl.spspd.change.actors.buffs.Awareness;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.Speck;
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

public class PerformerSkill extends ClassSkill {

	{
		//name = "performer cloak";
		image = ItemSpriteSheet.ARMOR_PERFORMER;
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

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob, Charm.class,Charm.durationFactor(mob)*5f).object = curUser.id();
				mob.sprite.centerEmitter().start(Speck.factory(Speck.HEART),0.2f, 5);
				Buff.affect(mob, Amok.class, 10f);
				Buff.prolong(mob, Haste.class, 5f);
				Buff.prolong(mob, ArmorBreak.class, 20f).level(50);
			}
		}
		curUser.HP -= (curUser.HP / 2);
		Buff.affect(curUser, DefenceUp.class,10).level(25);
		Buff.affect(curUser, AttackUp.class,10).level(25);
        curUser.spend(Actor.TICK);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		}
}

