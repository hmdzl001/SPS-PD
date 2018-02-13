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
package com.hmdzl.spspd.change.items.weapon.missiles;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.mobs.Assassin;
import com.hmdzl.spspd.change.actors.mobs.Bat;
import com.hmdzl.spspd.change.actors.mobs.Brute;
import com.hmdzl.spspd.change.actors.mobs.Gnoll;
import com.hmdzl.spspd.change.actors.mobs.GoldThief;
import com.hmdzl.spspd.change.actors.mobs.PoisonGoo;
import com.hmdzl.spspd.change.actors.mobs.Rat;
import com.hmdzl.spspd.change.actors.mobs.RatBoss;
import com.hmdzl.spspd.change.actors.mobs.Shaman;
import com.hmdzl.spspd.change.actors.mobs.SpectralRat;
import com.hmdzl.spspd.change.actors.mobs.Thief;
import com.hmdzl.spspd.change.actors.mobs.GnollArcher;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ForestDart extends MissileWeapon {

	{
		//name = "lucky throwing knive";
		image = ItemSpriteSheet.KNIVE;

		MIN = 6;
		MAX = 14;

		bones = false; // Finding them in bones would be semi-frequent and
						// disappointing.
	}

	public ForestDart() {
		this(1);
	}

	public ForestDart(int number) {
		super();
		quantity = number;
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
		
       if (    defender instanceof Gnoll 
    		|| defender instanceof GnollArcher  
    		|| defender instanceof Shaman  
    		|| defender instanceof Brute
    		|| defender instanceof Bat
    		|| defender instanceof Rat
    		|| defender instanceof RatBoss
    		|| defender instanceof Assassin 
    		|| defender instanceof Thief 
    		|| defender instanceof GoldThief 
    		|| defender instanceof PoisonGoo 
    		|| defender instanceof SpectralRat 
    		){
    	   defender.damage(Random.Int(damage*2,damage*5), this);
       } else {
    	   defender.damage(Random.Int(damage,damage*2), this); 
       }


	}


	@Override
	public Item random() {
		quantity = Random.Int(5, 15);
		return this;
	}

	@Override
	public int price() {
		return quantity * 2;
	}
	
	private static final Glowing GREEN = new Glowing(0x00FF00);
	
	@Override
	public Glowing glowing() {
		return GREEN;
	}
}
