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
package com.hmdzl.spspd.change.items.misc;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.ItemStatusHandler;
import com.hmdzl.spspd.change.items.KindofMisc;
import com.hmdzl.spspd.change.items.rings.Ring.RingBuff;
import com.hmdzl.spspd.change.items.rings.RingOfAccuracy.Accuracy;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class LuckyBadge extends MiscEquippable {

	{
		//name = "LuckyBadge";
		image = ItemSpriteSheet.LUCKY_BADGE;
		bones = false;
		unique = true;
		
	}
	
	@Override
	protected MiscBuff buff() {
		return new GreatLucky();
	}

	public class GreatLucky extends MiscBuff {
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return true;
	}
}
