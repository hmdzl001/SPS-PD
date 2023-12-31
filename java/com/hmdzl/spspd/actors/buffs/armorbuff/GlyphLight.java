package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.LightShootAttack;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentLight;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentLight2;

public class GlyphLight extends ArmorGlyphBuff {

		{
			immunities.add( Blindness.class );
			immunities.add( Vertigo.class );
			immunities.add( Charm.class );
			immunities.add(WeatherOfSun.class );

			immunities.add( EnchantmentLight.class );
			immunities.add( EnchantmentLight2.class );
			//immunities.add( Lightglyph.class );
			immunities.add(LightShootAttack.class );
			immunities.add(DamageType.LightDamage.class);
		}

	}