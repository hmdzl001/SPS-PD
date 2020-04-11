/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.items.bombs;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class DarkBomb extends Bomb {

	{
		//name = "seeking bomb";
		image = ItemSpriteSheet.DARK_BOMB;
	}


	@Override
	public void explode(int cell) {
		super.explode(cell);
		curUser = Dungeon.hero;
		 for (int n: Level.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}
				Char ch = Actor.findChar(c);
				if (ch != null){
				Buff.affect(ch, Terror.class, Terror.DURATION).object = curUser.id();
				if (ch.properties().contains(Char.Property.HUMAN)
						|| 	ch.properties().contains(Char.Property.PLANT)
						|| ch.properties().contains(Char.Property.ORC)
						||  ch.properties().contains(Char.Property.TROLL)
						||  ch.properties().contains(Char.Property.DWARF)
						|| ch.properties().contains(Char.Property.BEAST)
						|| ch.properties().contains(Char.Property.ELF)
						|| ch.properties().contains(Char.Property.GOBLIN)
						|| ch.properties().contains(Char.Property.BOSS)
						|| ch.properties().contains(Char.Property.MINIBOSS)
						) {
                	 int dmg = Random.NormalIntRange(200, 400);
                	 	ch.damage(dmg, this);

                   } else {
					int dmg = Random.NormalIntRange(50, 100);
					ch.damage(dmg, this);
				}
			}}
		}
	     	
	    Dungeon.observe();
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
}
