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
package com.hmdzl.spspd.change.actors.mobs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.watabou.utils.Random;

public class Bestiary {

	public static Mob mob(int depth) {
		@SuppressWarnings("unchecked")
		Class<? extends Mob> cl = (Class<? extends Mob>) mobClass(depth);
		try {
			return cl.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public static Mob mutable(int depth) {
		@SuppressWarnings("unchecked")
		Class<? extends Mob> cl = (Class<? extends Mob>) mobClass(depth);

		if (Random.Int(30) == 0) {
			if (cl == Rat.class) {
				cl = Albino.class;
			} else if (cl == Thief.class) {
				cl = Bandit.class;
			} else if (cl == Brute.class) {
				cl = Shielded.class;
			} else if (cl == Monk.class) {
				cl = Senior.class;
			} else if (cl == Scorpio.class) {
				cl = Acidic.class;
			}
		}

		try {
			return cl.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	private static Class<?> mobClass(int depth) {
		
		float[] chances;
		Class<?>[] classes;
		
				
		switch (depth) {
		case 1:
			chances = new float[] { 1, 1, 0.02f };
			classes = new Class<?>[] {Rat.class, BrownBat.class,
					RatBoss.class};	
			break;
		case 2:
			chances = new float[] { 1, 1, 0.7f, 0.5f};
			classes = new Class<?>[] { Rat.class, BrownBat.class,
					DustElement.class, LiveMoss.class };
			break;
		case 3:
			chances = new float[] { 1, 1, 0.8f, 0.6f, 0.4f, 0.2f };
			classes = new Class<?>[] {
					Rat.class, BrownBat.class,
					DustElement.class, LiveMoss.class,
					Swarm.class, Crab.class};
			break;
		case 4:
			chances = new float[] { 1, 1, 0.9f, 0.7f, 0.5f, 0.3f, 0.02f, 0.02f };
			classes = new Class<?>[] { Rat.class, BrownBat.class,
					DustElement.class, LiveMoss.class,
					Swarm.class, Crab.class,
					Gnoll.class,Thief.class };
			break;
		case 5:
			chances = new float[] { 1 };
			classes = new Class<?>[] { Goo.class };
			break;

		case 6:
			chances = new float[] { 0.8f, 0.8f, 1, 1, 0.5f };
			classes = new Class<?>[] {Swarm.class, Crab.class,
					 Thief.class, Gnoll.class,GreyRat.class,};
			break;
		case 7:
			chances = new float[] { 0.6f, 1, 1, 0.6f, 0.4f};
			classes = new Class<?>[] { Crab.class,
					Thief.class, Gnoll.class,GreyRat.class,
					Guard.class};
			break;
		case 8:
			chances = new float[] { 1, 1, 0.7f, 0.5f, 0.4f, 0.3f };
			classes = new Class<?>[] { Thief.class, Gnoll.class,GreyRat.class,
					Guard.class, Assassin.class, TrollWarrior.class };
			break;
		case 9:
			if (Dungeon.sporkAvail){
			chances = new float[] { 1, 1,  0.1f };
			classes = new Class<?>[] {Assassin.class, TrollWarrior.class, BanditKing.class };
			} else {
				chances = new float[] { 1, 1, 1, 0.8f, 0.6f, 0.5f, 0.02f, 0.01f };
				classes = new Class<?>[] { Thief.class, Gnoll.class,GreyRat.class,
						Guard.class, Assassin.class, TrollWarrior.class,
						Bat.class, Brute.class };
			}
			break;

		case 10:
			chances = new float[] { 1 };
			classes = new Class<?>[] { Tengu.class };
			break;

		case 11:
			chances = new float[] { 0.8f, 0.6f, 1, 0.7f, 0.5f };
			classes = new Class<?>[] { Assassin.class, TrollWarrior.class,
					Bat.class, Skeleton.class,Brute.class };
			break;
		case 12:
			chances = new float[] { 1, 0.9f, 0.7f, 0.5f, 0.3f };
			classes = new Class<?>[] { Bat.class, Skeleton.class, Brute.class,
					Shaman.class,Spinner.class };
			break;
		case 13:
			chances = new float[] { 1, 1, 0.9f, 0.7f, 0.6f, 0.4f, 0.02f };
			classes = new Class<?>[] { Bat.class, Skeleton.class, Brute.class,
					Shaman.class,Spinner.class, BrokenRobot.class,
					FireElemental.class };
			break;
		case 14:
			chances = new float[] { 1, 1, 1, 0.9f,0.8f,0.6f,0.04f, 0.02f };
			classes = new Class<?>[] {  Bat.class, Skeleton.class, Brute.class,
					Shaman.class,Spinner.class, BrokenRobot.class,
					FireElemental.class, Monk.class};
			break;

		case 15:
			chances = new float[] { 1 };
			classes = new Class<?>[] { DM300.class };
			break;

		case 16:
			chances = new float[] { 0.8f, 0.6f, 1, 1, 0.4f };
			classes = new Class<?>[] { Shaman.class,BrokenRobot.class,
					FireElemental.class, Warlock.class, Monk.class };
			break;
		case 17:
			chances = new float[] { 1, 1, 0.8f, 0.4f, 0.2f };
			classes = new Class<?>[] { FireElemental.class, Warlock.class,Monk.class,
					Golem.class, Musketeer.class};
			break;
		case 18:
			chances = new float[] { 1, 1, 1, 0.8f, 0.6f, 0.2f };
			classes = new Class<?>[] { FireElemental.class, Warlock.class,Monk.class,
					Golem.class, Musketeer.class, DwarfLich.class };
			break;
		case 19:
			
			chances = new float[] { 1, 1, 1, 1, 0.8f, 0.6f, 0.02f };
			classes = new Class<?>[] { FireElemental.class, Warlock.class,Monk.class,
					Golem.class, Musketeer.class, DwarfLich.class,
					Succubus.class};
			break;
		case 20:
			chances = new float[] { 1 };
			classes = new Class<?>[] { King.class };
			break;

		case 22:
			chances = new float[] { 0.8f,0.8f, 1, 1, 1 };
			classes = new Class<?>[] {Musketeer.class, DwarfLich.class,
					Succubus.class, Eye.class, DemonGoo.class };
			break;
		case 23:
			chances = new float[] { 1, 1, 1, 0.5f,0.5f };
			classes = new Class<?>[] { Succubus.class, Eye.class, DemonGoo.class,
					Scorpio.class,ThiefImp.class };
			break;
		case 24:
			chances = new float[] { 1, 1, 1, 1, 1, 1/* 0.1f*/};
			classes = new Class<?>[] {Succubus.class, Eye.class, DemonGoo.class,
					Scorpio.class,ThiefImp.class,/* DemonPlant.class*/
					RatBoss.class};
			break;

		case 25:
			chances = new float[] { 1 };
			classes = new Class<?>[] { Yog.class };
			break;
		
		case 27:
			chances = new float[] { 1, 0.05f };
			classes = new Class<?>[] { GnollArcher.class, ForestProtector.class};
			break;
		case 28:
			chances = new float[] { 1, 0.05f };
			classes = new Class<?>[] { MossySkeleton.class, GraveProtector.class};
			break;	
		case 29:
			chances = new float[] { 1, 0.05f };
			classes = new Class<?>[] { AlbinoPiranha.class, FishProtector.class};
			break;	
		case 30:
		    chances = new float[] { 1, 0.05f };
			classes = new Class<?>[] { GoldThief.class, VaultProtector.class};
			break;	
				
		case 31:
			chances = new float[] { 1, 0.1f };
			classes = new Class<?>[] { BlueWraith.class, DwarfLich.class};
			break;
			    
		case 32:
			chances = new float[] { 1 };
			classes = new Class<?>[] { Orc.class };
			break;
		case 33:
			chances = new float[] { 1 };
			classes = new Class<?>[] { FlyingProtector.class };
			break;
		case 35:
			chances = new float[] {1, 1 };
			classes = new Class<?>[] {GoldOrc.class, SpectralRat.class };
			break;
		case 36:
			chances = new float[] {1};
			classes = new Class<?>[] {TenguDen.class};
			break;
		case 41:
			chances = new float[] {1};
			classes = new Class<?>[] {BanditKing.class};
			break;
		case 71:
		    chances = new float[] {1};
			classes = new Class<?>[] {Dragonking.class};
			break;
		default:
			chances = new float[] { 1 };
			classes = new Class<?>[] { Eye.class };
		}
			
			
		
		return classes[Random.chances(chances)];
	}

	public static boolean isUnique(Char mob) {
		return mob instanceof Goo 
		    || mob instanceof Tengu
			|| mob instanceof DM300 
			|| mob instanceof King
			|| mob instanceof Yog.BurningFist
			|| mob instanceof Yog.RottingFist
			|| mob instanceof FetidRat
			|| mob instanceof GnollTrickster
			|| mob instanceof GreatCrab;
	}
}
