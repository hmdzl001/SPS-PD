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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.Freezing;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.Regrowth;
import com.hmdzl.spspd.actors.blobs.ShockWeb;
import com.hmdzl.spspd.actors.blobs.StenchGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.VenomGas;
import com.hmdzl.spspd.actors.blobs.Web;
import com.hmdzl.spspd.actors.blobs.effectblobs.ElectriShock;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfRain;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;

public class Bless extends FlavourBuff {

    public static final float DURATION = 30f;

	{
		type = buffType.NEUTRAL;
	}
	@Override
	public int icon() {
		return BuffIndicator.BLESS;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	{
		resistances.add( ParalyticGas.class );
		resistances.add( ToxicGas.class );
		resistances.add( ConfusionGas.class );
		resistances.add( StenchGas.class );
		resistances.add( VenomGas.class );
		resistances.add( CorruptGas.class );
		resistances.add( Fire.class );
		resistances.add( Freezing.class );
		resistances.add( ElectriShock.class );
		resistances.add( LightningTrap.Electricity.class );
		resistances.add( Regrowth.class );
		resistances.add( Web.class );
		resistances.add( ShockWeb.class );
		resistances.add( Hot.class );
		resistances.add( Cold.class );
		resistances.add( Wet.class );
		resistances.add( Dry.class );
		resistances.add(WeatherOfRain.class);
		resistances.add(WeatherOfSand.class);
		resistances.add(WeatherOfSnow.class);
		resistances.add(WeatherOfSun.class);
	}	
	
	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}
}
