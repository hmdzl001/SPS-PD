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

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Disarm;
import com.hmdzl.spspd.change.actors.buffs.GoldTouch;
import com.hmdzl.spspd.change.actors.buffs.HighAttack;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.wands.WandOfBlood;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
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
		RogueSkill.charge += 10;
		
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
			Buff.affect(curUser, Haste.class,15);
			break;
			case 2:
            Buff.affect(curUser, AttackUp.class,15).level(50);
			break;
			case 3:
			break;
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

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Dungeon.level.distance(curUser.pos, mob.pos) <= 10)) {
				Buff.affect(mob, Silent.class,9999f);
				Buff.affect(mob, Disarm.class,5f);
				Buff.affect(mob, ArmorBreak.class, 10f).level(50);
				Buff.prolong(mob, Blindness.class, 3f);
			}
		}

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