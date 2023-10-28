/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.effects.Beam;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.hmdzl.spspd.sprites.SkeletonSprite;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class LotusSummoner extends Mob {
	
	{
		spriteClass = ErrorSprite.class;
		
		HP = HT = 40;
		evadeSkill = 14;
		
		EXP = 7;
		maxLvl = 14;
		
		loot = new PotionOfHealing();
		lootChance = 0.2f; //see lootChance()
		
		properties.add(Property.DEMONIC);

	}
	
	public boolean summoning = false;

	
	protected boolean firstSummon = true;


	@Override
	protected boolean act() {
		if (summoning && state != HUNTING){
			summoning = false;
		}
		return super.act();
	}
	
	@Override
	public int drRoll() {
		return super.drRoll() + Random.NormalIntRange(0, 5);
	}





}