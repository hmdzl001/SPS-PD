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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.StenchGas;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GasesImmunity;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphDark;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphEarth;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphElectricity;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphFire;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphIce;
import com.hmdzl.spspd.actors.buffs.armorbuff.GlyphLight;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Testglyph extends Glyph {

	private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing(0x22CC44);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		GlyphDark gdark = defender.buff(GlyphDark.class);
		GlyphIce gice = defender.buff(GlyphIce.class);
		GlyphLight glight = defender.buff(GlyphLight.class);
		GlyphFire gfire = defender.buff(GlyphFire.class);
		GlyphEarth gearth = defender.buff(GlyphEarth.class);
		GlyphElectricity gelect = defender.buff(GlyphElectricity.class);
		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);

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
			
		if (Random.Int(level + 5) >= 4 || (fcb != null && Random.Int(level + 5) >= 2)) {
			Buff.prolong(defender, GasesImmunity.class, GasesImmunity.DURATION);
			
			switch (Random.Int (6)) {
				case 0:
					GameScene.add(Blob.seed(attacker.pos, 25, ToxicGas.class));
					break;
				case 1:
					GameScene.add(Blob.seed(attacker.pos, 25, ConfusionGas.class));
					break;
				case 2:
					GameScene.add(Blob.seed(attacker.pos, 25, ParalyticGas.class));
					break;
				case 3:
					GameScene.add(Blob.seed(attacker.pos, 25, DarkGas.class));
					break;
				case 4:
					GameScene.add(Blob.seed(attacker.pos, 25, TarGas.class));
				    break;	
				case 5:
					GameScene.add(Blob.seed(attacker.pos, 25, StenchGas.class));
					break;			
				default:
					break;
			}
		}
		return damage;
	}

	@Override
	public Glowing glowing() {
		return GREEN;
	}

}
