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
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.noosa.Camera;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class JupitersWraith extends RelicMeleeWeapon {

	public JupitersWraith() {
		super(6, 1f, 1f, 4);
		// TODO Auto-generated constructor stub
	}
	
	{
		//name = "jupiter's wraith";
		image = ItemSpriteSheet.JUPITERSWRAITH;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = 0;
		chargeCap = 1000;

		cooldown = 0;
		 
	}

	public static final String AC_EXPLODE = "EXPLODE";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_EXPLODE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EXPLODE)) {
			int distance=distance();
			explode(distance, hero);		
		} else
			super.execute(hero, action);
	}
	

	private int distance(){
		return 4;
	}
	
	private void explode(int distance, Hero hero) {
        charge = 0;
		int length = Floor.getLength();
		int width = Floor.getWidth();
		for (int i = width; i < length - width; i++){
			int	 dist = Floor.distance(hero.pos, i);
			  if (dist<distance){
				  doExplode(i);			    
			     }
			   }
			  
	}
	
   public void doExplode(int cell) {
		
		Camera.main.shake(3, 0.7f);
		
				if (Dungeon.visible[cell] && Floor.passable[cell]) {
					CellEmitter.center(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
				}
				
				Char ch = Actor.findChar(cell);
				if (ch != null && ch!=Dungeon.hero) {
					// those not at the center of the blast take damage less
					// consistently.
					int minDamage = MIN;
					int maxDamage = MAX;
					                    
					
					int dmg = Random.NormalIntRange(minDamage, maxDamage) - Math.max(ch.drRoll(),0);
					
					
					if (dmg > 0) {
						ch.damage(dmg, this,1);
						if(Random.Int(3)==1 && ch.isAlive()){Buff.prolong(ch, Paralysis.class, 1);}
					}
											
     			}

	}	


	
	public class ExplodeCharge extends WeaponBuff {

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
			return "Explode";
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
	
	protected WeaponBuff passiveBuff() {
		return new ExplodeCharge();
	}

}
