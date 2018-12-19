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
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Lightning;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.armor.Armor.Glyph;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphDark;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphEarth;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphElectricity;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphFire;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphIce;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphLight;
import com.watabou.noosa.Camera;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class Revivalglyph extends Glyph {

	private static ItemSprite.Glowing RED = new ItemSprite.Glowing(0xCC0000);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		
	    GlyphDark gdark = defender.buff(GlyphDark.class); 
		GlyphIce gice = defender.buff(GlyphIce.class); 
	    GlyphLight glight = defender.buff(GlyphLight.class); 
	    GlyphFire gfire = defender.buff(GlyphFire.class); 
		GlyphEarth gearth = defender.buff(GlyphEarth.class); 
		GlyphElectricity gelect = defender.buff(GlyphElectricity.class); 
		
		if (defender.isAlive() && (gdark != null || gice != null || glight != null || gfire != null || gearth != null || gelect != null ))
		{
			Buff.detach(defender,GlyphIce.class);
			Buff.detach(defender,GlyphLight.class);
			Buff.detach(defender,GlyphFire.class);
			Buff.detach(defender,GlyphEarth.class);
			Buff.detach(defender,GlyphElectricity.class);
			Buff.detach(defender,GlyphDark.class);
		}		
		
		int level = Math.max(0, armor.level);
        if (damage > defender.HP && Math.min(level,30) > Random.Int(100)){
			defender.HP = defender.HT;
			CellEmitter.get(defender.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
			GLog.w(Messages.get(this, "revive"));
			return 0;
		}
		return damage;
	}

	@Override
	public Glowing glowing() {
		return RED;
	}
}
