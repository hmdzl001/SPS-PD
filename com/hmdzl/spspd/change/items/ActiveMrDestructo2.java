/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.change.items;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.MrDestructo2dot0;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Shieldblock;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.watabou.noosa.audio.Sample;

public class ActiveMrDestructo2 extends Item {

	{
		//name = "mr destructo 2.0";
		image = ItemSpriteSheet.ACTIVEMRD2;
		defaultAction = AC_ACTIVATETHROW;
		stackable = true;
	}

	
	private static boolean activate = false;

	private static final String AC_ACTIVATETHROW = "ACTIVATETHROW";
	public final int fullCharge = 250;
	public int charge = 0;
	private static final String CHARGE = "charge";
		
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}	

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (charge >= 250){
		actions.add(AC_ACTIVATETHROW);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_ACTIVATETHROW)) {
		    if (charge < 250)
				GLog.i(Messages.get(this, "rest"));
            else {			
			activate = true;
			action = AC_THROW;}
		} else
			activate = false;

		super.execute(hero, action);
	}

	@Override
	protected void onThrow(int cell) {
		
		if (Actor.findChar(cell) != null) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int i : Level.NEIGHBOURS8)
				if (Level.passable[cell + i])
					candidates.add(cell + i);
			int newCell = candidates.isEmpty() ? cell : Random
					.element(candidates);
			
			   if (!Level.pit[newCell] && activate) {
				   MrDestructo2dot0.spawnAt(newCell);
			   } else {
			   Dungeon.level.drop(this, newCell).sprite.drop(cell);
			   }
			   
		} else if (!Level.pit[cell] && activate) {
				MrDestructo2dot0.spawnAt(cell);
		} else {
			
			super.onThrow(cell);
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

	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(this,"charge",charge,fullCharge);
		return info;
	}	
	
}
