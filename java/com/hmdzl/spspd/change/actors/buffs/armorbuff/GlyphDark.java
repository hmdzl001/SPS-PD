package com.hmdzl.spspd.change.actors.buffs.armorbuff;

import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfDead;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.CountDown;
import com.hmdzl.spspd.change.actors.buffs.DeadRaise;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.Warlock;

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