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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GrassBook extends Item {

    public static final String AC_READ = "READ";

	public static final String AC_READ2 = "READ2";
    

	{
		//name = "GrassBook";
		image = ItemSpriteSheet.POWERTRIAL;

		unique = true;
        charge = 0;
		//defaultAction = AC_READ;
	}

	public static int charge;
	private static final String CHARGE = "charge";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.gold > 500){
		actions.add(AC_READ);
		actions.add(AC_READ2);
		}
		
		return actions;
	}
	
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
	public void execute(Hero hero, String action) {

		if (action.equals(AC_READ)) {
               Dungeon.gold -= 500;
			   hero.spend(1f);
			   hero.busy();
			   hero.sprite.operate(hero.pos);		
			if (Random.Int(5) == 1) { 
			
			   Dungeon.level.drop(Generator.random(Generator.Category.MUSHROOM), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				
			} else if (Random.Int(4) == 1) {
			
			  Dungeon.level.drop(Generator.random(Generator.Category.BERRY), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			
			} else if (Random.Int(3) == 1) {
			
			  Dungeon.level.drop(new ScrollOfRegrowth(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			
			} else {
			
			  Dungeon.level.drop(Generator.random(Generator.Category.SEED), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			
			}

		}

		if (action.equals(AC_READ2)) {
	           Dungeon.gold -= 500;
			   hero.spend(1f);
			   hero.busy();
			   hero.sprite.operate(hero.pos);
			Buff.affect(hero,Levitation.class,30f);
			Buff.affect(hero,ShieldArmor.class).level(hero.lvl+10);
			for (int n : Level.NEIGHBOURS8) {
				int c = hero.pos + n;
				if (c >= 0 && c < Level.getLength()) {
					if ((Dungeon.level.map[c] == Terrain.EMPTY ||
					Dungeon.level.map[c] == Terrain.EMPTY_DECO ||
					Dungeon.level.map[c] == Terrain.EMPTY_SP ||
					Dungeon.level.map[c] == Terrain.GRASS )&& Level.insideMap(c)) {
						Level.set(c, Terrain.OLD_HIGH_GRASS);
						GameScene.updateMap(c);
						Dungeon.observe();
					}
				}
			}

		
		} else {
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

	@Override
	public int price() {
		return 50 * quantity;
	}

}
