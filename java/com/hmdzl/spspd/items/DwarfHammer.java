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

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class DwarfHammer extends Item {

	public static final float TIME_TO_USE = 1;

	public static final String AC_BREAK = "BREAK";

		{
		//name = "dwarf demon hammer";
		image = ItemSpriteSheet.DWARFHAMMER;
		unique = true;

		}

	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_BREAK);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action == AC_BREAK) {

			if (Dungeon.bossLevel() || Dungeon.depth > 25 || Dungeon.depth < 21) {
				hero.spend(DwarfHammer.TIME_TO_USE);
				GLog.w(Messages.get(this, "prevent"));
				return;
			}

			if (!Dungeon.visible[Dungeon.level.exit]) {
				hero.spend(DwarfHammer.TIME_TO_USE);
				GLog.w(Messages.get(this, "prevent"));
				return;
			}


		}

		if (action == AC_BREAK) {
           
		  if (hero.pos != Dungeon.level.exit)	{
			  detach(Dungeon.hero.belongings.backpack);			  
			  		 
			  Dungeon.level.sealedlevel=false;
			  
			  //Dungeon.level.map[Dungeon.level.exit]=Terrain.EMPTY;
			  //GameScene.updateMap(Dungeon.level.exit);
			  //Dungeon.observe();

			  Dungeon.level.map[Dungeon.level.exit]=Terrain.EXIT;
			  GameScene.updateMap(Dungeon.level.exit);
			  Dungeon.observe();
			  
			  GLog.w(Messages.get(this,"unseal"));  	
				  
		  }
			
		} else {
			GLog.w(Messages.get(this, "prevent"));
			super.execute(hero, action);
		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
}
