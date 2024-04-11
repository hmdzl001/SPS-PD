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
package com.hmdzl.spspd.items.weapon.melee.start;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class PixelTorch extends MeleeWeapon {
	
	public static final String AC_TLIGHT = "TLIGHT";

	{
		//name = "PixelTorch";
		image = ItemSpriteSheet.TORCH;
	    defaultAction = AC_TLIGHT;
	}

	public PixelTorch() {
		super(2, 1f, 1f, 1);
		MIN = 3;
		MAX = 15;
		unique = true;
		reinforced = true;
	}
	
	
	@Override
	public void execute(final Hero hero, String action) {

		if (action.equals(AC_TLIGHT)) {
			if (Dungeon.hero.spp > 50){
				Buff.prolong(Dungeon.hero, Light.class, 50f);
				Dungeon.hero.spp -= 50;
				Dungeon.hero.spendAndNext(1f);
			} else {
				Buff.prolong(Dungeon.hero, Light.class, 1f);
				Dungeon.hero.spendAndNext(1f);
			}
		} else {
			super.execute(hero, action);
		}
	}


    @Override
    public void proc(Char attacker, Char defender, int damage) {
		if (Random.Int(100) < 20) {
			Buff.affect(defender, Burning.class).set(4f);
		} else if (Random.Int(80) < 20) {
			Buff.affect(defender, Shocked.class).set(4f);
		}
		Dungeon.hero.spp++;
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
    }		
}
