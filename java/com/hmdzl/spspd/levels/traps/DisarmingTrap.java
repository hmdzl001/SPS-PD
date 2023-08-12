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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.TrapSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class DisarmingTrap extends Trap{

	{
		color = TrapSprite.ORANGE;
		shape = TrapSprite.LARGE_DOT;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		Heap heap = Dungeon.level.heaps.get( pos );

		if (heap != null){
			int cell = Dungeon.level.randomRespawnCell();

			if (cell != -1) {
				Item item = heap.pickUp();
				Dungeon.level.drop( item, cell ).seen = true;
				for (int i : Level.NEIGHBOURS9)
					Dungeon.level.visited[cell+i] = true;
				Dungeon.observe();

				Sample.INSTANCE.play(Assets.SND_TELEPORT);
				CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);
			}
		}

		if (Dungeon.hero.pos == pos){
			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;

			if (weapon != null && !(weapon instanceof Knuckles || weapon instanceof FightGloves) && !weapon.cursed) {

				int cell = Dungeon.level.randomRespawnCell();
				if (cell != -1) {
					hero.belongings.weapon = null;
					Dungeon.quickslot.clearItem(weapon);
					weapon.updateQuickslot();

					Dungeon.level.drop(weapon, cell).seen = true;
					for (int i : Level.NEIGHBOURS9)
						Dungeon.level.visited[cell+i] = true;
					Dungeon.observe();

					GLog.w( Messages.get(this, "disarm") );

					Sample.INSTANCE.play(Assets.SND_TELEPORT);
					CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);

				}

			}
		}
	}
}
