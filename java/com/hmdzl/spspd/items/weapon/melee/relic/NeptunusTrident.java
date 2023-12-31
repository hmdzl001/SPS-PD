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
package com.hmdzl.spspd.items.weapon.melee.relic;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;

import java.util.ArrayList;

public class NeptunusTrident extends RelicMeleeWeapon {

	public NeptunusTrident() {
		super(6, 1f, 1f, 2);
		// TODO Auto-generated constructor stub
	}

	
	{
		//name = "Neptunus Trident";
		image = ItemSpriteSheet.TRIDENT;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = 0;
		chargeCap = 1000;

		cooldown = 0;
		 
	}
	
	
	private int distance(){
		return Math.round(2);
	}
	
	private void flood(int distance, Hero hero) {
        charge = 0;
		ArrayList<Integer> affected = new ArrayList<Integer>();
		
		int length = Floor.getLength();
		int width = Floor.getWidth();
		for (int i = width; i < length - width; i++){
			int	 dist = Floor.distance(hero.pos, i);
			  if (dist<distance){
			    //GLog.i("TRI2 %s", dist);	
			    if (checkFloodable(i)) {
			    	affected.add(i);
			    	Dungeon.depth.map[i]=Terrain.WATER;
					Floor.water[i] = true;
			     }
			   }
			  
			}
		//GLog.i("TRI1 %s", length);
		for (int n : affected){
				int t = Terrain.WATER_TILES;
				for (int j = 0; j < Floor.NEIGHBOURS4.length; j++) {
					if ((Terrain.flags[Dungeon.depth.map[n + Floor.NEIGHBOURS4[j]]] & Terrain.UNSTITCHABLE) != 0) {
						t += 1 << j;	
						
					}
				}
				
				Char ch = Actor.findChar(n);
				if (ch != null && ch != hero) {
					Buff.affect(ch, Slow.class, level/10);
				}
				
				Dungeon.depth.map[n] = t;
				//Level.water[i] = true;
				GameScene.updateMap(n);		  
		}
		Dungeon.observe();
		
	}
	
	private boolean checkFloodable (int cell){
		
		boolean check=false;
		
		if ((Dungeon.depth.map[cell]==Terrain.EMPTY ||
			Dungeon.depth.map[cell]==Terrain.GRASS ||
			Dungeon.depth.map[cell]==Terrain.HIGH_GRASS ||
			Dungeon.depth.map[cell]==Terrain.EMBERS ||
			Dungeon.depth.map[cell]==Terrain.EMPTY_DECO ||
			Dungeon.depth.map[cell]==Terrain.SIGN ||
			Dungeon.depth.map[cell]==Terrain.SHRUB ||
			Dungeon.depth.map[cell]==Terrain.STATUE ||
			Dungeon.depth.map[cell]==Terrain.SECRET ||
			Dungeon.depth.map[cell]==Terrain.AVOID)
			&& 
			!(Dungeon.depth.map[cell]==Terrain.UNSTITCHABLE||Dungeon.depth.map[cell]==Terrain.WELL)
				){
			check=true;
		} 
		
		if (Floor.water[cell]){
			check=true;			
		}
		
		if(Dungeon.depth.map[cell]==Terrain.ENTRANCE || Dungeon.depth.map[cell]==Terrain.EXIT){
			check=false;
		}
		
		return check;
	}
	
	
	public static final String AC_FLOOD = "FLOOD";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_FLOOD);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_FLOOD)) {
			int distance=distance();
			flood(distance, hero);		
		} else
			super.execute(hero, action);
	}

	
	public class Flooding extends WeaponBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap) {
				charge+=Math.min(level, 10);
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}


		@Override
		public String toString() {
			return "Flooding";
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.NONE;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}

	}
	
	@Override
	protected WeaponBuff passiveBuff() {
		return new Flooding();
	}
	
}


