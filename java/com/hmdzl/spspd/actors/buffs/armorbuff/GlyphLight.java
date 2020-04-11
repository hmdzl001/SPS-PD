package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.actors.mobs.DwarfLich;
import com.hmdzl.spspd.actors.mobs.Fiend;
import com.hmdzl.spspd.actors.mobs.SewerHeart;
import com.hmdzl.spspd.actors.mobs.Warlock;

	public class GlyphLight extends Buff {

		{
			immunities.add( Blindness.class );
			immunities.add( Vertigo.class );
			immunities.add( Charm.class );
			immunities.add(WeatherOfSun.class );
		}

	}