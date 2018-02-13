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
package com.hmdzl.spspd.change.items.armor.glyphs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.armor.Armor.Glyph;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.items.wands.WandOfBlood;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Displacement extends Glyph {

	private static ItemSprite.Glowing BLUE = new ItemSprite.Glowing(0x66AAFF);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		if (Dungeon.bossLevel()) {
			return damage;
		}
		
	    float LEVEL = 0.333f;
		if (defender.HP > defender.HT * LEVEL) {
			return damage;
		}

		int nTries = (armor.level < 0 ? 1 : armor.level + 1) * 5;
		for (int i = 0; i < nTries; i++) {
			int pos = Random.Int(Level.getLength());
			if (Dungeon.visible[pos] && Level.passable[pos]
					&& Actor.findChar(pos) == null) {

				ScrollOfTeleportation.appear(defender, pos);
				Dungeon.level.press(pos, defender);
				Buff.affect(defender, Invisibility.class, Invisibility.DURATION);
				Dungeon.observe();

				break;
			}
		}

		return damage;
	}

	@Override
	public Glowing glowing() {
		return BLUE;
	}
}
