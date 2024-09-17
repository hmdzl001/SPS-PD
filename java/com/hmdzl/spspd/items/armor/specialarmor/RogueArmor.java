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
package com.hmdzl.spspd.items.armor.specialarmor;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class RogueArmor extends NormalArmor {

	{
		//name = "phantom armor";
		image = ItemSpriteSheet.ARMOR_ROGUE;
		STR -= 1;
		MAX = 2;
		MIN = 0;
		M_MIN = 0;
		M_MAX = 7;
	}

	public RogueArmor() {
		super(1,5f,13f,2);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		if (Random.Int(8) == 0) {
			Dungeon.gold+=damage;
			Dungeon.hero.sprite.showStatus(CharSprite.NEUTRAL, "%+d", damage);
		}

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}
	}
}