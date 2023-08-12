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

import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.SkillRecharge;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class CursePhone extends MiscEquippable {

	{
		//name = "CursePhone";
		image = ItemSpriteSheet.CURSE_PHONE;
		
		cursed = true;

		
	}
	
	@Override
	protected MiscBuff buff() {
		return new CurseTell();
	}

	public class CurseTell extends MiscBuff {
		@Override
		public boolean act() {
			if(cursed && Random.Int(10) == 0){
				Buff.prolong( target, Terror.class, 10f).object = target.id();
				Buff.prolong( target, Vertigo.class, 10f);
				Buff.affect(target,ArmorBreak.class,10f).level(30);
				Buff.prolong(target,Arcane.class,10f);
				Buff.prolong(target,Arcane.class,10f);
				Buff.prolong(target,SkillRecharge.class,10f);
			}	
		    spend( TICK );
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
		return 300 * quantity;
	}	
}
