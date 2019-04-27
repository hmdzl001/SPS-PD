/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.Freezing;
import com.hmdzl.spspd.change.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.change.actors.blobs.Regrowth;
import com.hmdzl.spspd.change.actors.blobs.ShockWeb;
import com.hmdzl.spspd.change.actors.blobs.StenchGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.blobs.VenomGas;
import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfRain;
import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.change.actors.blobs.Web;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;

public class GoldTouch extends FlavourBuff {

    public static final float DURATION = 30f;

	@Override
	public int icon() {
		return BuffIndicator.GOLDTOUCH;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	
	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}
}