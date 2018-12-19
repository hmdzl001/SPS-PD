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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Drowsy;
import com.hmdzl.spspd.change.actors.mobs.BlueWraith;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.FlyingProtector;
import com.hmdzl.spspd.change.actors.mobs.Golem;
import com.hmdzl.spspd.change.actors.mobs.RedWraith;
import com.hmdzl.spspd.change.actors.mobs.Sentinel;
import com.hmdzl.spspd.change.actors.mobs.ShadowYog;
import com.hmdzl.spspd.change.actors.mobs.Skeleton;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.Statue;
import com.hmdzl.spspd.change.actors.mobs.Wraith;
import com.hmdzl.spspd.change.actors.mobs.Yog;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class RiceBall extends MissileWeapon {
	
	public static final float DURATION = 10f;

	{
		//name = "rice dumpling";
		image = ItemSpriteSheet.RICEBALL;

		MIN = 1;
		MAX = 2;
		
		DLY = 0.25f;

		bones = false; 
	}

	public RiceBall() {
		this(1);
	}

	public RiceBall(int number) {
		super();
		quantity = number;
	}

	@Override
	public String desc() {
		return "A satisfying dumpling lovingly crafted from magic rice. "
		       +"Anything that eats would gladly take a dumpling. ";
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
		
			
		if (defender != null 
				&& !(defender instanceof NPC)
				&& !(defender instanceof BlueWraith)
				&& !(defender instanceof Wraith)
				&& !(defender instanceof RedWraith)
				&& !(defender instanceof Sentinel)
				&& !(defender instanceof FlyingProtector)
				&& !(defender instanceof Golem)
				&& !(defender instanceof Skeleton)
				&& !(defender instanceof DwarfLich)
				&& !(defender instanceof Statue)
				&& !(defender instanceof Yog)
				&& !(defender instanceof ShadowYog)
				&& !(defender instanceof Fiend)
				) {
  
			Buff.affect(defender, Drowsy.class);
			defender.sprite.centerEmitter().start(Speck.factory(Speck.NOTE), 0.3f, 5);
			
			if  (defender.HP/defender.HT > 0.01f){ 
			int count = 20;
			int pos;
			do {
				pos = Dungeon.level.randomRespawnCell();
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
	public Item random() {
		quantity = Random.Int(5, 15);
		return this;
	}

	@Override
	public int price() {
		return quantity * 2;
	}
}
