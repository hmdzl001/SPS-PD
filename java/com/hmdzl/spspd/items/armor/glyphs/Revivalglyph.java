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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.CountDown;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.buffs.armorbuff.ArmorGlyphBuff;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.LeafParticle;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Revivalglyph extends Glyph {

	private static ItemSprite.Glowing RED = new ItemSprite.Glowing(0xCC0000);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		ArmorGlyphBuff armorGlyphBuff = defender.buff(ArmorGlyphBuff.class);

		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);

		if (defender.isAlive() && (armorGlyphBuff!= null))
		{
			Buff.detach(defender,ArmorGlyphBuff.class);
		}

		int level = Math.max(0, armor.level);
		if (((Random.Int( 50 ) < level) && (level > 10 ))||
				(Random.Int(4) == 0 ) ) {
			int p = defender.pos;
			plantGrass(p);
		}
		
		if (Random.Int( level ) >= 5) {
			Buff.detach(defender, Paralysis.class);
			Buff.detach(defender, Burning.class);
			Buff.detach(defender, Ooze.class);
			Buff.detach(defender, Tar.class);
			Buff.detach(defender, STRdown.class);
			Buff.detach(defender, Vertigo.class);
			Buff.detach(defender, Poison.class);
			Buff.detach(defender, Cripple.class);
			Buff.detach(defender, Bleeding.class);
			Buff.detach(defender, Slow.class);
			Buff.detach(defender, AttackDown.class);
			Buff.detach(defender, ArmorBreak.class);
			Buff.detach(defender, CountDown.class);
            Buff.detach(defender, GrowSeed.class);
		}		
		
		return damage;
	}

	@Override
	public Glowing glowing() {
		return RED;
	}


	
	
	private boolean plantGrass(int cell){
		int c = Dungeon.depth.map[cell];
		if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
				|| c == Terrain.EMBERS || c == Terrain.GRASS || c == Terrain.WATER_TILES
				|| c == Terrain.WATER ){
			Floor.set(cell, Terrain.HIGH_GRASS);
			GameScene.updateMap(cell);
			CellEmitter.get( cell ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
			return true;
		}
		return false;
	}
		
	
}
