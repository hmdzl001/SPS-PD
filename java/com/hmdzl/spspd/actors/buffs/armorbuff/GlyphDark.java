package com.hmdzl.spspd.actors.buffs.armorbuff;

import com.hmdzl.spspd.actors.blobs.weather.WeatherOfDead;
import com.hmdzl.spspd.actors.buffs.CountDown;
import com.hmdzl.spspd.actors.buffs.DeadRaise;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.mobs.DwarfLich;
import com.hmdzl.spspd.actors.mobs.Fiend;
import com.hmdzl.spspd.actors.mobs.Warlock;
import com.hmdzl.spspd.items.armor.glyphs.Darkglyph;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark2;

public class GlyphDark extends ArmorGlyphBuff {

        {
            immunities.add( STRdown.class );
			immunities.add( CountDown.class );
			immunities.add( DeadRaise.class );
            immunities.add( Silent.class );
            immunities.add( WeatherOfDead.class );

            resistances.add( DwarfLich.class );
            resistances.add( Warlock.class );
            resistances.add( Fiend.class );

            immunities.add( EnchantmentDark2.class );
            immunities.add( EnchantmentDark.class );
			immunities.add(Darkglyph.class );
			immunities.add(DamageType.DarkDamage.class);

        }

	}