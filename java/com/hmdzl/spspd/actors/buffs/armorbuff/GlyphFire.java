package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.Fire;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Hot;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.actors.mobs.DwarfLich;
import com.hmdzl.spspd.actors.mobs.Fiend;
import com.hmdzl.spspd.actors.mobs.FireElemental;
import com.hmdzl.spspd.actors.mobs.Warlock;
import com.hmdzl.spspd.actors.mobs.Yog;

public class GlyphFire extends Buff {

		{
			immunities.add( Burning.class );
			resistances.add( FireElemental.class );
			resistances.add( Yog.BurningFist.class );
			immunities.add( Fire.class );
			immunities.add( Tar.class );
			immunities.add( TarGas.class );

			immunities.add( Hot.class );
		}

	}