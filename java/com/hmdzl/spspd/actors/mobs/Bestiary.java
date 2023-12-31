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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.mobs.npcs.Hmdzl001;
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

		if (Random.Int(8) == 0) {
			if (cl == Rat.class) {
				cl = Albino.class;
			} else if (cl == Vagrant.class) {
				cl = ExVagrant.class;
			} else if (cl == Thief.class) {
				cl = Bandit.class;
			} else if (cl == BambooMob.class) {
				cl = ExBambooMob.class;
			} else if (cl == Brute.class) {
				cl = Shielded.class;
			} else if (cl == IceBug.class) {
				cl = BombBug.class;
			} else if (cl == Monk.class) {
				cl = Senior.class;
			} else if (cl == Scorpio.class) {
				cl = Acidic.class;
			} else if (cl == Succubus.class) {
				cl = FireSuccubus.class;
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
			//case 1:
			//	chances = new float[]{1, 1, 0.02f};
			//	classes = new Class<?>[]{Rat.class, BrownBat.class, RatBoss.class};
			//	break;
			case 2:
				chances = new float[]{1, 1, 0.7f, 0.02f};
				classes = new Class<?>[]{Rat.class, BrownBat.class, DustElement.class, RatBoss.class};
				break;
			case 3:
				chances = new float[]{1, 1,1,
						0.8f, 0.6f, 0.4f,
						0.2f, 0.7f};
				classes = new Class<?>[]{
						Rat.class, BrownBat.class,Shit.class,
						DustElement.class, LiveMoss.class, Swarm.class,
						Crab.class,PatrolUAV.class};
				break;
			case 4:
				chances = new float[]{1, 1,
						1,1,1,
						1,1,1};
				classes = new Class<?>[]{BrownBat.class,Shit.class,
						DustElement.class, LiveMoss.class, Swarm.class,
						Crab.class,PatrolUAV.class,Vagrant.class};
				break;
			case 5:
				if (Random.Int(3) == 1) {
					chances = new float[]{ 1 };
					classes = new Class<?>[]{Goo.class};
				} else if (Random.Int(2) == 1) {
					chances = new float[]{ 1 };
					classes = new Class<?>[]{SewerHeart.class};
				} else {
					chances = new float[]{ 1 };
					classes = new Class<?>[]{PlagueDoctor.class};
				}
				break;

			//case 6:
			//	chances = new float[]{ 1, 1,0.5f,
			//			0.5f,0.5f};
			//	classes = new Class<?>[]{Thief.class, Gnoll.class,PatrolUAV.
			//			class,Guard.class,Vagrant.class
			//	};
			//	break;
			case 7:
				chances = new float[]{ 1, 1, 0.4f,
						0.2f,0.2f};
				classes = new Class<?>[]{Thief.class, Gnoll.class, Guard.class,
						PatrolUAV.class,Vagrant.class};
				break;
			case 8:
				chances = new float[]{1, 1, 0.5f,
						0.4f, 0.3f,
						0.5f,1f,0.5f};
				classes = new Class<?>[]{Thief.class, Gnoll.class, Guard.class,
						Assassin.class, TrollWarrior.class,
						FireRabbit.class,BambooMob.class,GoldCollector.class};
				break;
			case 9:
				if (Dungeon.sporkAvail) {
					chances = new float[]{1, 1, 0.1f,
							1,1};
					classes = new Class<?>[]{Assassin.class, TrollWarrior.class, BanditKing.class,
							GoldCollector.class,FireRabbit.class};
				} else {
					chances = new float[]{1, 1, 1,
							1,1,1,
							1,1,1};
					classes = new Class<?>[]{Thief.class, Gnoll.class, Guard.class,
							Assassin.class, TrollWarrior.class, Zombie.class
							,FireRabbit.class,BambooMob.class,GoldCollector.class};
				}
				break;

			case 10:
		if (Random.Int(3) == 1){
			chances = new float[]{ 1 };
			classes = new Class<?>[]{Tengu.class};
		}else if (Random.Int(2) == 1){
			//}else{
			chances = new float[]{ 1 };
			classes = new Class<?>[]{PrisonWander.class};
		}else{
			chances = new float[] { 1 };
			classes = new Class<?>[] { Tank.class };
		}
			break;

		//case 11:
		//	chances = new float[] { 0.8f, 0.6f, 1,
		//			0.7f, 0.5f,0.5f };
		//	classes = new Class<?>[] { Assassin.class, TrollWarrior.class, Bat.class,
		//			Skeleton.class,SandMob.class,FireRabbit.class };
		//	break;
		case 12:
			chances = new float[] { 1, 0.9f,
					0.5f, 0.3f ,0.9f,
					1 };
			classes = new Class<?>[] { Bat.class, Skeleton.class,
					GnollShaman.class,Spinner.class,SandMob.class,
					IceBug.class };
			break;
		case 13:
			chances = new float[] { 1, 1, 0.3f,
					0.7f, 0.6f, 0.7f,
					0.4f,1 ,0.3f};
			classes = new Class<?>[] { Bat.class, Skeleton.class, Brute.class,
					GnollShaman.class,Spinner.class, BrokenRobot.class,
					SandMob.class,IceBug.class,TimeKeeper.class };
			break;
		case 14:
			chances = new float[] { 1, 1, 1,
					1,1,1,
					1,1,1
			};
			classes = new Class<?>[] {  Bat.class, Skeleton.class, Brute.class,
					GnollShaman.class,Spinner.class, BrokenRobot.class,
					SandMob.class,IceBug.class,TimeKeeper.class};
			break;

		case 15:
			if (Random.Int(3) ==1) {
				chances = new float[]{ 1 };
				classes = new Class<?>[] { Hybrid.class };
			   } else if (Random.Int(2) == 1){
				chances = new float[]{ 1 };
				classes = new Class<?>[] { DM300.class };
				} else {
				chances = new float[]{ 1 };
				classes = new Class<?>[]{SpiderQueen.class};
			}
			break;

		//case 16:
			//chances = new float[] { 0.8f, 0.6f, 1, 1, 0.4f };
			//classes = new Class<?>[] { GnollShaman.class,BrokenRobot.class, FireElemental.class,Warlock.class, Musketeer.class };
			//break;
		case 17:
			chances = new float[] { 1, 0.8f,
					0.4f,0.4f ,0.2f};
			classes = new Class<?>[] { FireElemental.class, Monk.class,
					Golem.class,SpiderBot.class, Musketeer.class};
			break;
		case 18:
			chances = new float[] { 1, 1, 1, 0.8f,
					0.8f, 0.8f, 0.6f,
					0.2f,0.1f, 0.6f };
			classes = new Class<?>[] { FireElemental.class, Warlock.class,Monk.class,DragonRider.class,
					Golem.class, SpiderBot.class,Musketeer.class,
					DwarfLich.class,ManySkeleton.class,LevelChecker.class };
			break;
		case 19:
			
			chances = new float[] { 1, 1, 1, 1,
					1,1,1,1,
					1,1};
			classes = new Class<?>[] { FireElemental.class, Warlock.class,Monk.class,DragonRider.class,
					Golem.class, SpiderBot.class,Musketeer.class, DwarfLich.class ,
					ManySkeleton.class,LevelChecker.class};
			break;
		case 20:
			if (Random.Int(3) ==1) {
				chances = new float[]{ 1 };
				classes = new Class<?>[] { LichDancer.class };
			} else if (Random.Int(2) == 1){
				chances = new float[]{ 1 };
				classes = new Class<?>[] { ElderAvatar.class };
			} else {
				chances = new float[] { 1 };
				classes = new Class<?>[] { King.class };
			}

			break;
		//case 21:
				//chances = new float[] { 0.8f,0.8f, 0.8f, 1, 1, 1, 0.2f, 0.2f};
				//classes = new Class<?>[] {Musketeer.class, DwarfLich.class,DragonRider.class,
				//		Succubus.class, Eye.class, DemonGoo.class,
				//		DemonFlower.class, Sufferer.class/*,DemonTraper.class*/};
				//break;
		case 22:
			chances = new float[] { 1, 0.5f, 1,
					0.5f, 0.5f, 0.5f};
			classes = new Class<?>[] { Succubus.class, Eye.class, DemonGoo.class,
					ThiefImp.class , DemonFlower.class, Sufferer.class
					/*,DemonTraper.class*/};
			break;
		case 23:
			chances = new float[] { 1, 1, 1,
					0.5f,0.5f, 0.5f,0.5f, 0.5f };
			classes = new Class<?>[] { Succubus.class, Eye.class, DemonGoo.class,
					Scorpio.class,ThiefImp.class , DemonFlower.class, Sufferer.class, DemonRabbit.class
					/*,DemonTraper.class*/};
			break;
		case 24:
			chances = new float[] { 1, 1, 1,
					1, 1, 1, 1, 1 };
			classes = new Class<?>[] {Succubus.class, Eye.class, DemonGoo.class,
					Scorpio.class,ThiefImp.class, DemonFlower.class, Sufferer.class, DemonRabbit.class/*,DemonTraper.class*/
					};
			break;

		case 25:
			chances = new float[] { 1 };
			classes = new Class<?>[] { Yog.class };
			break;
		
		case 27:
			chances = new float[] { 1, 0.1f,0.1f };
			classes = new Class<?>[] { GnollArcher.class, ForestProtector.class,Brute.class };
			break;
		case 28:
			chances = new float[] { 1, 0.1f,0.1f };
			classes = new Class<?>[] { MossySkeleton.class, GraveProtector.class,ManySkeleton.class};
			break;	
		case 29:
			chances = new float[] { 1, 0.1f,0.1f };
			classes = new Class<?>[] { AlbinoPiranha.class, FishProtector.class,Crab.class };
			break;	
		case 30:
		    chances = new float[] { 1, 0.1f,0.1f };
			classes = new Class<?>[] { GoldThief.class, VaultProtector.class,Succubus.class};
			break;	
				
		case 31:
			chances = new float[] { 1, 0.1f,0.3f,0.3f };
			classes = new Class<?>[] { BlueWraith.class, DwarfLich.class,Zombie.class,ManySkeleton.class};
			break;
			    
		case 32:
			chances = new float[] { 1,0.05f,0.5f,0.4f };
			classes = new Class<?>[] { Orc.class, GoldOrc.class,Greatmoss.class,Brute.class};
			break;
		case 33:
			chances = new float[] { 1, 0.2f,0.2f,0.4f };
			classes = new Class<?>[] { FlyingProtector.class, FireElemental.class,LevelChecker.class,PatrolUAV.class };
			break;
		case 35:
			chances = new float[] {1, 1 };
			classes = new Class<?>[] { GoldOrc.class, Fiend.class };
			break;
		case 36:
			chances = new float[] {1};
			classes = new Class<?>[] {TenguDen.class};
			break;
		case 41:
			chances = new float[] {1};
			classes = new Class<?>[] {BanditKing.class};
			break;
		case 45:
			chances = new float[] {1 };
			classes = new Class<?>[] {TestMob.class };
			break;
		case 71:
		    chances = new float[] {1};
			classes = new Class<?>[] {Dragonking.class};
			break;
		case 84:	
			chances = new float[] {
			1,1,1,1,
			1,1,1,
			1,1,1,
			1,1,1,1,
			1,1,1,
			1,1,1,1,
			1,1,1,
			1,1,1,1,
			1,1,1,1
			};
			classes = new Class<?>[] {
			Rat.class, BrownBat.class,DustElement.class, LiveMoss.class,
			Swarm.class, Crab.class,PatrolUAV.class,
			Thief.class, Gnoll.class,TimeKeeper.class,
			Guard.class, Assassin.class, TrollWarrior.class, Zombie.class,
			Bat.class, Skeleton.class, Brute.class,
			GnollShaman.class,Spinner.class, BrokenRobot.class,SandMob.class,
			FireElemental.class, Warlock.class,Monk.class,
			Golem.class, SpiderBot.class,Musketeer.class, DwarfLich.class,
			BambooMob.class,Greatmoss.class,Piranha.class,DragonRider.class
			};
			break;			
		case 85:
		    chances = new float[] {
			1,1,1,1,
			1,1,1,
			1,1,1,
			1,1,1,1,
			1,1,1,1,
			1,1,1,1,1,
			1,1,1,1,
			1,1,1,1,
			1,
			1,1,
			1,1};
			classes = new Class<?>[] {
			Rat.class, BrownBat.class,DustElement.class, LiveMoss.class,
			Swarm.class, Crab.class,PatrolUAV.class,
			Thief.class, Gnoll.class,BambooMob.class,
			Guard.class, Assassin.class, TrollWarrior.class, Zombie.class,
			Bat.class, Skeleton.class, Brute.class,TimeKeeper.class,
			GnollShaman.class,Spinner.class, BrokenRobot.class,SandMob.class,IceBug.class,
			FireElemental.class, Warlock.class,Monk.class,DragonRider.class,
			Golem.class, SpiderBot.class,Musketeer.class, DwarfLich.class,
			Succubus.class, Eye.class, DemonGoo.class,
			Scorpio.class,ThiefImp.class, DemonFlower.class, Sufferer.class,
			BlueWraith.class,
			Orc.class,FlyingProtector.class,
            GoldOrc.class, Fiend.class };
			break;

			case 86:
				chances = new float[] {
						1,1,1,1,
						1,1,1,
						1,1,1,
						1,1,1,1,
						1,1,1,1,
						1,1,1,1,1,
						1,1,1,1,
						1,1,1,1,
						1,1,1,1,1,1,1,1,
						1,1,1,
						1};
				classes = new Class<?>[] {
						Rat.class, BrownBat.class,DustElement.class, LiveMoss.class,
						Swarm.class, Crab.class,PatrolUAV.class,
						Thief.class, Gnoll.class,BambooMob.class,
						Guard.class, Assassin.class, TrollWarrior.class, Zombie.class,
						Bat.class, Skeleton.class, Brute.class,TimeKeeper.class,
						GnollShaman.class,Spinner.class, BrokenRobot.class,SandMob.class,IceBug.class,
						FireElemental.class, Warlock.class,Monk.class,DragonRider.class,
						Golem.class, SpiderBot.class,Musketeer.class, DwarfLich.class,
						Succubus.class, Eye.class, DemonGoo.class,
						Scorpio.class,ThiefImp.class, DemonFlower.class, Sufferer.class,
						BlueWraith.class,GnollArcher.class, ForestProtector.class,MossySkeleton.class, GraveProtector.class, FishProtector.class,GoldThief.class, VaultProtector.class,
						Orc.class,FlyingProtector.class,MonsterBox.class,
						Fiend.class };
				break;
			case 90:
				chances = new float[] {1 };
				classes = new Class<?>[] { IceBall.class };
				break;
			default:
			chances = new float[] { 1 };
			classes = new Class<?>[] { Eye.class };
		}

		return classes[Random.chances(chances)];
	}

	public static Mob exmob(int depth) {
		@SuppressWarnings("unchecked")
		Class<? extends Mob> cl = (Class<? extends Mob>) exmobClass(depth);
		try {
			return cl.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	private static Class<?> exmobClass(int depth) {

		float[] chances;
		Class<?>[] classes;

		switch (depth) {
			//case 1:
			//	chances = new float[]{1};
			//	classes = new Class<?>[]{Albino.class};
			//	break;
			case 2:
				chances = new float[]{1};
				classes = new Class<?>[]{Albino.class};
				break;
			case 3:
				chances = new float[]{1,0.5f};
				classes = new Class<?>[]{Albino.class,ExVagrant.class};
				break;
			case 4:
				chances = new float[]{0.5f,1};
				classes = new Class<?>[]{Albino.class,ExVagrant.class};
				break;
			//case 6:
			//	chances = new float[]{ 1,1};
			//	classes = new Class<?>[]{Bandit.class,ExVagrant.class};
			//	break;
			case 7:
				chances = new float[]{ 0.5f,1};
				classes = new Class<?>[]{Bandit.class,ExVagrant.class};
				break;
			case 8:
				chances = new float[]{1,0.5f};
				classes = new Class<?>[]{Bandit.class, ExBambooMob.class};
				break;
			case 9:
				chances = new float[]{1,1};
				classes = new Class<?>[]{Bandit.class,ExBambooMob.class};
				break;
			//case 11:
			//	chances = new float[] { 1,1};
			//	classes = new Class<?>[] { BombBug.class, Shielded.class};
			//	break;
			case 12:
				chances = new float[] { 1,0.5f};
				classes = new Class<?>[] {BombBug.class, Shielded.class };
				break;
			case 13:
				chances = new float[] { 1,1};
				classes = new Class<?>[] { BombBug.class, Shielded.class };
				break;
			case 14:
				chances = new float[] { 0.5f,1};
				classes = new Class<?>[] { BombBug.class, Shielded.class};
				break;
			//case 16:
			//	chances = new float[] { 1};
			//	classes = new Class<?>[] { Senior.class };
			//	break;
			case 17:
				chances = new float[] { 1 };
				classes = new Class<?>[] { Senior.class};
				break;
			case 18:
				chances = new float[] { 1};
				classes = new Class<?>[] { Senior.class};
				break;
			case 19:

				chances = new float[] { 1};
				classes = new Class<?>[] { Senior.class};
				break;
			//case 21:
			//	chances = new float[] { 1};
			//	classes = new Class<?>[] { FireSuccubus.class};
			//	break;
			case 22:
				chances = new float[] { 1};
				classes = new Class<?>[] {FireSuccubus.class};
				break;
			case 23:
				chances = new float[] { 1,1 };
				classes = new Class<?>[] { Acidic.class,FireSuccubus.class};
				break;
			case 24:
				chances = new float[] { 1,1 };
				classes = new Class<?>[] {Acidic.class,FireSuccubus.class};
				break;
			case 31:
				chances = new float[] { 1 };
				classes = new Class<?>[] { BlueWraith.class};
				break;
			case 32:
				chances = new float[] { 1 };
				classes = new Class<?>[] { Orc.class};
				break;
			case 33:
				chances = new float[] { 1};
				classes = new Class<?>[] { FlyingProtector.class };
				break;
			case 55:
				chances = new float[] { 1 };
				classes = new Class<?>[] { Hmdzl001.class };
				/*chances = new float[] {
						1,1,1,1,1,
						1,1,1,1,1,
						1,1,1,1,1
				};
				classes = new Class<?>[] { Coconut.class, Jinkeloid.class, AshWolf.class, Hmdzl001.class, Apostle.class,
						Lyn.class, NutPainter.class, RainTrainer.class, Tempest102.class, WhiteGhost.class,
						FruitCat.class, BoneStar.class, AFly.class, SheepSokobanSwitch.class, HBB.class, };*/
				break;
			case 85:
				chances = new float[] { 1,1,1,1,1,
						1,1,1,1};
				classes = new Class<?>[] { Albino.class,Bandit.class,Shielded.class,BombBug.class,Senior.class,
						Acidic.class,ExVagrant.class,FireSuccubus.class ,ExBambooMob.class};
				break;

			default:
				chances = new float[] { 1 };
				classes = new Class<?>[] { RatBoss.class };
		}

		return classes[Random.chances(chances)];
	}

}
