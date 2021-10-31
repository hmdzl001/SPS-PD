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
package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.blobs.ElectriShock;
import com.hmdzl.spspd.actors.blobs.FrostGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.ShockWeb;
import com.hmdzl.spspd.actors.blobs.SlowWeb;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.VenomGas;
import com.hmdzl.spspd.actors.blobs.Web;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfRain;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.actors.buffs.AflyBless;
import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.BloodImbue;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Disarm;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.Feed;
import com.hmdzl.spspd.actors.buffs.ForeverShadow;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.buffs.GasesImmunity;
import com.hmdzl.spspd.actors.buffs.GoldTouch;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.MagicImmunity;
import com.hmdzl.spspd.actors.buffs.Muscle;
import com.hmdzl.spspd.actors.buffs.Needling;
import com.hmdzl.spspd.actors.buffs.Notice;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Rhythm2;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.mobs.BrokenRobot;
import com.hmdzl.spspd.actors.mobs.DM300;
import com.hmdzl.spspd.actors.mobs.Eye;
import com.hmdzl.spspd.actors.mobs.GnollShaman;
import com.hmdzl.spspd.actors.mobs.LitTower;
import com.hmdzl.spspd.actors.mobs.Otiluke;
import com.hmdzl.spspd.actors.mobs.Shell;
import com.hmdzl.spspd.actors.mobs.Warlock;
import com.hmdzl.spspd.actors.mobs.Yog;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.levels.traps.SpearTrap;
import com.hmdzl.spspd.messages.Messages;

import java.text.DecimalFormat;
import java.util.HashSet;

public class RingOfElements extends Ring {

	{
		//name = "Ring of Elements";
		initials = 1;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats",
			new DecimalFormat("#.##").format(Math.min(300,100+100*level/15)),
			new DecimalFormat("#.##").format(Math.max(40,(100-level*2))));
		} else {
			return "???";
		}
	}	
		
	
	@Override
	protected RingBuff buff() {
		return new RingResistance();
	}

	public static float Rresist(Char target, Class effect ){
		if (getBonus(target, RingResistance.class) == 0) return 1f;

		for (Class c : RESISTS){
			if (c.isAssignableFrom(effect)){
				return (float)Math.max(0.40, ((100-getBonus(target, RingResistance.class)*2)/100));
			}
		}
		
		for (Class c : WEAKS){
			if (c.isAssignableFrom(effect)){
				return (float)Math.min(3.00,((15+getBonus(target, RingResistance.class))/15));
			}
		}		

		return 1f;
	}

	//private static final HashSet<Class<?>> EMPTY = new HashSet<Class<?>>();
	//private static final HashSet<Class<?>> FULLA = new HashSet<Class<?>>();
	//private static final HashSet<Class<?>> FULLB = new HashSet<Class<?>>();
    public static final HashSet<Class> RESISTS = new HashSet<>();
	public static final HashSet<Class> WEAKS = new HashSet<>();

	static {
		RESISTS.add(Burning.class);
		RESISTS.add(Slow.class);
		RESISTS.add(ToxicGas.class);
		RESISTS.add(VenomGas.class);
		RESISTS.add(SpearTrap.class);
		RESISTS.add(ParalyticGas.class);
		RESISTS.add(CorruptGas.class);
		RESISTS.add(DarkGas.class);
		RESISTS.add(ElectriShock.class);
		RESISTS.add(FrostGas.class);
		RESISTS.add(ConfusionGas.class);
		RESISTS.add(ShockWeb.class);
		RESISTS.add(SlowWeb.class);
		RESISTS.add(Web.class);
		RESISTS.add(Blindness.class);
		RESISTS.add(Disarm.class);
		RESISTS.add(Locked.class);
		RESISTS.add(Silent.class);
		RESISTS.add(WeatherOfRain.class);
		RESISTS.add(WeatherOfSand.class);
		RESISTS.add(WeatherOfSnow.class);
		RESISTS.add(WeatherOfSun.class);
		RESISTS.add(Poison.class);
		RESISTS.add(LightningTrap.Electricity.class);
		RESISTS.add(Warlock.class);
		RESISTS.add(GnollShaman.class);
		RESISTS.add(BrokenRobot.class);
		RESISTS.add(DM300.class);
		RESISTS.add(Eye.class);
		RESISTS.add(Otiluke.class);
		RESISTS.add(LitTower.class);
		RESISTS.add(Shell.class);
		RESISTS.add(Yog.BurningFist.class);
		RESISTS.add(Yog.PinningFist.class);

		WEAKS.add(AflyBless.class);
		WEAKS.add(Arcane.class);
		WEAKS.add(Awareness.class);
		WEAKS.add(BloodImbue.class);
		WEAKS.add(EarthImbue.class);
		WEAKS.add(Feed.class);
		WEAKS.add(ForeverShadow.class);
		WEAKS.add(FrostImbue.class);
		WEAKS.add(GasesImmunity.class);
		WEAKS.add(GoldTouch.class);
		WEAKS.add(HasteBuff.class);
	    WEAKS.add(Invisibility.class);
	    WEAKS.add(Levitation.class);
		WEAKS.add(MagicImmunity.class);
		WEAKS.add(Muscle.class);
		WEAKS.add(Needling.class);
		WEAKS.add(Notice.class);
        WEAKS.add(Recharging.class);
		WEAKS.add(Rhythm.class);
		WEAKS.add(Rhythm2.class);
		WEAKS.add(TargetShoot.class);
	}

	public class RingResistance extends RingBuff {

		/*public HashSet<Class<?>> resistances() {
			if (Random.Int(level + 2) >= 2) {
				return FULLA;
			} else {
				return EMPTY;
			}
		}

		public float durationFactor() {
			return level < 0 ? 1 : (1 + 0.5f * level) / (1 + level);
		}

		public HashSet<Class<?>> weakness() {
			if (Random.Int(level + 2) >= 2) {
				return FULLB;
			} else {
				return EMPTY;
			}
		}
		public float durationFactor2() {
			return level < 0 ? 1 : (1 +  (int)(level / 10));
		}*/
	}



}
