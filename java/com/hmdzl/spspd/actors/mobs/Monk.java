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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.food.staplefood.NormalRation;
import com.hmdzl.spspd.items.food.staplefood.OverpricedRation;
import com.hmdzl.spspd.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.MonkSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class Monk extends Mob {

	public static final String TXT_DISARM = "%s has knocked the %s from your hands!";

	{
		spriteClass = MonkSprite.class;

		HP = HT = 160+(adj(0)*Random.NormalIntRange(3, 5));
		evadeSkill = 30+adj(1);

		EXP = 14;
		maxLvl = 30;

		loot = new NormalRation();
		lootChance = 0.1f;
		
		lootOther = new OverpricedRation();
		lootChanceOther = 0.4f; // by default, see die()
		
		properties.add(Property.DWARF);
	}


	@Override
	public Item SupercreateLoot(){
		return new FightGloves();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(22, 36+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 30+adj(01);
	}

	@Override
	protected float attackDelay() {
		return 0.5f;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(2, 12);
	}

	@Override
	public void die(Object cause) {
		Imp.Quest.process(this);

		super.die(cause);
	}

	@Override
	public int attackProc(Char enemy, int damage) {

		if (Random.Int(12) == 0 && enemy == Dungeon.hero) {

			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;

			if (weapon != null && !(weapon instanceof Knuckles || weapon instanceof FightGloves)
					&& !weapon.cursed) {
				hero.belongings.weapon = null;
				Dungeon.quickslot.clearItem(weapon);
				weapon.updateQuickslot();
				Dungeon.depth.drop(weapon, hero.pos).sprite.drop();
				GLog.w(Messages.get(this, "disarm"));
			}
		}

		return damage;
	}

    {
		immunities.add(Amok.class);
		immunities.add(Terror.class);
	}
}
