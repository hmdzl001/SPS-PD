package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.Fire;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.DBurning;
import com.hmdzl.spspd.actors.buffs.Hot;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.mobs.FireElemental;
import com.hmdzl.spspd.actors.mobs.Yog;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire2;

public class GlyphFire extends Buff {

		{
			immunities.add( Burning.class );
			resistances.add( FireElemental.class );
			resistances.add( Yog.BurningFist.class );
			immunities.add( Fire.class );
			immunities.add( Tar.class );
			immunities.add( TarGas.class );

			immunities.add( Hot.class );

			immunities.add( EnchantmentFire.class );
			immunities.add( EnchantmentFire2.class );
			//immunities.add( Iceglyph.class );
			immunities.add(DBurning.class );
			immunities.add(DamageType.FireDamage.class);
		}

	}