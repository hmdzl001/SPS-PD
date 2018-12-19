package com.hmdzl.spspd.change.actors.buffs.armorbuff;

import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.SewerHeart;
import com.hmdzl.spspd.change.actors.mobs.ShadowRat;
import com.hmdzl.spspd.change.actors.mobs.Warlock;

	public class GlyphLight extends Buff {

		{
			immunities.add( Blindness.class );
			immunities.add( Vertigo.class );
			immunities.add( Charm.class );
			immunities.add(WeatherOfSun.class );
		}

	}