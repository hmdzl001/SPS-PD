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
		sname = "ELE";
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

	public static float fintime(Char target, Class effect ){
		if (getBonus(target, RingResistance.class) == 0) return 1f;

		for (Class c : REDUCE){
			if (c.isAssignableFrom(effect)){
				return (float)Math.max(0.40, ((100-getBonus(target, RingResistance.class)*2)/100));
			}
		}
		
		for (Class c : IMPROVE){
			if (c.isAssignableFrom(effect)){
				return (float)Math.min(3.00,((15+getBonus(target, RingResistance.class))/15));
			}
		}		

		return 1f;
	}

	//private static final HashSet<Class<?>> EMPTY = new HashSet<Class<?>>();
	//private static final HashSet<Class<?>> FULLA = new HashSet<Class<?>>();
	//private static final HashSet<Class<?>> FULLB = new HashSet<Class<?>>();
    public static final HashSet<Class> REDUCE = new HashSet<>();
	public static final HashSet<Class> IMPROVE = new HashSet<>();

	static {
		REDUCE.add(Burning.class);
		REDUCE.add(Slow.class);
		REDUCE.add(ToxicGas.class);
		REDUCE.add(VenomGas.class);
		REDUCE.add(SpearTrap.class);
		REDUCE.add(ParalyticGas.class);
		REDUCE.add(CorruptGas.class);
		REDUCE.add(DarkGas.class);
		REDUCE.add(ElectriShock.class);
		REDUCE.add(FrostGas.class);
		REDUCE.add(ConfusionGas.class);
		REDUCE.add(ShockWeb.class);
		REDUCE.add(SlowWeb.class);
		REDUCE.add(Web.class);
		REDUCE.add(Blindness.class);
		REDUCE.add(Disarm.class);
		REDUCE.add(Locked.class);
		REDUCE.add(Silent.class);
		REDUCE.add(WeatherOfRain.class);
		REDUCE.add(WeatherOfSand.class);
		REDUCE.add(WeatherOfSnow.class);
		REDUCE.add(WeatherOfSun.class);
		REDUCE.add(Poison.class);
		REDUCE.add(LightningTrap.Electricity.class);
		REDUCE.add(Warlock.class);
		REDUCE.add(GnollShaman.class);
		REDUCE.add(BrokenRobot.class);
		REDUCE.add(DM300.class);
		REDUCE.add(Eye.class);
		REDUCE.add(Otiluke.class);
		REDUCE.add(LitTower.class);
		REDUCE.add(Shell.class);
		REDUCE.add(Yog.BurningFist.class);
		REDUCE.add(Yog.PinningFist.class);

		IMPROVE.add(AflyBless.class);
		IMPROVE.add(Arcane.class);
		IMPROVE.add(Awareness.class);
		IMPROVE.add(BloodImbue.class);
		IMPROVE.add(EarthImbue.class);
		IMPROVE.add(Feed.class);
		IMPROVE.add(ForeverShadow.class);
		IMPROVE.add(FrostImbue.class);
		IMPROVE.add(GasesImmunity.class);
		IMPROVE.add(GoldTouch.class);
		IMPROVE.add(HasteBuff.class);
	    IMPROVE.add(Invisibility.class);
	    IMPROVE.add(Levitation.class);
		IMPROVE.add(MagicImmunity.class);
		IMPROVE.add(Muscle.class);
		IMPROVE.add(Needling.class);
		IMPROVE.add(Notice.class);
        IMPROVE.add(Recharging.class);
		IMPROVE.add(Rhythm.class);
		IMPROVE.add(Rhythm2.class);
		IMPROVE.add(TargetShoot.class);
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
