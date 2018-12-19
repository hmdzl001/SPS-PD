
package com.hmdzl.spspd.change.actors.buffs.armorbuff;

import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Chill;
import com.hmdzl.spspd.change.actors.buffs.Cold;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.ShadowRat;
import com.hmdzl.spspd.change.actors.mobs.Warlock;

public class GlyphIce extends Buff {

		{
			immunities.add( Frost.class );
			immunities.add( Cold.class );
			immunities.add( Chill.class );
			immunities.add(WeatherOfSnow.class );

		}

	}