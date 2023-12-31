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
package com.hmdzl.spspd.items.armor.glyphs;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.armorbuff.ArmorGlyphBuff;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class RecoilGlyph extends Glyph {

	private static ItemSprite.Glowing BROWN = new ItemSprite.Glowing(0xCC6600);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		ArmorGlyphBuff armorGlyphBuff = defender.buff(ArmorGlyphBuff.class);

		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);

		if (defender.isAlive() && (armorGlyphBuff!= null))
		{
			Buff.detach(defender,ArmorGlyphBuff.class);
		}

		int level = Math.max(0, armor.level);
			
		if (Random.Int( level + 5 ) >= 4 || (fcb != null && Random.Int(level + 5) >= 2)){
			int oppositeHero = attacker.pos + (attacker.pos - defender.pos);
			Ballistica trajectory = new Ballistica(attacker.pos, oppositeHero, Ballistica.MAGIC_BOLT);
			WandOfFlow.throwChar(attacker, trajectory, 2);
		}
		
		if ( Random.Int( level/2 + 5) >= 4) {

			Buff.affect( attacker, Bleeding.class).set( Math.max( level/2, damage));

		}		
		
		return damage;
	}

	@Override
	public Glowing glowing() {
		return BROWN;
	}

}
