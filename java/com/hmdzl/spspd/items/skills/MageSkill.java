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
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Feed;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.actors.buffs.HTimprove;
import com.hmdzl.spspd.actors.buffs.LightShootAttack;
import com.hmdzl.spspd.actors.buffs.MagicWeak;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.ShadowCurse;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class MageSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "mage robe";
		image = ItemSpriteSheet.SKILL_MAGE;
	}

	@Override
	public void doSpecial() {

		for (Mob mob : Dungeon.depth.mobs) {
			if (Floor.fieldOfView[mob.pos] && (Floor.distance(hero.pos, mob.pos) <= 10)) {
				int dmg = (int) (hero.lvl * (1 + 0.1 * hero.magicSkill())) ;
				switch (Random.Int(7)) {
					case 0:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.ENERGY_DAMAGE,2);
						if (mob.isAlive()) {
						Buff.affect(mob, MagicWeak.class,10f);
						}
						break;
					case 1:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.FIRE_DAMAGE,2);
						if (mob.isAlive()) {
						Buff.affect(mob, Burning.class).set(10f);
						}
						break;
					case 2:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.ICE_DAMAGE,2);
						if (mob.isAlive()) {
						Buff.affect(mob, FrostIce.class).level(10);
						}
						break;
					case 3:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.EARTH_DAMAGE,2);
						if (mob.isAlive()) {
						Buff.affect(mob, Ooze.class).set(10f);
						}
						break;
					case 4:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.SHOCK_DAMAGE,2);
						if (mob.isAlive()) {
						Buff.affect(mob, Shocked.class).set(10f);
						}
						break;
					case 5:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.LIGHT_DAMAGE,2);
						if (mob.isAlive()) {
						Buff.affect(mob, LightShootAttack.class).set(10f);
						}
						break;
					case 6:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.DARK_DAMAGE,2);
						if (mob.isAlive()) {
						Buff.affect(mob, ShadowCurse.class);
						}
						break;
					default:
						GLog.i("nothing happened");
						break;
				}

			}
		}

		if (hero.lvl > 55) {
			for (int n : Floor.NEIGHBOURS4OUT) {
				int c = curUser.pos + n;
				if (c >= 0 && c < Floor.getLength()) {
					if ((Dungeon.depth.map[c] == Terrain.WALL || Dungeon.depth.map[c] == Terrain.GLASS_WALL || Dungeon.depth.map[c] == Terrain.WALL_DECO) && Floor.insideMap(c)) {
						Floor.set(c, Terrain.EMBERS);
						GameScene.updateMap(c);
						Dungeon.observe();
					}
				}
			}
		}


        MageSkill.charge += 20;
		
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}
	
	@Override
	public void doSpecial2() {

		Buff.affect(curUser, Feed.class, 50f);
		Buff.affect(curUser, HTimprove.class, 100f);

		if (hero.lvl < 56) {
			MageSkill.charge += 20;
		} else {
			MageSkill.charge += 10;
		}
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}	

	@Override
	public void doSpecial3() {

		ScrollOfTeleportation.teleportHero(curUser);
		hero.lvl--;
		if (hero.lvl < 56) {
			hero.TRUE_HT -= 10;
		}
		if (hero.TRUE_HT<0){
			hero.die(Messages.format(ResultDescriptions.LOSE));
			Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
		}
		hero.updateHT(true);

		MageSkill.charge += 15;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}

	@Override
	public void doSpecial4() {
		if (hero.lvl < 56) {
			Dungeon.depth.drop(Generator.random(Generator.Category.WAND).upgrade(5).identify().uncurse(), hero.pos).sprite.drop(hero.pos);
		} else {
			Dungeon.depth.drop(Generator.random(Generator.Category.WAND).upgrade(5).identify().reinforce().uncurse(), hero.pos).sprite.drop(hero.pos);
		}
		Buff.affect(curUser, Recharging.class, 30);
		MageSkill.charge += 20;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}


}