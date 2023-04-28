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
package com.hmdzl.spspd.items.scrolls;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ForeverShadow;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ScarecrowSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class ScrollOfDummy extends Scroll {


	{
		//name = "Scroll of Mirror Image";
		consumedValue = 5;
		initials = 15;
	}

	@Override
	public void doRead() {

		ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			int p = curUser.pos + Level.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null
					&& (Level.passable[p] || Level.avoid[p])) {
				respawnPoints.add(p);
			}
		}

		if ( respawnPoints.size() > 0) {
			int index = Random.index(respawnPoints);
			MiniDummy mob = new MiniDummy();
			GameScene.add(mob);
			mob.HP = 30;
			ScrollOfTeleportation.appear(mob, respawnPoints.get(index));
		}
		Buff.affect(hero, Invisibility.class, 5f);
		setKnown();
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();
		readAnimation();
	}
	
	@Override
	public void empoweredRead() {

        ArrayList<Integer> respawnPoints = new ArrayList<Integer>();
        for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
            int p = curUser.pos + Level.NEIGHBOURS8[i];
            if (Actor.findChar(p) == null
                    && (Level.passable[p] || Level.avoid[p])) {
                respawnPoints.add(p);
            }
        }

        if ( respawnPoints.size() > 0) {
            int index = Random.index(respawnPoints);
            MiniDummy mob = new MiniDummy();
            GameScene.add(mob);
            mob.HP = 50;
            ScrollOfTeleportation.appear(mob, respawnPoints.get(index));
        }

		setKnown();
		Buff.affect(hero, ForeverShadow.class, 5f);
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();

		curUser.spendAndNext(TIME_TO_READ);
	}

	public static class MiniDummy extends Mob {

		{
			spriteClass = ScarecrowSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT = 40;
			evadeSkill = 0;
			ally=true;
			properties.add(Property.UNKNOW);
		}

		@Override
		protected boolean act() {
			damage(1, this);
			return super.act();
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		protected boolean getCloser(int target) {
			return true;
		}

		@Override
		protected boolean getFurther(int target) {
			return true;
		}

		@Override
		protected Char chooseEnemy() {
			return null;
		}

		@Override
		public int defenseProc(Char enemy, int damage) {
			this.HP++;
			return super.defenseProc(enemy, damage);
		}

		@Override
		public void damage( int dmg, Object src ) {

			if(dmg > 0){
				dmg = 2;
			}

			super.damage(dmg, src);
		}

	}

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}	
}
