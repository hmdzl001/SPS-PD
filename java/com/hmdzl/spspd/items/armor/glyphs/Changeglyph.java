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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.armorbuff.ArmorGlyphBuff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.NormalCell;
import com.hmdzl.spspd.actors.mobs.npcs.MirrorImage;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.Armor.Glyph;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Changeglyph extends Glyph {

	private static ItemSprite.Glowing PURPLE = new ItemSprite.Glowing(0x8844CC);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		ArmorGlyphBuff armorGlyphBuff = defender.buff(ArmorGlyphBuff.class);

		FourClover.FourCloverBless fcb = defender.buff(FourClover.FourCloverBless.class);

		if (defender.isAlive() && (armorGlyphBuff!= null))
		{
			Buff.detach(defender,ArmorGlyphBuff.class);
		}

		int level = Math.max(0, armor.level);

		if (Random.Int(level / 2 + 6) >= 5 || (fcb != null && Random.Int(level / 2 + 6) >= 3)) {

			ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
				int p = defender.pos + Floor.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Floor.passable[p] || Floor.avoid[p])) {
					respawnPoints.add(p);
				}
			}

			if (respawnPoints.size() > 0) {
				if (defender == Dungeon.hero) {
					MirrorImage mob = new MirrorImage();
					mob.duplicate((Hero) defender);
					GameScene.add(mob);
					ScrollOfTeleportation.appear(mob, Random.element(respawnPoints));
					checkOwner(defender);
				} else {
					NormalCell mob1 = new NormalCell();
					GameScene.add(mob1);
					ScrollOfTeleportation.appear(mob1, Random.element(respawnPoints));
				}
			}
		}		
		
		if (Dungeon.bossLevel()) {
			return damage;
		}
		
		int nTries = (armor.level < 0 ? 1 : armor.level + 1) * 5;
		for (int i=0; i < nTries; i++) {
			int pos = Random.Int( Floor.LENGTH );
			if (Dungeon.visible[pos] && Floor.passable[pos] && Actor.findChar( pos ) == null) {
				
				ScrollOfTeleportation.appear( defender, pos );
				Dungeon.depth.press( pos, defender );
				Buff.affect(defender, Invisibility.class, 5f);
				Dungeon.observe();
				break;
			}
		}		

		return damage;
	}

	@Override
	public Glowing glowing() {
		return PURPLE;
	}
}
