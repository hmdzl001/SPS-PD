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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class RainShield extends MiscEquippable {

	{
		image = ItemSpriteSheet.RAIN_SHIELD;

	}
	
	@Override
	protected MiscBuff buff() {
		return new RainShieldBuff();
	}

	public class RainShieldBuff extends MiscBuff {
		
	    @Override
	    public boolean act() {
		
		if (target.HP > target.HT/10){
			target.HP = Math.max(target.HT/10, target.HP - 1);
		}
		spend(TICK);
		Buff.affect(target, ShieldArmor.class).level(Math.max(0,target.HT - target.HP));
		return true;
	    }
		
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public int price() {
		return 500 * quantity;
	}
}
