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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Disarm;
import com.hmdzl.spspd.change.actors.buffs.Fury;
import com.hmdzl.spspd.change.actors.buffs.BloodImbue;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.actors.buffs.SpAttack;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.pets.PET;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.noosa.audio.Sample;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashMap;


public class WarriorSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "warrior suit of armor";
		image = ItemSpriteSheet.ARMOR_WARRIOR;
	}

	private HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();

	@Override
	public void doSpecial() {
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.prolong(curUser, BloodImbue.class,30f);
		charge += 10;
	}

	@Override
	public void doSpecial2() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {
				if (Dungeon.level.distance(curUser.pos, mob.pos) <= 3){
					Buff.affect(mob, AttackUp.class).level(10);
				} else {
				Buff.affect(mob, Disarm.class, curUser.STR);
				Buff.affect(mob, Silent.class, curUser.STR);
				}
			}
		}
		charge += 15;
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.prolong(curUser, DefenceUp.class,targets.size()*5f).level(50);
	}

	@Override
	public void doSpecial3() {

		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof PET) {
				mob.HP += (int)mob.HT/2;
			}
		}
		Buff.affect(curUser,ShieldArmor.class).level(curUser.HT/2);
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			Char mob = Actor.findChar(curUser.pos
					+ Level.NEIGHBOURS8[i]);
			if (mob != null && mob != curUser && !(mob instanceof PET ) ) {
				mob.damage((int)curUser.HT/2, this);
			}

		}

		charge += 25;
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.prolong(curUser, DefenceUp.class,targets.size()*5f).level(50);
	}

	@Override
	public void doSpecial4() {
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.prolong(curUser, SpAttack.class,30f);
		charge += 8;
	}

	/*protected static CellSelector.Listener leaper = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer target) {
			if (target != null && target != curUser.pos) {

				int cell = Ballistica.cast(curUser.pos, target, false, true);
				if (Actor.findChar(cell) != null && cell != curUser.pos) {
					cell = Ballistica.trace[Ballistica.distance - 2];
				}

				curUser.HP -= (curUser.HP / 3);
				if (curUser.subClass == HeroSubClass.BERSERKER
						&& curUser.HP <= curUser.HT * Fury.LEVEL) {
					Buff.affect(curUser, Fury.class);
				}

				final int dest = cell;
				curUser.busy();
				curUser.sprite.jump(curUser.pos, cell, new Callback() {
					@Override
					public void call() {
						curUser.move(dest);
						Dungeon.level.press(dest, curUser);
						Dungeon.observe();

						for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
							Char mob = Actor.findChar(curUser.pos
									+ Level.NEIGHBOURS8[i]);
							if (mob != null && mob != curUser) {
								Buff.prolong(mob, EnchantmentEnergy.class, SHOCK_TIME);
							}
						}

						CellEmitter.center(dest).burst(
								Speck.factory(Speck.DUST), 10);
						Camera.main.shake(2, 0.5f);

						curUser.spendAndNext(LEAP_TIME);
					}
				});
			}
		}

		@Override
		public String prompt() {
			return "Choose direction to leap";
		}
	};*/
}