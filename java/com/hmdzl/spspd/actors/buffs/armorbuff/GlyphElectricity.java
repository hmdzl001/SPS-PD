package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.ElectriShock;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Shocked2;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.mobs.GnollShaman;
import com.hmdzl.spspd.actors.mobs.Shell;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentShock;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentShock2;
import com.hmdzl.spspd.levels.traps.LightningTrap;

public class GlyphElectricity extends Buff {

		{
			resistances.add( Shell.class );
			resistances.add( GnollShaman.class );

			immunities.add( WandOfLightning.class );
			immunities.add( Shocked.class );
			immunities.add( ElectriShock.class );
			immunities.add( Locked.class );
			immunities.add( LightningTrap.Electricity.class );

			immunities.add( EnchantmentShock.class );
			immunities.add( EnchantmentShock2.class );
			immunities.add( Shocked2.class );
			immunities.add(DamageType.ShockDamage.class);
		}

	}