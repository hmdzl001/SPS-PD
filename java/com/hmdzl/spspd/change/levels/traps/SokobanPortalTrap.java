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
package com.hmdzl.spspd.change.levels.traps;

import java.util.Collection;
import java.util.HashSet;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.MrDestructo;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanStop;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class SokobanPortalTrap {

	public static int portPos = 0;
	
	// 00x66CCEE

	public static void trigger(int pos, Char ch, int dest) {

		if (ch instanceof Hero ){
			//teleport ch to dest from pos teleport scroll
			ScrollOfTeleportation.teleportHeroLocation((Hero) ch,dest);
			//GLog.i("teleport to,  %s",dest);
				
		}
	}
}
