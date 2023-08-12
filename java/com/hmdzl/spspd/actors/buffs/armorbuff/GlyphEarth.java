package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.SwampGas;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEarth;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEarth2;

public class GlyphEarth extends Buff {

		{
			immunities.add( Roots.class );
			immunities.add( Ooze.class );
			immunities.add( Poison.class );
			immunities.add(WeatherOfSand.class );

			immunities.add( EnchantmentEarth.class );
			immunities.add( EnchantmentEarth2.class );
			immunities.add( GrowSeed.class );
			immunities.add(SwampGas.class );
			immunities.add(DamageType.EarthDamage.class);
		}

	}