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
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Disarm;
import com.hmdzl.spspd.actors.buffs.GoldTouch;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HighAttack;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Strength;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class RogueSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "rogue garb";
		image = ItemSpriteSheet.ARMOR_ROGUE;
	}

	@Override
	public void doSpecial() {
		//GameScene.selectCell(teleporter);
		RogueSkill.charge += 15;
		
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.affect(curUser, Invisibility.class,20);
		
		switch (Random.Int(3)){
			case 0:
		    Buff.affect(curUser, Strength.class);
			break;
			case 1:
			Buff.affect(curUser, HasteBuff.class,15);
			break;
			case 2:
            Buff.affect(curUser, AttackUp.class,15).level(50);
			break;
			case 3:
			break;
		}
		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Level.distance(curUser.pos, mob.pos) <= 10)) {
				Buff.affect(mob, Silent.class,9999f);
				Buff.affect(mob, Disarm.class,5f);
				Buff.affect(mob, ArmorBreak.class, 10f).level(50);
				Buff.prolong(mob, Blindness.class, 3f);
			}
		}

	}

	@Override
	public void doSpecial2() {
		RogueSkill.charge += 20;
		
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.affect(curUser, GoldTouch.class,15f);
	}

	@Override
	public void doSpecial3() {

		Dungeon.level.drop(Generator.random(Generator.Category.RING).identify().uncurse().reinforce().upgrade(3), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);

		RogueSkill.charge += 20;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	@Override
	public void doSpecial4() {
		RogueSkill.charge += 20;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.affect(curUser, HighAttack.class);
	}

	/*protected static CellSelector.Listener teleporter = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer target) {
			if (target != null) {

				if (!Level.fieldOfView[target]
						|| !(Level.passable[target] || Level.avoid[target])
						|| Actor.findChar(target) != null) {

					GLog.w(TXT_FOV);
					return;
				}

				curUser.HP -= (curUser.HP / 5);

				ScrollOfTeleportation.appear(curUser, target);
				CellEmitter.get(target).burst(Speck.factory(Speck.WOOL), 10);
				Sample.INSTANCE.play(Assets.SND_PUFF);
				Dungeon.level.press(target, curUser);
				Dungeon.observe();

				curUser.spendAndNext(SKILL_TIME);
			}
		}

		@Override
		public String prompt() {
			return "Choose a location to jump to";
		}
	};*/
}