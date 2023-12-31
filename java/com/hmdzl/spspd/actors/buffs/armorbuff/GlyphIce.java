
package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Cold;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce2;

public class GlyphIce extends ArmorGlyphBuff {

		{
			immunities.add( Frost.class );
			immunities.add( Cold.class );
			immunities.add( Chill.class );
			immunities.add(WeatherOfSnow.class );

			immunities.add( EnchantmentIce.class );
			immunities.add( EnchantmentIce2.class );
			//immunities.add( Iceglyph.class );
			immunities.add(FrostIce.class );
            immunities.add(DamageType.IceDamage.class);
		}

	}