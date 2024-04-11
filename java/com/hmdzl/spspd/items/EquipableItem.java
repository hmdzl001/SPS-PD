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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;

public abstract class EquipableItem extends Item {

	public static final String AC_EQUIP = "EQUIP";
	public static final String AC_UNEQUIP = "UNEQUIP";

	public static final String AC_UNEQUIP_SECOND = "UNEQUIPSECOND";

	{

	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EQUIP)) {
			// In addition to equipping itself, item reassigns itself to the
			// quickslot
			// This is a special case as the item is being removed from
			// inventory, but is staying with the hero.
			int slot = Dungeon.quickslot.getSlot(this);
			doEquip(hero);
			if (slot != -1) {
				Dungeon.quickslot.setSlot(slot, this);
				updateQuickslot();
			}
		} else if (action.equals(AC_UNEQUIP)) {
			doUnequip(hero, true);
		} else if (action.equals(AC_UNEQUIP_SECOND)) {
			doUnequipSecond(hero, true);
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public void doDrop(Hero hero) {
		if (!isEquipped(hero) || ( !isEquippedSecond(hero) && doUnequip(hero, false, false)) ||
				doUnequipSecond(hero, false, false)
				) {
			super.doDrop(hero);
		}
	}

	@Override
	public void cast(final Hero user, int dst) {

		if(isEquippedSecond(user)){
			if (quantity == 1 && !this.doUnequipSecond(user, false, false)) {
				return;
			}
		} else if (isEquipped(user)) {
			if (quantity == 1 && !this.doUnequip(user, false, false)) {
				return;
			}
		}

		super.cast(user, dst);
	}

	public static void equipCursed(Hero hero) {
		hero.sprite.emitter().burst(ShadowParticle.CURSE, 6);
		Sample.INSTANCE.play(Assets.SND_CURSED);
	}

	protected float time2equip(Hero hero) {
		return 1;
	}

	public abstract boolean doEquip(Hero hero);

	public boolean doUnequip(Hero hero, boolean collect, boolean single) {

		if (cursed) {
			GLog.w(Messages.get(this,"unequip_cursed", name()));
			return false;
		}

		if (single) {
			hero.spendAndNext(time2equip(hero));
		} else {
			hero.spend(time2equip(hero));
		}

		if (Dungeon.hero.heroClass == HeroClass.WARRIOR && Hero.skins == 4){
			if(!this.isunique()){
				Dungeon.hero.spp += 5;
				Dungeon.hero.spp += this.level;
				collect = false;
			}
		}

		if (collect && !collect(hero.belongings.backpack)) {
			Dungeon.depth.drop(this, hero.pos);
		}

		return true;
	}

	final public boolean doUnequip(Hero hero, boolean collect) {
		return doUnequip(hero, collect, true);
	}

	public boolean doUnequipSecond(Hero hero, boolean collect, boolean single) {

		if (cursed) {
			GLog.w(Messages.get(this,"unequip_cursed", name()));
			return false;
		}

		if (single) {
			hero.spendAndNext(time2equip(hero));
		} else {
			hero.spend(time2equip(hero));
		}

		if (Dungeon.hero.heroClass == HeroClass.WARRIOR && Hero.skins == 4){
			if(!this.isunique()){
				Dungeon.hero.spp += 5;
				Dungeon.hero.spp += this.level;
				collect = false;
			}
		}

		if (collect && !collect(hero.belongings.backpack)) {
			Dungeon.depth.drop(this, hero.pos);
		}

		return true;
	}

	final public boolean doUnequipSecond(Hero hero, boolean collect) {
		return doUnequipSecond(hero, collect, true);
	}

}
