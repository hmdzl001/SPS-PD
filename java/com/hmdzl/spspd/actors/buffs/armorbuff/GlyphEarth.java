package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.actors.mobs.DwarfLich;
import com.hmdzl.spspd.actors.mobs.Fiend;
import com.hmdzl.spspd.actors.mobs.Warlock;

	public class GlyphEarth extends Buff {

		{
			immunities.add( Roots.class );
			immunities.add( Ooze.class );
			immunities.add( Poison.class );
			immunities.add(WeatherOfSand.class );
		}

	}