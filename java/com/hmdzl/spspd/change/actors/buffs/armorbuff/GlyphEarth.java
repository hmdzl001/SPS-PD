package com.hmdzl.spspd.change.actors.buffs.armorbuff;

import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.ShadowRat;
import com.hmdzl.spspd.change.actors.mobs.Warlock;

	public class GlyphEarth extends Buff {

		{
			immunities.add( Roots.class );
			immunities.add( Ooze.class );
			immunities.add( Poison.class );
			immunities.add(WeatherOfSand.class );
		}

	}