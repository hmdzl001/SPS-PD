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

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.TarGas;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Hot;
import com.hmdzl.spspd.change.actors.buffs.Tar;
import com.hmdzl.spspd.change.actors.mobs.FireElemental;
import com.hmdzl.spspd.change.actors.mobs.Yog;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.FlameParticle;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.armor.Armor.Glyph;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphDark;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphEarth;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphElectricity;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphFire;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphIce;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphLight;

import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Fireglyph extends Glyph {

	private static ItemSprite.Glowing ORANGE = new ItemSprite.Glowing( 0xFF4400 );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

	    GlyphDark gdark = defender.buff(GlyphDark.class);
		GlyphIce gice = defender.buff(GlyphIce.class);
	    GlyphLight glight = defender.buff(GlyphLight.class);
	    GlyphFire gfire = defender.buff(GlyphFire.class);
		GlyphEarth gearth = defender.buff(GlyphEarth.class);
		GlyphElectricity gelect = defender.buff(GlyphElectricity.class);	
	
		if (defender.isAlive() && gfire == null)
		{
			Buff.detach(defender,GlyphIce.class);
			Buff.detach(defender,GlyphLight.class);
			Buff.detach(defender,GlyphDark.class);
			Buff.detach(defender,GlyphEarth.class);
			Buff.detach(defender,GlyphElectricity.class);
			Buff.affect(defender,GlyphFire.class);
		}	
	
		int level = Math.max(0, armor.level);

		if (Random.Int(level + 6) >= 5) {
			Buff.affect(attacker, Burning.class).reignite( attacker );
			defender.sprite.emitter().burst(FlameParticle.FACTORY, 5);
		}

		if (Random.Int(level + 7) >= 6) {
			Buff.prolong(defender, AttackUp.class,5f).level(25);
		}
		
		return damage;
	}

	@Override
	public Glowing glowing() {
		return ORANGE;
	}

}
