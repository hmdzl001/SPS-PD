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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.WarGroove;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class Shovel extends Item {

	public static final String AC_USE = "USE";
	public static final String AC_BUILD = "BUILD";

	private static final float TIME_TO_DIG = 1f;

	{
		//name = "Shovel";
		image = ItemSpriteSheet.SHOVEL;
		defaultAction = AC_USE;
		unique = true;
		 
	}
	
	public final int fullCharge = 120;
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
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (charge >= 40){
		actions.add(AC_USE);
		}
		if (charge >= 100){
			actions.add(AC_BUILD);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {		
      if( action.equals( AC_USE ) ){
			for (int i = 0; i < Level.NEIGHBOURS4.length; i++) {

				final int pos = hero.pos + Level.NEIGHBOURS4[i];
				if(charge < 40){
					GLog.i(Messages.get(Shovel.class, "break"));
					return;
				} else if ((Dungeon.level.map[pos] == Terrain.WALL || Dungeon.level.map[pos] == Terrain.GLASS_WALL)&& Level.insideMap(pos)) {
					hero.spend(TIME_TO_DIG);
					hero.busy();
					hero.sprite.attack(pos, new Callback() {
						@Override
						public void call() {
							CellEmitter.center(pos).burst(
								Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);
							Level.set(pos, Terrain.EMBERS);
							GameScene.updateMap(pos);

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}
							Buff.affect(hero,WarGroove.class);
							charge-=50;
							updateQuickslot();
							hero.onOperateComplete();
						}
					});
	  			return;
				}
			}
		} else if ( action.equals( AC_BUILD )){
			for (int n : Level.NEIGHBOURS4) {
				int c = hero.pos + n;
				if (c >= 0 && c < Level.getLength()) {
					if ((Dungeon.level.map[c] == Terrain.EMPTY ||
					Dungeon.level.map[c] == Terrain.EMPTY_DECO ||
					Dungeon.level.map[c] == Terrain.EMPTY_SP ||
					Dungeon.level.map[c] == Terrain.GRASS )&& Level.insideMap(c)) {
						Level.set(c, Terrain.WALL);
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
			charge-=100;
		  updateQuickslot();
		  hero.onOperateComplete();
		  return;
		} else {
			super.execute(hero, action);
			
		}
	}

	@Override
	public String status() {
		return Messages.format("%d", charge /40);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(Shovel.class, "charge",charge,fullCharge);
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
	
	@Override
	public int price() {
		return 30 * quantity;
	}
}
