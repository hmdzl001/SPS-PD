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
package com.hmdzl.spspd.items.weapon.missiles.arrows;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Drowsy;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class RiceBall extends Arrows {
	
	public static final float DURATION = 10f;

	{
		//name = "rice dumpling";
		image = ItemSpriteSheet.DUST;


		  
	}

	public RiceBall() {
		this(1);
	}

	public RiceBall(int number) {
		super(1,1);
		quantity = number;
	}

	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
		if (defender != null 
				&& !(defender instanceof NPC)
				&& !defender.properties().contains(Char.Property.UNDEAD)
				&& !defender.properties().contains(Char.Property.BOSS)
				&& !defender.properties().contains(Char.Property.DEMONIC)
				&& !defender.properties().contains(Char.Property.UNKNOW)
				&& !defender.properties().contains(Char.Property.ELEMENT)
				&& !defender.properties().contains(Char.Property.MECH)
				) {
  
			Buff.affect(defender, Drowsy.class);
			defender.sprite.centerEmitter().start(Speck.factory(Speck.NOTE), 0.3f, 5);
			
			if  (defender.HP/defender.HT > 0.01f){ 
			int count = 20;
			int pos;
			do {
				pos = Dungeon.depth.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (pos == -1);

			if (pos == -1) {

				GLog.w(Messages.get(ScrollOfTeleportation.class, "tele"));

			} else {

				defender.pos = pos;
				defender.sprite.place(defender.pos);
				defender.sprite.visible = Dungeon.visible[pos];
				GLog.i(curUser.name + " teleported " + defender.name
						+ " to somewhere");

			}

		   } else {

			GLog.i("nothing happened");

		  }
	    }
		
		super.proc(attacker, defender, damage);
	}
	@Override
	public int price() {
		return quantity * 10;
	}
}
