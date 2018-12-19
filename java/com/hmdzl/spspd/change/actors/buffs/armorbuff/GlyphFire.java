package com.hmdzl.spspd.change.actors.buffs.armorbuff;

import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.TarGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Hot;
import com.hmdzl.spspd.change.actors.buffs.Tar;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.FireElemental;
import com.hmdzl.spspd.change.actors.mobs.ShadowRat;
import com.hmdzl.spspd.change.actors.mobs.Warlock;
import com.hmdzl.spspd.change.actors.mobs.Yog;

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