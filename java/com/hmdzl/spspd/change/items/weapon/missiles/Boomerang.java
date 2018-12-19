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
package com.hmdzl.spspd.change.items.weapon.missiles;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.MissileSprite;
import java.util.ArrayList;

public class Boomerang extends MissileWeapon {

	{
		//name = "boomerang";
		image = ItemSpriteSheet.BOOMERANG;

		STR = 10;

		MIN = 3;
		MAX = 6;

		stackable = false;
		unique = true;

		bones = false;
	}

	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		if (!isEquipped(hero)) actions.add(AC_EQUIP);
		return actions;
	}	

	@Override
	public Item upgrade(boolean enchant) {
		
		MIN += 1;
		MAX += 2;
		super.upgrade(enchant);

		updateQuickslot();

		return this;
	}

	@Override
	public Item degrade() {

		return super.degrade();
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		super.proc(attacker, defender, damage);
		if (attacker instanceof Hero && ((Hero) attacker).rangedWeapon == this) {
			circleBack(defender.pos, (Hero) attacker);
		}
	}

	@Override
	protected void miss(int cell) {
		circleBack(cell, curUser);
	}

	private void circleBack(int from, Hero owner) {

		((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class))
				.reset(from, curUser.pos, curItem, null);

		if (throwEquiped) {
			owner.belongings.weapon = this;
			owner.spend(-TIME_TO_EQUIP);
			Dungeon.quickslot.replaceSimilar(this);
			updateQuickslot();
		} else if (!collect(curUser.belongings.backpack)) {
			Dungeon.level.drop(this, owner.pos).sprite.drop();
		}
	}

	private boolean throwEquiped;

	@Override
	public void cast(Hero user, int dst) {
		throwEquiped = isEquipped(user);
		super.cast(user, dst);
	}

	@Override
	public String desc() {
		String info = super.desc();

		if(reinforced){
			info += "\n\n" + Messages.get(Item.class, "reinforced");;
		}		
		
		return info;
	}
}
