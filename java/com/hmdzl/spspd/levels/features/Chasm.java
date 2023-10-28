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
package com.hmdzl.spspd.levels.features;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Wound;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.PitfallTrap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.MobSprite;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class Chasm {

	public static boolean jumpConfirmed = false;

	public static void heroJump(final Hero hero) {
		GameScene.show(new WndOptions(Messages.get(Chasm.class, "chasm"),
						Messages.get(Chasm.class, "jump"),
						Messages.get(Chasm.class, "yes"),
						Messages.get(Chasm.class, "no") ) {
			@Override
			protected void onSelect(int index) {
				if (index == 0) {
					jumpConfirmed = true;
					hero.resume();
				}
			}
        });
	}

	
	
	public static void heroFall(int pos) {

		jumpConfirmed = false;

		Sample.INSTANCE.play(Assets.SND_FALLING);

		Buff buff = hero.buff(TimekeepersHourglass.timeFreeze.class);
		if (buff != null)
			buff.detach();
            int damage = Random.NormalIntRange(Dungeon.depth, Dungeon.depth*2);
			Buff.affect( hero, Bleeding.class).set(damage);
			Buff.affect( hero, Cripple.class,5f);
		    hero.damage(Random.IntRange(hero.HT/4, hero.HT/3), new Hero.Doom() {
			@Override
			public void onDeath() {
				Badges.validateDeathFromFalling();
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n("You fell to death...");
			}
		});
			Wound.hit( pos );
		if (hero.isAlive()) {
			hero.interrupt();
		} else {
			hero.sprite.visible = false;
			Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
		}
	}
	

	public static void heroLand() {

		Hero hero = Dungeon.hero;

		hero.sprite.burst(hero.sprite.blood(), 10);
		Camera.main.shake(4, 0.2f);

		Buff.prolong(hero, Cripple.class, Cripple.DURATION);
			
				
		hero.damage(Random.IntRange(hero.HT/3, hero.HT/2), new Hero.Doom() {
			@Override
			public void onDeath() {
				Badges.validateDeathFromFalling();
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n("You fell to death...");
			}
		});
	}

	public static void mobFall(Mob mob) {
		int pos = mob.pos;
		int damage = Random.NormalIntRange(Dungeon.depth, Dungeon.depth*2);
	    Buff.affect( mob, Bleeding.class).set(damage);
		Buff.affect( mob, Cripple.class,5f);
		Wound.hit( mob );
		mob.damage(mob.HT/5, Gold.class);
		Dungeon.level.setTrap( new PitfallTrap().hide(), mob.pos );
		Level.set( mob.pos, Terrain.SECRET_TRAP);
		GameScene.updateMap( mob.pos);
		((MobSprite) mob.sprite).fall();
	}
}
