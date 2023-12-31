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
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.armorbuff.ArmorGlyphBuff;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphLight;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

public class Lightglyph extends Glyph {

	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF44 );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {


	    GlyphLight glight = defender.buff(GlyphLight.class);
		ArmorGlyphBuff armorGlyphBuff = defender.buff(ArmorGlyphBuff.class);
		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);

		if (defender.isAlive() && glight == null)
		{
			Buff.detach(defender,ArmorGlyphBuff.class);
			Buff.affect(defender,GlyphLight.class);
		}	
	
		int level = (int) GameMath.gate(0, armor.level, 6);

		if (Random.Int(level / 2 + 5) >= 4) {
			Buff.affect(attacker, Charm.class,  Random.IntRange( 4, 7 )).object = defender.id();
		    Buff.affect(attacker, Amok.class,10f);
			attacker.sprite.centerEmitter().start(Speck.factory(Speck.HEART),0.2f, 5);
		} 
		
		else if (Random.Int(level / 2 + 5) >= 3 || (fcb != null && Random.Int(level/2 + 5) >= 1)) {
			Buff.affect(attacker, Terror.class, 10f).object = defender.id();
		} 		

		return damage;
	}

	@Override
	public Glowing glowing() {
		return YELLOW;
	}

}
