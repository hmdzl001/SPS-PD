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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class TreasureMap extends Item {
	
	public static final float TIME_TO_USE = 1;

	public static final String AC_PORT = "PORT";

	private int specialLevel = 43;
	private int returnDepth = -1;
	private int returnPos;

	{
		//name = "treasure map";
		image = ItemSpriteSheet.CHEIF_MAP;

		stackable = false;
		unique = true;
	}
	
	private static final String DEPTH = "depth";
	private static final String POS = "pos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DEPTH, returnDepth);
		if (returnDepth != -1) {
			bundle.put(POS, returnPos);
		}
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		returnDepth = bundle.getInt(DEPTH);
		returnPos = bundle.getInt(POS);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_PORT);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_PORT)) {
            PocketBallFull.removePet(hero);
			if ((Dungeon.bossLevel() || Dungeon.dungeondepth ==1 || Dungeon.dungeondepth >25 ) && Dungeon.dungeondepth !=specialLevel) {
				hero.spend(TIME_TO_USE);
				GLog.w(Messages.get(Item.class, "not_here"));
				return;
			}
			
			if (Dungeon.dungeondepth ==specialLevel && !Dungeon.gnollkingkilled && !Dungeon.depth.reset) {
				hero.spend(TIME_TO_USE);
				GLog.w(Messages.get(Item.class, "boss_first"));
				return;
			}
		}

		if (action.equals(AC_PORT)) {
                PocketBallFull.removePet(hero);
				Buff buff = Dungeon.hero
						.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

              if (Dungeon.dungeondepth <25 && !Dungeon.bossLevel()){
            	
            	returnDepth = Dungeon.dungeondepth;
       			returnPos = hero.pos;
				InterlevelScene.mode = InterlevelScene.Mode.PORTMAP;
			} else {
				InterlevelScene.mode = InterlevelScene.Mode.RETURN;	
				detach(hero.belongings.backpack);
			}
               	InterlevelScene.returnDepth = returnDepth;
				InterlevelScene.returnPos = returnPos;
				Game.switchScene(InterlevelScene.class);
					
		} else {

			super.execute(hero, action);

		}
	}
	

	private PET checkpet(){
		for (Mob mob : Dungeon.depth.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}
	
		
	public void reset() {
		returnDepth = -1;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}


	private static final Glowing WHITE = new Glowing(0xFFFFCC);
	

	@Override
	public Glowing glowing() {
		return WHITE;
	}

}
