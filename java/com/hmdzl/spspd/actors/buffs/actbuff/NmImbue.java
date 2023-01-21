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

package com.hmdzl.spspd.actors.buffs.actbuff;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.NmGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

/**
 * Created by debenhame on 19/11/2014.
 */
public class NmImbue extends Buff implements Hero.Doom  {

	@Override
	public boolean act() {

		GameScene.add(Blob.seed(target.pos, 30, NmGas.class));

		if (target.isAlive()) {

			Hero hero = (Hero) target;
			Nmstop nmstop = target.buff(Nmstop.class);

			if ((((Random.Int(250) < Dungeon.hero.spp) &&  
			(Dungeon.hero.spp < Statistics.deepestFloor * 10+20) ) &&
					( nmstop == null ))
			|| Dungeon.hero.spp == 0) {
				Item item = hero.belongings.randomUnequipped();
				Bag bag = hero.belongings.backpack;
				if (item instanceof Bag) {
					bag = (Bag) item;
					item = Random.element(bag.items);
				}
				if ((item instanceof Scroll
						|| item instanceof Potion
						|| item instanceof Food
						|| item instanceof Wand
						|| item instanceof Plant.Seed
						|| item instanceof Weapon
						|| item instanceof Armor
						|| item instanceof Ring
						|| item instanceof Artifact) && !item.unique
						) {

					item = item.detach(hero.belongings.backpack);
					GLog.w(Messages.get(this, "burnsup", item.toString()));
					Dungeon.hero.spp++;
					Heap.burnFX(hero.pos);

				}
			} else if ((Dungeon.hero.spp > Statistics.deepestFloor * 10) && (Random.Int(20) == 0) && nmstop == null){
				Buff.affect( target, Nmstop.class,720f);
			}  else if ( nmstop != null && (Dungeon.hero.spp > Statistics.deepestFloor * 10)){
				Dungeon.hero.spp--;
			}
		}


		spend(TICK);

		return true;
	}

	@Override
	public int icon() {
		return BuffIndicator.PTOXIC;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}

	@Override
	public void onDeath() {

		Badges.validateDeathFromFire();

		Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
		//GLog.n(TXT_BURNED_TO_DEATH);
	}

	{
		immunities.add(NmGas.class);
	}
}
