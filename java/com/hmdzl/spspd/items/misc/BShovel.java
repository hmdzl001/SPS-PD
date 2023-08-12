/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BShovel extends Item {

	public static final String AC_USE = "USE";

	private static final float TIME_TO_DIG = 1f;

	{
		//name = "Shovel";
		image = ItemSpriteSheet.BSHOVEL;
		defaultAction = AC_USE;
		unique = true;
		 
	}
	
	public final int fullCharge = 150;
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
	public int price() {
		return 30 * quantity;
	}	
	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (charge >= 65){
		actions.add(AC_USE);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {		
      if( action.equals( AC_USE ) ){
      	if(charge < 65){
		  GLog.i(Messages.get(BShovel.class, "break"));
		  return;
      	} else {
			for (int n : Level.NEIGHBOURS4) {
				int c = hero.pos + n;

				if (c >= 0 && c < Level.getLength()) {
					if ((Dungeon.level.map[c] == Terrain.WALL || Dungeon.level.map[c] == Terrain.GLASS_WALL) && Level.insideMap(c)) {
						Level.set(c, Terrain.DOOR);
						GameScene.updateMap(c);
						Dungeon.observe();
					}
				}
			}
			hero.spend(TIME_TO_DIG);
			hero.busy();
					Hunger hunger = hero.buff(Hunger.class);
				if (hunger != null && !hunger.isStarving()) {
						hunger.satisfy(-10);
						BuffIndicator.refreshHero();
				}
				charge-=65;

		switch (Random.Int (6)) {
			case 0 :
			Buff.affect(hero,MindVision.class,5f);
			GLog.p(Messages.get(BShovel.class,"mob"));
			break;
			case 1 :
			Buff.affect(hero,HasteBuff.class,5f);
			GLog.p(Messages.get(BShovel.class,"haste"));
			break;
			case 2 :
			Buff.affect(hero,Awareness.class,5f);
			GLog.p(Messages.get(BShovel.class,"item"));
			break;
			case 3 :
            Dungeon.gold+=Dungeon.hero.lvl*10;
			GLog.p(Messages.get(BShovel.class,"gold"));
			break;
			case 4 :
			Dungeon.hero.HP=Dungeon.hero.HT;
			GLog.p(Messages.get(BShovel.class,"heal"));
			break;
			case 5 :
            Buff.affect(hero,MechArmor.class).level(30);
			GLog.p(Messages.get(BShovel.class,"mech"));
			break;
			default:
				break;
		}
				updateQuickslot();
				hero.onOperateComplete();
 
	  			return;
            }
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public String status() {
		return Messages.format("%d", charge /65);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(BShovel.class, "charge",charge,fullCharge);
		return info;	
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
