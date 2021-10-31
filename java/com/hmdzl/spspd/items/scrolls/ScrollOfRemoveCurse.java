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
package com.hmdzl.spspd.items.scrolls;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.LightShootAttack;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Flare;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfRemoveCurse extends Scroll {

	{
		//name = "Scroll of Remove Curse";
		consumedValue = 15;
		initials = 11;
	}

	@Override
	public void doRead() {

		new Flare(6, 32).show(curUser.sprite, 2f);
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		boolean procced = uncurse(curUser,
				curUser.belongings.backpack.items.toArray(new Item[0]));
		procced = uncurse(curUser, curUser.belongings.weapon,
				curUser.belongings.armor, curUser.belongings.misc1,
				curUser.belongings.misc2, curUser.belongings.misc3 )
				|| procced;

		Buff.detach(curUser, STRdown.class);

		if (procced) {
			GLog.p(Messages.get(this, "cleansed") );
		} else {
			GLog.i(Messages.get(this, "not_cleansed"));
		}

		setKnown();

		readAnimation();
	}

	public static boolean uncurse(Hero hero, Item... items) {

		boolean procced = false;
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item != null && item.cursed) {
				item.uncurse();
				if(item.level<0){item.upgrade((-item.level)*2);} //upgrade to reverse of negatives
         	   if (item.cursed==false) {procced = true;}
			}
			if (item instanceof Bag) {
				for (Item bagItem: ((Bag)item).items){
                   if (bagItem != null && bagItem.cursed) {
                	   bagItem.uncurse();
                	   if(bagItem.level<0){bagItem.upgrade((-bagItem.level)*2);}
                	   if (bagItem.cursed==false) {procced = true;}
                     }
				}
			}
		}

		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob,LightShootAttack.class).level(6);
			}
		}

		if (procced) {
			hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);
		}

		return procced;
	}
	
	@Override
	public void empoweredRead() {
		for (Item item : curUser.belongings){
			if (item.cursed){
				item.cursedKnown = true;
			}
		}
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		doRead();
	}	

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
