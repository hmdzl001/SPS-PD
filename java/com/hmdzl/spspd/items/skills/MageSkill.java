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
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class MageSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "mage robe";
		image = ItemSpriteSheet.SKILL_MAGE;
	}

	@Override
	public void doSpecial() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Level.distance(curUser.pos, mob.pos) <= 10)) {

				int dmg = (int) (Dungeon.hero.lvl * (1 + 0.1 * Dungeon.hero.magicSkill())) ;
				switch (Random.Int(7)) {
					case 0:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.ENERGY_DAMAGE);
						if (mob.isAlive()) {
						Buff.affect(mob, MagicWeak.class,10f);
						}
						break;
					case 1:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.FIRE_DAMAGE);
						if (mob.isAlive()) {
						Buff.affect(mob, Burning.class).set(10f);
						}
						break;
					case 2:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.ICE_DAMAGE);
						if (mob.isAlive()) {
						Buff.affect(mob, FrostIce.class).level(10);
						}
						break;
					case 3:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.EARTH_DAMAGE);
						if (mob.isAlive()) {
						Buff.affect(mob, Ooze.class);
						}
						break;
					case 4:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.SHOCK_DAMAGE);
						if (mob.isAlive()) {
						Buff.affect(mob, Shocked.class).set(10f);
						}
						break;
					case 5:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.LIGHT_DAMAGE);
						if (mob.isAlive()) {
						Buff.affect(mob, LightShootAttack.class).set(10f);
						}
						break;
					case 6:
						mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.DARK_DAMAGE);
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

        for (int n : Level.NEIGHBOURS4OUT) {
            int c = curUser.pos + n;
            if (c >= 0 && c < Level.getLength()) {
                if ((Dungeon.level.map[c] == Terrain.WALL || Dungeon.level.map[c] == Terrain.GLASS_WALL || Dungeon.level.map[c] == Terrain.WALL_DECO)&& Level.insideMap(c)) {
                    Level.set(c, Terrain.EMBERS);
                    GameScene.updateMap(c);
                    Dungeon.observe();
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
		MageSkill.charge += 15;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}	

	@Override
	public void doSpecial3() {

		ScrollOfTeleportation.teleportHero(curUser);
		Dungeon.hero.lvl--;
		Dungeon.hero.TRUE_HT-=10;
		if (Dungeon.hero.TRUE_HT<0){
			Dungeon.hero.die(Messages.format(ResultDescriptions.LOSE));
			Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
		}
		Dungeon.hero.updateHT(true);

		MageSkill.charge += 15;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}

	@Override
	public void doSpecial4() {
		Dungeon.level.drop(Generator.random(Generator.Category.WAND).identify().reinforce().uncurse(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
		Buff.affect(curUser, Recharging.class, 30);
		MageSkill.charge += 20;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}


}