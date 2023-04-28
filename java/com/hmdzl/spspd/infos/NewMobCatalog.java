/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.hmdzl.spspd.infos;

import com.hmdzl.spspd.actors.mobs.Acidic;
import com.hmdzl.spspd.actors.mobs.Albino;
import com.hmdzl.spspd.actors.mobs.AlbinoPiranha;
import com.hmdzl.spspd.actors.mobs.Assassin;
import com.hmdzl.spspd.actors.mobs.BambooMob;
import com.hmdzl.spspd.actors.mobs.Bandit;
import com.hmdzl.spspd.actors.mobs.Bat;
import com.hmdzl.spspd.actors.mobs.BlueWraith;
import com.hmdzl.spspd.actors.mobs.BombBug;
import com.hmdzl.spspd.actors.mobs.BrokenRobot;
import com.hmdzl.spspd.actors.mobs.BrownBat;
import com.hmdzl.spspd.actors.mobs.Brute;
import com.hmdzl.spspd.actors.mobs.Crab;
import com.hmdzl.spspd.actors.mobs.DM300;
import com.hmdzl.spspd.actors.mobs.DemonFlower;
import com.hmdzl.spspd.actors.mobs.DemonGoo;
import com.hmdzl.spspd.actors.mobs.DragonRider;
import com.hmdzl.spspd.actors.mobs.DustElement;
import com.hmdzl.spspd.actors.mobs.DwarfLich;
import com.hmdzl.spspd.actors.mobs.ElderAvatar;
import com.hmdzl.spspd.actors.mobs.Eye;
import com.hmdzl.spspd.actors.mobs.Fiend;
import com.hmdzl.spspd.actors.mobs.FireElemental;
import com.hmdzl.spspd.actors.mobs.FireRabbit;
import com.hmdzl.spspd.actors.mobs.Gnoll;
import com.hmdzl.spspd.actors.mobs.GnollArcher;
import com.hmdzl.spspd.actors.mobs.GnollShaman;
import com.hmdzl.spspd.actors.mobs.GoldCollector;
import com.hmdzl.spspd.actors.mobs.GoldOrc;
import com.hmdzl.spspd.actors.mobs.GoldThief;
import com.hmdzl.spspd.actors.mobs.Golem;
import com.hmdzl.spspd.actors.mobs.Goo;
import com.hmdzl.spspd.actors.mobs.Greatmoss;
import com.hmdzl.spspd.actors.mobs.Guard;
import com.hmdzl.spspd.actors.mobs.Hybrid;
import com.hmdzl.spspd.actors.mobs.IceBug;
import com.hmdzl.spspd.actors.mobs.King;
import com.hmdzl.spspd.actors.mobs.LichDancer;
import com.hmdzl.spspd.actors.mobs.LiveMoss;
import com.hmdzl.spspd.actors.mobs.ManySkeleton;
import com.hmdzl.spspd.actors.mobs.Mimic;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.Monk;
import com.hmdzl.spspd.actors.mobs.MossySkeleton;
import com.hmdzl.spspd.actors.mobs.Musketeer;
import com.hmdzl.spspd.actors.mobs.Orc;
import com.hmdzl.spspd.actors.mobs.PatrolUAV;
import com.hmdzl.spspd.actors.mobs.Piranha;
import com.hmdzl.spspd.actors.mobs.PlagueDoctor;
import com.hmdzl.spspd.actors.mobs.PrisonWander;
import com.hmdzl.spspd.actors.mobs.Rat;
import com.hmdzl.spspd.actors.mobs.SandMob;
import com.hmdzl.spspd.actors.mobs.Scorpio;
import com.hmdzl.spspd.actors.mobs.Senior;
import com.hmdzl.spspd.actors.mobs.SewerHeart;
import com.hmdzl.spspd.actors.mobs.Shielded;
import com.hmdzl.spspd.actors.mobs.Shit;
import com.hmdzl.spspd.actors.mobs.Skeleton;
import com.hmdzl.spspd.actors.mobs.SpiderBot;
import com.hmdzl.spspd.actors.mobs.SpiderQueen;
import com.hmdzl.spspd.actors.mobs.Spinner;
import com.hmdzl.spspd.actors.mobs.Succubus;
import com.hmdzl.spspd.actors.mobs.Sufferer;
import com.hmdzl.spspd.actors.mobs.Swarm;
import com.hmdzl.spspd.actors.mobs.Tank;
import com.hmdzl.spspd.actors.mobs.Tengu;
import com.hmdzl.spspd.actors.mobs.TestMob;
import com.hmdzl.spspd.actors.mobs.Thief;
import com.hmdzl.spspd.actors.mobs.ThiefImp;
import com.hmdzl.spspd.actors.mobs.TimeKeeper;
import com.hmdzl.spspd.actors.mobs.TrollWarrior;
import com.hmdzl.spspd.actors.mobs.Vagrant;
import com.hmdzl.spspd.actors.mobs.Warlock;
import com.hmdzl.spspd.actors.mobs.Wraith;
import com.hmdzl.spspd.actors.mobs.Yog;
import com.hmdzl.spspd.actors.mobs.Zombie;
import com.hmdzl.spspd.actors.mobs.pets.Abi;
import com.hmdzl.spspd.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.actors.mobs.pets.Bunny;
import com.hmdzl.spspd.actors.mobs.pets.ButterflyPet;
import com.hmdzl.spspd.actors.mobs.pets.Chocobo;
import com.hmdzl.spspd.actors.mobs.pets.CocoCat;
import com.hmdzl.spspd.actors.mobs.pets.Datura;
import com.hmdzl.spspd.actors.mobs.pets.DogPet;
import com.hmdzl.spspd.actors.mobs.pets.DwarfBoy;
import com.hmdzl.spspd.actors.mobs.pets.Fly;
import com.hmdzl.spspd.actors.mobs.pets.FoxHelper;
import com.hmdzl.spspd.actors.mobs.pets.FrogPet;
import com.hmdzl.spspd.actors.mobs.pets.GentleCrab;
import com.hmdzl.spspd.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.actors.mobs.pets.Haro;
import com.hmdzl.spspd.actors.mobs.pets.Kodora;
import com.hmdzl.spspd.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.actors.mobs.pets.LightDragon;
import com.hmdzl.spspd.actors.mobs.pets.LitDemon;
import com.hmdzl.spspd.actors.mobs.pets.Monkey;
import com.hmdzl.spspd.actors.mobs.pets.PigPet;
import com.hmdzl.spspd.actors.mobs.pets.RedDragon;
import com.hmdzl.spspd.actors.mobs.pets.RibbonRat;
import com.hmdzl.spspd.actors.mobs.pets.Scorpion;
import com.hmdzl.spspd.actors.mobs.pets.ShadowDragon;
import com.hmdzl.spspd.actors.mobs.pets.Snake;
import com.hmdzl.spspd.actors.mobs.pets.Spider;
import com.hmdzl.spspd.actors.mobs.pets.StarKid;
import com.hmdzl.spspd.actors.mobs.pets.Stone;
import com.hmdzl.spspd.actors.mobs.pets.Velocirooster;
import com.hmdzl.spspd.actors.mobs.pets.VioletDragon;
import com.hmdzl.spspd.actors.mobs.pets.YearPet;

import java.util.Collection;
import java.util.LinkedHashMap;

public enum NewMobCatalog {
	
	SEWER,
	PRISON,
	CAVE,
	CITY,
	HALL,
	EX,
	ETC;
	
	private LinkedHashMap<Class<? extends Mob>,Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Mob>> mob(){
		return seen.keySet();
	}

	static {
		SEWER.seen.put( Rat.class , true);
		SEWER.seen.put( Albino.class , true);
		SEWER.seen.put( BrownBat.class , true);
		SEWER.seen.put( DustElement.class , true);
		SEWER.seen.put( LiveMoss.class , true);
		SEWER.seen.put( Swarm.class , true);
		SEWER.seen.put( Crab.class , true);
		SEWER.seen.put( Shit.class , true);
		SEWER.seen.put( Vagrant.class , true);
		SEWER.seen.put( PatrolUAV.class , true);
		SEWER.seen.put( Shit.class , true);
		SEWER.seen.put( Vagrant.class , true);
		SEWER.seen.put( Goo.class , true);
		SEWER.seen.put( SewerHeart.class , true);
		SEWER.seen.put( PlagueDoctor.class , true);

		PRISON.seen.put( Thief.class , true);
		PRISON.seen.put( Bandit.class , true);
		PRISON.seen.put( Gnoll.class , true);
		PRISON.seen.put( Guard.class , true);
		PRISON.seen.put( Zombie.class , true);
		PRISON.seen.put( GoldCollector.class , true);
		PRISON.seen.put( BambooMob.class , true);
		PRISON.seen.put( Assassin.class , true);
		PRISON.seen.put( TrollWarrior.class , true);
		PRISON.seen.put( FireRabbit.class , true);
		PRISON.seen.put( Tengu.class , true);
		PRISON.seen.put( PrisonWander.class , true);
		PRISON.seen.put( Tank.class , true);

		CAVE.seen.put( Bat.class , true);
		CAVE.seen.put( Skeleton.class , true);
        CAVE.seen.put( GnollShaman.class , true);
		CAVE.seen.put( Brute.class , true);
		CAVE.seen.put( Shielded.class , true);
		CAVE.seen.put( SandMob.class , true);
		CAVE.seen.put( TimeKeeper.class , true);
		CAVE.seen.put( Spinner.class , true);
		CAVE.seen.put( IceBug.class , true);
		CAVE.seen.put( BombBug.class , true);
		CAVE.seen.put( TimeKeeper.class , true);
		CAVE.seen.put( BrokenRobot.class , true);
		CAVE.seen.put( Hybrid.class , true);
		CAVE.seen.put( DM300.class , true);
		CAVE.seen.put( SpiderQueen.class , true);

		CITY.seen.put( FireElemental.class , true);
		CITY.seen.put( Warlock.class , true);
		CITY.seen.put( Monk.class , true);
		CITY.seen.put( Senior.class , true);
		CITY.seen.put( SpiderBot.class , true);
		CITY.seen.put( Golem.class , true);
		CITY.seen.put( DragonRider.class , true);
		CITY.seen.put( Musketeer.class , true);
		CITY.seen.put( DwarfLich.class , true);
		CITY.seen.put( ManySkeleton.class , true);
		//CITY.seen.put( DragonRider.class , true);
		CITY.seen.put( LichDancer.class , true);
		CITY.seen.put( ElderAvatar.class , true);
		CITY.seen.put( King.class , true);

		HALL.seen.put( Succubus.class , true);
		HALL.seen.put( Eye.class , true);
		HALL.seen.put( DemonGoo.class , true);
		HALL.seen.put( DemonFlower.class , true);
		HALL.seen.put( Sufferer.class , true);
		HALL.seen.put( ThiefImp.class , true);
		HALL.seen.put( Scorpio.class , true);
		HALL.seen.put( Acidic.class , true);
		HALL.seen.put( Yog.class , true);

		EX.seen.put( GnollArcher.class , true);
		EX.seen.put( MossySkeleton.class , true);
		EX.seen.put( AlbinoPiranha.class , true);
		EX.seen.put( GoldThief.class , true);
		EX.seen.put( BlueWraith.class , true);
		EX.seen.put( Orc.class , true);
		EX.seen.put( GoldOrc.class , true);
		EX.seen.put( Fiend.class , true);
		EX.seen.put( Wraith.class , true);
		EX.seen.put( Greatmoss.class , true);
		EX.seen.put( Piranha.class , true);
		EX.seen.put( Mimic.class , true);
		EX.seen.put( TestMob.class , true);

		ETC.seen.put( BlueDragon.class , true);
		ETC.seen.put( BugDragon.class , true);
		ETC.seen.put( Bunny.class , true);
		ETC.seen.put( ButterflyPet.class , true);
		ETC.seen.put( Chocobo.class , true);
		ETC.seen.put( CocoCat.class , true);
		ETC.seen.put(Datura.class , true);
		ETC.seen.put( DogPet.class , true);
		ETC.seen.put(Fly.class , true);
		ETC.seen.put( GentleCrab.class , true);
		ETC.seen.put( GoldDragon.class , true);
		ETC.seen.put( GreenDragon.class , true);
		ETC.seen.put(Haro.class , true);
		ETC.seen.put( Kodora.class , true);
		ETC.seen.put( LeryFire.class , true);
		ETC.seen.put( LightDragon.class , true);
		ETC.seen.put( Monkey.class , true);
		ETC.seen.put( PigPet.class , true);
		ETC.seen.put( RedDragon.class , true);
		ETC.seen.put( RibbonRat.class , true);
		ETC.seen.put( Scorpion.class , true);
		ETC.seen.put( ShadowDragon.class , true);
		ETC.seen.put( Snake.class , true);
		ETC.seen.put( Spider.class , true);
		ETC.seen.put( Stone.class , true);
		ETC.seen.put( Velocirooster.class , true);
		ETC.seen.put( VioletDragon.class , true);
		ETC.seen.put( YearPet.class , true);
		ETC.seen.put( FoxHelper.class , true);
		ETC.seen.put( DwarfBoy.class , true);
		ETC.seen.put( FrogPet.class , true);
		ETC.seen.put( StarKid.class , true);
		ETC.seen.put( LitDemon.class , true);
		ETC.seen.put( Abi.class , true);


	}

}