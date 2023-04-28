/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.actors.damagetype;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.messages.Messages;

public class DamageType {

	public static final EnergyDamage ENERGY_DAMAGE = new EnergyDamage();

	public static class EnergyDamage extends DamageType {

	}

	public static final FireDamage FIRE_DAMAGE = new FireDamage();

	public static class FireDamage extends DamageType{

	}

	public static final IceDamage ICE_DAMAGE = new IceDamage();

	public static class IceDamage extends DamageType{

	}

	public static final ShockDamage SHOCK_DAMAGE = new ShockDamage();

	public static class ShockDamage extends DamageType{

	}

	public static final EarthDamage EARTH_DAMAGE = new EarthDamage();

	public static class EarthDamage extends DamageType{

	}

	public static final LightDamage LIGHT_DAMAGE = new LightDamage();

	public static class LightDamage extends DamageType{

	}

	public static final DarkDamage DARK_DAMAGE = new DarkDamage();

	public static class DarkDamage extends DamageType{

	}

	public void onDeath() {

		Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
		//GLog.n(TXT_DEATH);
	}
}
