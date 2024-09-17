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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.StenchGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.mobs.BrokenRobot;
import com.hmdzl.spspd.actors.mobs.Eye;
import com.hmdzl.spspd.actors.mobs.Warlock;
import com.hmdzl.spspd.actors.mobs.Yog;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;


public class MagicImmunity extends FlavourBuff {

	public static final float DURATION = 10f;

	{
		type = buffType.POSITIVE;
	}	
	
	@Override
	public int icon() {
		return BuffIndicator.BLOCK_SHIELD;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}	
	
	{
		immunities.add(ParalyticGas.class);
		immunities.add(ToxicGas.class);
		immunities.add(ConfusionGas.class);
		immunities.add(StenchGas.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(Poison.class);
		immunities.add(LightningTrap.Electricity.class);
		immunities.add(Warlock.class);
		immunities.add(Eye.class);
		immunities.add(Yog.BurningFist.class);
		immunities.add(BrokenRobot.class);
		immunities.add(CorruptGas.class);
	}	

}

