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
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.CountDown;
import com.hmdzl.spspd.change.actors.buffs.Disarm;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.buffs.Shocked;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.effects.particles.EnergyParticle;
import com.hmdzl.spspd.change.effects.particles.SmokeParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.QuickSlotButton;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class MageSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "mage robe";
		image = ItemSpriteSheet.ARMOR_MAGE;
	}

	@Override
	public void doSpecial() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Dungeon.level.distance(curUser.pos, mob.pos) <= 10)) {
				Buff.affect(mob, Burning.class).reignite(mob);
				Buff.affect(mob, Ooze.class);
				Buff.affect(mob, Slow.class, 8);
				Buff.prolong(mob, Roots.class, 3);
			}
		}

		MageSkill.charge += 15;
		
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}
	
	@Override
	public void doSpecial2() {
		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Dungeon.level.distance(curUser.pos, mob.pos) <= 10)) {
				Buff.affect(mob, CountDown.class);
				for (int n : Level.NEIGHBOURS4) {
					int c = mob.pos + n;
					if (c >= 0 && c < Level.getLength()) {
						if (Dungeon.visible[c]) {
							CellEmitter.get(c).burst(SmokeParticle.FACTORY, 2);
						}
						if (Dungeon.level.map[c] == Terrain.WALL && Level.insideMap(c)) {
							Level.set(c, Terrain.EMPTY);
							GameScene.updateMap(c);
							Dungeon.observe();
						}
					}
				}
			}
		}

		MageSkill.charge += 15;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}	

	@Override
	public void doSpecial3() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Dungeon.level.distance(curUser.pos, mob.pos) <= 10)) {
				int dmg = (int) (Dungeon.hero.lvl * (1 + 0.1 * Dungeon.hero.magicSkill())) ;
                mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), this );
				Buff.prolong(mob, Frost.class, 8);
			}
		}

		MageSkill.charge += 15;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}

	@Override
	public void doSpecial4() {
		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Dungeon.level.distance(curUser.pos, mob.pos) <= 4)) {
				Buff.prolong(mob, Shocked.class, 8);
			}
		}

		Buff.affect(curUser, Recharging.class, 30);
		MageSkill.charge += 9;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}


}