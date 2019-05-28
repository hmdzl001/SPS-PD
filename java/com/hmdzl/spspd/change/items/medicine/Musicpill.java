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
package com.hmdzl.spspd.change.items.medicine;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Rhythm;
import com.hmdzl.spspd.change.actors.buffs.Rhythm2;
import com.hmdzl.spspd.change.actors.buffs.WarGroove;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

public class Musicpill extends Pill {

	{
		//name = "speedpill";
		image = ItemSpriteSheet.PILL_MUC;
		bones = false;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			Buff.affect(hero, Rhythm.class,200f);
			if (Dungeon.hero.heroClass == HeroClass.PERFORMER)
				Buff.affect(hero, WarGroove.class);
			if (Dungeon.hero.subClass == HeroSubClass.SUPERSTAR)
				Buff.affect(hero, Rhythm2.class,200f);
			hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.4f, 4);
		}
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

}