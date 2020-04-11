package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.weather.WeatherOfDead;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.CountDown;
import com.hmdzl.spspd.actors.buffs.DeadRaise;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.actors.mobs.DwarfLich;
import com.hmdzl.spspd.actors.mobs.Fiend;
import com.hmdzl.spspd.actors.mobs.Warlock;

public class GlyphDark extends Buff {

        {
            immunities.add( Weakness.class );
			immunities.add( CountDown.class );
			immunities.add( DeadRaise.class );
            immunities.add( Silent.class );
            immunities.add( WeatherOfDead.class );

            resistances.add( DwarfLich.class );
            resistances.add( Warlock.class );
            resistances.add( Fiend.class );
        }

	}