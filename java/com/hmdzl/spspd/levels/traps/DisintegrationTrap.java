/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.levels.traps;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Beam;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.TrapSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class DisintegrationTrap extends Trap {

	{
		color = TrapSprite.RED;
		shape = TrapSprite.LARGE_DOT;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (Dungeon.visible[ pos ]) {
			sprite.parent.add( new Beam.DeathRay( DungeonTilemap.tileCenterToWorld(pos-1),
					DungeonTilemap.tileCenterToWorld(pos+1)));
			sprite.parent.add(new Beam.DeathRay(DungeonTilemap.tileCenterToWorld(pos - Floor.WIDTH),
					DungeonTilemap.tileCenterToWorld(pos + Floor.WIDTH)));
			Sample.INSTANCE.play( Assets.SND_RAY );
		}

		Heap heap = Dungeon.depth.heaps.get(pos);
		if (heap != null) heap.explode();

		//Char ch = Actor.findChar(pos);
		if (ch != null){
			ch.damage( Math.max( ch.HT/5, Random.Int(ch.HP / 2, 2 * ch.HP / 3) ), this,2 );
			if (ch == Dungeon.hero){
				Hero hero = (Hero)ch;
				if (!hero.isAlive()){
					//Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
					//GLog.n( Messages.get(this, "ondeath") );
				} else {
					Item item = hero.belongings.randomUnequipped();
					Bag bag = hero.belongings.backpack;
					//bags do not protect against this trap
					if (item instanceof Bag){
						bag = (Bag)item;
						item = Random.element(bag.items);
					}
					if (item == null || item.level > 0 || item.unique  ) return;
					if (!item.stackable){
						item.detachAll(bag);
						GLog.w( Messages.get(this, "one", item.name()) );
					} else {
						int n = Random.NormalIntRange(1, (item.quantity()+1)/2);
						for(int i = 1; i <= n; i++)
							item.detach(bag);
						GLog.w( Messages.get(this, "some", item.name()) );
					}
				}
			}
		}

	}
}
