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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.BugSlow;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.BugMeat;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.SpiderBotSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class SpiderBot extends Mob {

	{
		spriteClass = SpiderBotSprite.class;

		HP = HT = 150+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 25+adj(1);

		EXP = 12;
		maxLvl = 30;

		loot = new Meat();
		lootChance = 0.3f;

		//FLEEING = new Fleeing();
		
		properties.add(Property.BEAST);
	}

	@Override
	public Item SupercreateLoot(){
		return new BugMeat();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15+adj(0), 40+adj(1));
	}

	@Override
	public int hitSkill(Char target) {
		return  30+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 20);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if( !skilluse ){
			spanbug(this.pos);
			skilluse = true;
		}
		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		Buff.affect(enemy,BugSlow.class);
		return super.defenseProc(enemy, damage);
	}

	@Override
	public void die(Object cause) {

		super.die(cause);
		BugMeat bugfood = new BugMeat();

		//Dungeon.level.drop( bugfood, pos ).sprite.drop();

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Level.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				Buff.affect(ch,BugSlow.class);
			}
			if (ch instanceof Hero){
				if (!bugfood.collect(Dungeon.hero.belongings.backpack)) {
					Dungeon.level.drop( bugfood, Dungeon.hero.pos ).sprite.drop();
				} else {
					GLog.n(Messages.get(this, "yell"));
				}
			}
		}

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_BONES);
		}
	}


	{
		resistances.add(Bleeding.class);
		immunities.add(Roots.class);
	}

	private void spanbug(int pos) {
		Char ch = Dungeon.hero;
		BugMeat bugfood = new BugMeat();
		int dist = Level.distance(pos, Dungeon.hero.pos);
		if (dist < 2) {
			if (!bugfood.collect(Dungeon.hero.belongings.backpack)) {
				Dungeon.level.drop( bugfood, ch.pos ).sprite.drop();
			} else {
				GLog.n(Messages.get(this, "yell"));
			}
		} else Dungeon.level.drop( bugfood, pos ).sprite.drop();
	}


	//private class Fleeing extends Mob.Fleeing {
	//	@Override
	//	protected void nowhereToRun() {
	//		if (buff(Terror.class) == null) {
	//			state = HUNTING;
	//		} else {
	//			super.nowhereToRun();
	//		}
	//	}
//	}
}