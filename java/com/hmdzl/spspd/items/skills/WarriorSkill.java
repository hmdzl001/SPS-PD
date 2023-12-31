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
package com.hmdzl.spspd.items.skills;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.BloodImbue;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Disarm;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.HTimprove;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.Muscle;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.SpAttack;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashMap;


public class WarriorSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "warrior suit of armor";
		image = ItemSpriteSheet.SKILL_WARRIOR;
	}

	private HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();

	@Override
	public void doSpecial() {
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
        Buff.affect(curUser,Muscle.class,160f);

		if (Dungeon.hero.lvl > 55) {
			Dungeon.depth.drop(Generator.random(Generator.Category.MELEEWEAPON).upgrade(5).uncurse().identify().reinforce(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.depth.drop(Generator.random(Generator.Category.ARMOR).upgrade(5).uncurse().identify().reinforce(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
		} else {
			if (Random.Int(2) == 0) {
				Dungeon.depth.drop(Generator.random(Generator.Category.MELEEWEAPON).upgrade(5).uncurse().identify(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			} else {
				Dungeon.depth.drop(Generator.random(Generator.Category.ARMOR).upgrade(5).uncurse().identify(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			}
		}
		WarriorSkill.charge += 20;
	}

	@Override
	public void doSpecial2() {

		for (Mob mob : Dungeon.depth.mobs) {
			if (Floor.fieldOfView[mob.pos]) {
				if (Floor.distance(curUser.pos, mob.pos) <= 3){
					Buff.affect(mob, AttackDown.class,20f).level(10);
				} else {
				Buff.affect(mob, Disarm.class, curUser.STR);
				Buff.affect(mob, Silent.class, curUser.STR);
				}
			}
		}
        if (Dungeon.hero.lvl > 55) {
			Buff.affect(curUser, AttackUp.class, 80f).level(75);
			Buff.affect(curUser, DefenceUp.class, 80f).level(75);
		}
		WarriorSkill.charge += 15;
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.prolong(curUser, DefenceUp.class,targets.size()*5f).level(50);
	}

	@Override
	public void doSpecial3() {

		for (Mob mob : Dungeon.depth.mobs) {
			if (mob instanceof PET) {
				mob.HP += mob.HT /2;
			}
		}
		Buff.affect(curUser,ShieldArmor.class).level(curUser.HT/2);
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			Char mob = Actor.findChar(curUser.pos
					+ Floor.NEIGHBOURS8[i]);
			if (mob != null && mob != curUser && !(mob instanceof PET ) ) {
				mob.damage(curUser.HT /2, Hero.class);
			}

		}
		Buff.detach(curUser, Poison.class);
		Buff.detach(curUser, Cripple.class);
		Buff.detach(curUser, STRdown.class);
		Buff.detach(curUser, BeOld.class);
		if (Dungeon.hero.lvl > 55) {
			Buff.affect(curUser, MagicArmor.class).level(curUser.HT/2);
			Buff.affect(curUser, EnergyArmor.class).level(curUser.HT/2);
		}


		WarriorSkill.charge += 25;
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
		Buff.prolong(curUser, BloodImbue.class,50f);
		Buff.prolong(curUser, SpAttack.class,50f);
		if (Dungeon.hero.lvl > 55) {
			Dungeon.hero.TRUE_HT ++;
			Buff.affect(curUser, HTimprove.class,50f);
			Dungeon.hero.updateHT(true);
		}

		WarriorSkill.charge += 10;
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