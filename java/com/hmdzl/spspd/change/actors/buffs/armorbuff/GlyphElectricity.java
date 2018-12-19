package com.hmdzl.spspd.change.actors.buffs.armorbuff;

import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Shocked;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.GnollShaman;
import com.hmdzl.spspd.change.actors.mobs.ShadowRat;
import com.hmdzl.spspd.change.actors.mobs.Shell;
import com.hmdzl.spspd.change.actors.mobs.Warlock;
import com.hmdzl.spspd.change.items.wands.WandOfLightning;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;

public class GlyphElectricity extends Buff {

		{
			resistances.add( Shell.class );
			resistances.add( GnollShaman.class );

			immunities.add( WandOfLightning.class );
			immunities.add( Shocked.class );
			immunities.add( ElectriShock.class );
			immunities.add( LightningTrap.Electricity.class );
		}

	}