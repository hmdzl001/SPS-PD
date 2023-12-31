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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.armorbuff.ArmorGlyphBuff;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphDark;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;

public class Darkglyph extends Glyph {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

	    GlyphDark gdark = defender.buff(GlyphDark.class);
		ArmorGlyphBuff armorGlyphBuff = defender.buff(ArmorGlyphBuff.class);
		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);

		if (defender.isAlive() && gdark == null)
		{
			Buff.detach(defender,ArmorGlyphBuff.class);
			Buff.affect(defender,GlyphDark.class);
		}
	
		int level = Math.max(0, armor.level);
		if (Random.Int(level / 2 + 5) >= 8 || (fcb != null && Random.Int(level/2 + 5) >= 6)) {

			int healing = Random.Int(attacker.HP/10);

			if (healing > 0) {
			defender.HP += Random.Int(Math.min(healing, (defender.HT - defender.HP)/4)/2);
			defender.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			//defender.sprite.showStatus(CharSprite.POSITIVE,Integer.toString(healing));
			attacker.damage(healing, DARK_DAMAGE);
			}
		}

		return damage;
	}

	@Override
	public Glowing glowing() {
		return BLACK;
	}

}
