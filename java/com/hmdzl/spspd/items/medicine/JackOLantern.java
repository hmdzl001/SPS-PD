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
package com.hmdzl.spspd.items.medicine;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DBurning;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class JackOLantern extends Pill {

	{
		//name = "jack o lantern mushroom";
		image = ItemSpriteSheet.MUSHROOM_LANTERN;
		 
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_EAT)) {
			
			if (Dungeon.bossLevel()){
				GLog.w(Messages.get(Food.class,"bosslevel"));
				return;
			}

		}
		
	   if (action.equals(AC_EAT)) {
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					GameScene.add(Blob.seed(mob.pos, 3, Fire.class));
					Buff.affect(mob,DBurning.class).set(8f);
				}
		}
	   
	   super.execute(hero, action);
	}	

	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public JackOLantern() {
		this(1);
	}

	public JackOLantern(int value) {
		this.quantity = value;
	}
}
