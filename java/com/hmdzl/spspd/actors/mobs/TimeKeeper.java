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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.WatchOut;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.medicine.Timepill;
import com.hmdzl.spspd.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.TimeKeeperSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

public class TimeKeeper extends Mob {

	{
		spriteClass = TimeKeeperSprite.class;
		
		HP = HT = 80+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 15+adj(0);

		EXP = 10;
		maxLvl = 25;

		loot = Generator.Category.SCROLL;
		lootChance = 0.15f;
		
		properties.add(Property.UNKNOW);
		properties.add(Property.MAGICER);
	}

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new Timepill(),new PotionOfMindVision(), new TimekeepersHourglass());
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(14, 20+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 16+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 4);
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		
		enemy.damage(damageRoll(), ENERGY_DAMAGE);
		damage = 0;

		if (enemy.isAlive() && Random.Int(5)==1) {
			if (enemy instanceof Hero) {
				ScrollOfTeleportation.teleportHero((Hero) enemy);
				((Hero) enemy).curAction = null;
			} else {
				int count = 10;
				int pos;
				do {
					pos = Dungeon.level.randomRespawnCell();
					if (count-- <= 0) {
						break;
					}
				} while (pos == -1);

				enemy.pos = pos;
				enemy.sprite.place(enemy.pos);
				enemy.sprite.visible = Dungeon.visible[pos];

			}
		}
		return damage;
	}	

	@Override
	public void damage(int dmg, Object src) {
		if (HT/2 > HP - dmg  && HP > HT/2){
			dmg = HP-(int)(HT/2)+1;
			Buff.affect(this,WatchOut.class);
			int newPos;
			do {
				newPos = Random.Int(Level.getLength());
			} while (!Level.passable[newPos]
					|| Level.adjacent(newPos, Dungeon.hero.pos)
					|| Actor.findChar(newPos) != null);

			sprite.move(pos, newPos);
			move(newPos);
			yell( Messages.get(this, "yell") );
			if (Dungeon.visible[newPos]) {
				CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
				Sample.INSTANCE.play(Assets.SND_PUFF);
			}

			spend(1 / speed());

		}
		super.damage(dmg, src);
	}

}
