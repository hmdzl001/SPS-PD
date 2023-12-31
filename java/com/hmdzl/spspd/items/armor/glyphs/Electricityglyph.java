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
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.armorbuff.ArmorGlyphBuff;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphElectricity;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.EnergyParticle;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Electricityglyph extends Glyph {

	private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing(	0xFFFFFF);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		GlyphElectricity gelect = defender.buff(GlyphElectricity.class);
		ArmorGlyphBuff armorGlyphBuff = defender.buff(ArmorGlyphBuff.class);
		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);

		if (defender.isAlive() && gelect == null)
		{
			Buff.detach(defender,ArmorGlyphBuff.class);
			Buff.affect(defender,GlyphElectricity.class);
		}	
	
		int level = Math.max(0, armor.level);

		
	   if (Random.Int(level) >= 5) {	
		   if (defender instanceof Hero) {
			   Buff.prolong(defender, Recharging.class, Math.min(level,30));
		  }
	    }
		
		if (Random.Int(level + 6) >= 5 || (fcb != null && Random.Int(level + 6) >= 3)) {
            Buff.prolong(attacker, Paralysis.class, 2f);
			CellEmitter.get(attacker.pos).start(EnergyParticle.FACTORY, 0.2f, 6);
		}		

		return damage;
	}

	@Override
	public Glowing glowing() {
		return WHITE;
	}
	
}
