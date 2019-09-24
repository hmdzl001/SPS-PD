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
package com.hmdzl.spspd.change.levels.traps;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.keys.Key;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.InterlevelScene;
import com.hmdzl.spspd.change.sprites.TrapSprite;
import com.watabou.noosa.Game;

public class DistortionTrap extends Trap{

	{
		color = TrapSprite.TEAL;
		shape = TrapSprite.LARGE_DOT;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		InterlevelScene.returnDepth = Dungeon.depth;
		for (Item item : Dungeon.hero.belongings.backpack.items.toArray( new Item[0])){
			if (item instanceof Key && ((Key)item).depth == Dungeon.depth){
				item.detachAll(Dungeon.hero.belongings.backpack);
			}
		}
		InterlevelScene.mode = InterlevelScene.Mode.RESET;
		Game.switchScene(InterlevelScene.class);
	}
}
