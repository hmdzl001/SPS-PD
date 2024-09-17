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

import com.hmdzl.spspd.actors.mobs.*;
import com.hmdzl.spspd.actors.mobs.chartype.AlienType;
import com.hmdzl.spspd.actors.mobs.chartype.BeastType;
import com.hmdzl.spspd.actors.mobs.chartype.DemonicType;
import com.hmdzl.spspd.actors.mobs.chartype.DragonType;
import com.hmdzl.spspd.actors.mobs.chartype.DwarfType;
import com.hmdzl.spspd.actors.mobs.chartype.ElementType;
import com.hmdzl.spspd.actors.mobs.chartype.ElfType;
import com.hmdzl.spspd.actors.mobs.chartype.FisherType;
import com.hmdzl.spspd.actors.mobs.chartype.GoblinType;
import com.hmdzl.spspd.actors.mobs.chartype.HumanType;
import com.hmdzl.spspd.actors.mobs.chartype.MechType;
import com.hmdzl.spspd.actors.mobs.chartype.OrcType;
import com.hmdzl.spspd.actors.mobs.chartype.PlantType;
import com.hmdzl.spspd.actors.mobs.chartype.TrollType;
import com.hmdzl.spspd.actors.mobs.chartype.UndeadType;
import com.hmdzl.spspd.actors.mobs.chartype.UnknowType;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftAFly;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftAHorse;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftAshWolf;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftBaMech;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftBegger;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftBunnyKeeper;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftCoconut;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftFlyLing;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftFruitWorker;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftMeatSeller;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftRen;
import com.hmdzl.spspd.actors.mobs.giftnpc.GiftTorch;
import com.hmdzl.spspd.actors.mobs.npcs.AFly;
import com.hmdzl.spspd.actors.mobs.npcs.ARealMan;
import com.hmdzl.spspd.actors.mobs.npcs.ATV9;
import com.hmdzl.spspd.actors.mobs.npcs.Apostle;
import com.hmdzl.spspd.actors.mobs.npcs.AshWolf;
import com.hmdzl.spspd.actors.mobs.npcs.Bilboldev;
import com.hmdzl.spspd.actors.mobs.npcs.BlackMeow;
import com.hmdzl.spspd.actors.mobs.npcs.Blacksmith;
import com.hmdzl.spspd.actors.mobs.npcs.Blacksmith2;
import com.hmdzl.spspd.actors.mobs.npcs.BoneStar;
import com.hmdzl.spspd.actors.mobs.npcs.CatSheep;
import com.hmdzl.spspd.actors.mobs.npcs.Coconut;
import com.hmdzl.spspd.actors.mobs.npcs.Coconut2;
import com.hmdzl.spspd.actors.mobs.npcs.ConsideredHamster;
import com.hmdzl.spspd.actors.mobs.npcs.Dachhack;
import com.hmdzl.spspd.actors.mobs.npcs.DreamPlayer;
import com.hmdzl.spspd.actors.mobs.npcs.Evan;
import com.hmdzl.spspd.actors.mobs.npcs.FlyLing;
import com.hmdzl.spspd.actors.mobs.npcs.FruitCat;
import com.hmdzl.spspd.actors.mobs.npcs.G2159687;
import com.hmdzl.spspd.actors.mobs.npcs.Ghost;
import com.hmdzl.spspd.actors.mobs.npcs.GoblinPlayer;
import com.hmdzl.spspd.actors.mobs.npcs.HBB;
import com.hmdzl.spspd.actors.mobs.npcs.HateSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.HeXA;
import com.hmdzl.spspd.actors.mobs.npcs.Hmdzl001;
import com.hmdzl.spspd.actors.mobs.npcs.HoneyPoooot;
import com.hmdzl.spspd.actors.mobs.npcs.Ice13;
import com.hmdzl.spspd.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.actors.mobs.npcs.ImpShopkeeper;
import com.hmdzl.spspd.actors.mobs.npcs.Jinkeloid;
import com.hmdzl.spspd.actors.mobs.npcs.Juh9870;
import com.hmdzl.spspd.actors.mobs.npcs.Kostis12345;
import com.hmdzl.spspd.actors.mobs.npcs.LaJi;
import com.hmdzl.spspd.actors.mobs.npcs.Leadercn;
import com.hmdzl.spspd.actors.mobs.npcs.Lery;
import com.hmdzl.spspd.actors.mobs.npcs.Locastan;
import com.hmdzl.spspd.actors.mobs.npcs.Lyn;
import com.hmdzl.spspd.actors.mobs.npcs.Lynn;
import com.hmdzl.spspd.actors.mobs.npcs.MemoryOfSand;
import com.hmdzl.spspd.actors.mobs.npcs.Millilitre;
import com.hmdzl.spspd.actors.mobs.npcs.Mtree;
import com.hmdzl.spspd.actors.mobs.npcs.NYRDS;
import com.hmdzl.spspd.actors.mobs.npcs.NewPlayer;
import com.hmdzl.spspd.actors.mobs.npcs.NutPainter;
import com.hmdzl.spspd.actors.mobs.npcs.Omicronrg9;
import com.hmdzl.spspd.actors.mobs.npcs.OtilukeNPC;
import com.hmdzl.spspd.actors.mobs.npcs.RENnpc;
import com.hmdzl.spspd.actors.mobs.npcs.RainTrainer;
import com.hmdzl.spspd.actors.mobs.npcs.RatKing;
import com.hmdzl.spspd.actors.mobs.npcs.Ravenwolf;
import com.hmdzl.spspd.actors.mobs.npcs.Rustyblade;
import com.hmdzl.spspd.actors.mobs.npcs.SFB;
import com.hmdzl.spspd.actors.mobs.npcs.SP931;
import com.hmdzl.spspd.actors.mobs.npcs.SadSaltan;
import com.hmdzl.spspd.actors.mobs.npcs.SaidbySun;
import com.hmdzl.spspd.actors.mobs.npcs.Shopkeeper;
import com.hmdzl.spspd.actors.mobs.npcs.Shower;
import com.hmdzl.spspd.actors.mobs.npcs.StormAndRain;
import com.hmdzl.spspd.actors.mobs.npcs.Tempest102;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer1;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer2;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer4;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer5;
import com.hmdzl.spspd.actors.mobs.npcs.TypedScroll;
import com.hmdzl.spspd.actors.mobs.npcs.Udawos;
import com.hmdzl.spspd.actors.mobs.npcs.UncleS;
import com.hmdzl.spspd.actors.mobs.npcs.Wandmaker;
import com.hmdzl.spspd.actors.mobs.npcs.Watabou;
import com.hmdzl.spspd.actors.mobs.npcs.WhiteGhost;
import com.hmdzl.spspd.actors.mobs.npcs.XixiZero;
import com.hmdzl.spspd.actors.mobs.pets.Abi;
import com.hmdzl.spspd.actors.mobs.pets.BatPet;
import com.hmdzl.spspd.actors.mobs.pets.BlueGirl;
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

	HUMAN,
	ORC,
	FISHER,
	ELF,
	DWARF,
	TROLL,
	DEMONIC,

	GOBLIN,
	BEAST,
	DRAGON,
	PLANT,
	ELEMENT,
	MECH,
	UNDEAD,

	ALIEN,
	UNKNOW,
	EX_MOB,
	BOSS,
	NPC1,
	NPC2,
	PET

	;
	
	private LinkedHashMap<Class<? extends Mob>,Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Mob>> mob(){
		return seen.keySet();
	}

	static {
		HUMAN.seen.put( HumanType.class , true);
		HUMAN.seen.put( Assassin.class , true);
		HUMAN.seen.put( ExVagrant.class , true);
		HUMAN.seen.put( Guard.class , true);
		HUMAN.seen.put( PlagueDoctor.class , true);
		HUMAN.seen.put( PrisonWander.class , true);
		HUMAN.seen.put( Sufferer.class , true);
		HUMAN.seen.put( Tengu.class , true);
		HUMAN.seen.put( TenguDen.class , true);
		HUMAN.seen.put( Vagrant.class , true);
		HUMAN.seen.put( VaultProtector.class , true);

		ORC.seen.put( OrcType.class , true);
		ORC.seen.put( Brute.class , true);
		ORC.seen.put( Gnoll.class , true);
		ORC.seen.put( DemonRabbit.class , true);
		ORC.seen.put( FireRabbit.class , true);
		ORC.seen.put( GnollArcher.class , true);
		ORC.seen.put( GnollShaman.class , true);
		ORC.seen.put( GnollTrickster.class , true);
		ORC.seen.put( GoldOrc.class , true);
		ORC.seen.put( Orc.class , true);
		ORC.seen.put( Shielded.class , true);
		ORC.seen.put( UIcecorps.class , true);
		ORC.seen.put( UIcecorps2.class , true);

		FISHER.seen.put( FisherType.class , true);
		FISHER.seen.put( AlbinoPiranha.class , true);
        FISHER.seen.put( Crab.class , true);
		FISHER.seen.put( CrabKing.class , true);
		FISHER.seen.put( Piranha.class , true);
		FISHER.seen.put( HermitCrab.class , true);
		FISHER.seen.put( GreatCrab.class , true);

		ELF.seen.put( ElfType.class , true);
		ELF.seen.put( Bandit.class , true);
		ELF.seen.put( BanditKing.class , true);
		ELF.seen.put( BlueCat.class , true);
		ELF.seen.put( GoldThief.class , true);
		ELF.seen.put( Shit.class , true);
		ELF.seen.put( ThiefKing.class , true);

		DWARF.seen.put( DwarfType.class , true);
		DWARF.seen.put( DwarfLich.class , true);
		DWARF.seen.put( King.class , true);
		DWARF.seen.put( Monk.class , true);
		DWARF.seen.put( Musketeer.class , true);
		DWARF.seen.put( Senior.class , true);
		DWARF.seen.put( Warlock.class , true);

		TROLL.seen.put( TrollType.class , true);
		TROLL.seen.put( GraveProtector.class , true);
		TROLL.seen.put( TrollWarrior.class , true);

		DEMONIC.seen.put( DemonicType.class , true);
		DEMONIC.seen.put( Acidic.class , true);
		DEMONIC.seen.put( Albino.class , true);
		DEMONIC.seen.put( DemonFlower.class , true);
		DEMONIC.seen.put( DemonRabbit.class , true);
		//DEMONIC.seen.put( DemonSummoner.class , true);
		DEMONIC.seen.put(Eye.class , true);
		DEMONIC.seen.put( Fiend.class , true);
		DEMONIC.seen.put(FireSuccubus.class , true);
		DEMONIC.seen.put( GoldOrc.class , true);
		//DEMONIC.seen.put( LotusSummoner.class , true);
		DEMONIC.seen.put( MagicEye.class , true);
		DEMONIC.seen.put(Succubus.class , true);
		DEMONIC.seen.put( Sufferer.class , true);
		DEMONIC.seen.put( ThiefImp.class , true);

		GOBLIN.seen.put( GoblinType.class , true);
		GOBLIN.seen.put( GoldCollector.class , true);
		GOBLIN.seen.put( Thief.class , true);

		BEAST.seen.put( BeastType.class , true);
		BEAST.seen.put( Acidic.class , true);
		BEAST.seen.put( Albino.class , true);
		BEAST.seen.put( BombBug.class , true);
		BEAST.seen.put( BrownBat.class , true);
		BEAST.seen.put( FetidRat.class , true);
		BEAST.seen.put( GreyRat.class , true);
		BEAST.seen.put( Hybrid.class , true);
		BEAST.seen.put( IceBug.class , true);
		BEAST.seen.put( Rat.class , true);
		BEAST.seen.put( RatBoss.class , true);
		BEAST.seen.put( Scorpio.class , true);
		BEAST.seen.put( SpiderBot.class , true);
		BEAST.seen.put( SpiderQueen.class , true);
		BEAST.seen.put( SpiderQueen.SpiderEgg.class , true);
		BEAST.seen.put( SpiderQueen.SpiderGold.class , true);
		BEAST.seen.put( SpiderQueen.SpiderJumper.class , true);
		BEAST.seen.put( SpiderQueen.SpiderMind.class , true);
		BEAST.seen.put( SpiderQueen.SpiderWorker.class , true);
		BEAST.seen.put( Spinner.class , true);
		BEAST.seen.put( Swarm.class , true);

		DRAGON.seen.put( DragonType.class , true);
		DRAGON.seen.put( AdultDragonViolet.class , true);
		DRAGON.seen.put( Dragonking.class , true);
		DRAGON.seen.put( DragonRider.class , true);

		PLANT.seen.put( PlantType.class , true);
		PLANT.seen.put( BambooMob.class , true);
		PLANT.seen.put( DemonFlower.class , true);
		PLANT.seen.put(ExBambooMob.class , true);
		PLANT.seen.put( ForestProtector.class , true);
		PLANT.seen.put( Greatmoss.class , true);
		PLANT.seen.put( LiveMoss.class , true);
		PLANT.seen.put( SewerHeart.class , true);
		PLANT.seen.put( SewerHeart.SewerLasher.class , true);
		PLANT.seen.put( TestMob.class , true);
		PLANT.seen.put( UKing.class , true);

		ELEMENT.seen.put( ElementType.class , true);
		ELEMENT.seen.put( ArmorStatue.class , true);
		ELEMENT.seen.put( DemonGoo.class , true);
		ELEMENT.seen.put( DustElement.class , true);
		ELEMENT.seen.put( Fiend.class , true);
		ELEMENT.seen.put( FireElemental.class , true);
		ELEMENT.seen.put( FishProtector.class , true);
		ELEMENT.seen.put( FlyingProtector.class , true);
		ELEMENT.seen.put( Goo.PoisonGoo.class , true);
		ELEMENT.seen.put( IceBall.class , true);
		ELEMENT.seen.put( MagicEye.class , true);
		ELEMENT.seen.put( Otiluke.class , true);
		ELEMENT.seen.put( SandMob.class , true);
		ELEMENT.seen.put( Statue.class , true);
		ELEMENT.seen.put( UGoo.class , true);
		ELEMENT.seen.put( UGoo.EarthGoo.class , true);
		ELEMENT.seen.put( UGoo.FireGoo.class , true);
		ELEMENT.seen.put( UGoo.IceGoo.class , true);
		ELEMENT.seen.put( UGoo.ShockGoo.class , true);
		ELEMENT.seen.put(  Yog.BurningFist.class , true);
		ELEMENT.seen.put(  Yog.InfectingFist.class , true);
		ELEMENT.seen.put(  Yog.PinningFist.class , true);
		ELEMENT.seen.put(  Yog.RottingFist.class , true);

		MECH.seen.put( MechType.class , true);
		MECH.seen.put( BrokenRobot.class , true);
		MECH.seen.put( DM300.class , true);
		MECH.seen.put( DM300.Tower.class , true);
		MECH.seen.put( ElderAvatar.TheMech.class , true);
		MECH.seen.put( Golem.class , true);
		MECH.seen.put( Hybrid.class , true);
		MECH.seen.put( IceBall.class , true);
		MECH.seen.put( LevelChecker.class , true);
		MECH.seen.put( LichDancer.BatteryTomb.class , true);
		MECH.seen.put( LichDancer.LinkXBomb.class , true);
		MECH.seen.put( LichDancer.LinkAddBomb.class , true);
		MECH.seen.put( LitTower.class , true);
		MECH.seen.put( MineSentinel.class , true);
		MECH.seen.put( OrbOfZotMob.class , true);
		MECH.seen.put( PatrolUAV.class , true);
		MECH.seen.put( PrisonWander.SeekBombP.class , true);
		MECH.seen.put( Sentinel.class , true);
		MECH.seen.put( Shell.class , true);
		MECH.seen.put( SokobanSentinel.class , true);
		MECH.seen.put( TestMob2.class , true);
		MECH.seen.put( UDM300.class , true);
		MECH.seen.put( UDM300.SeekBomb.class , true);

		UNDEAD.seen.put( UndeadType.class , true);
		UNDEAD.seen.put( BlueWraith.class , true);
		UNDEAD.seen.put( DwarfLich.class , true);
		UNDEAD.seen.put( Hybrid.class , true);
		UNDEAD.seen.put( King.Undead.class , true);
		UNDEAD.seen.put( LichDancer.class , true);
		UNDEAD.seen.put(ManySkeleton.class , true);
		UNDEAD.seen.put( MossySkeleton.class , true);
		UNDEAD.seen.put(RedWraith.class , true);
		UNDEAD.seen.put( Skeleton.class , true);
		UNDEAD.seen.put( SkeletonHand1.class , true);
		UNDEAD.seen.put( SkeletonHand2.class , true);
		UNDEAD.seen.put(SkeletonKing.class , true);
		UNDEAD.seen.put( SommonSkeleton.class , true);
		UNDEAD.seen.put( Tank.class , true);
		UNDEAD.seen.put( Wraith.class , true);
		UNDEAD.seen.put( Zombie.class , true);

		ALIEN.seen.put( AlienType.class , true);
		ALIEN.seen.put( ElderAvatar.class , true);
		ALIEN.seen.put( ElderAvatar.TheHunter.class , true);
		ALIEN.seen.put( ElderAvatar.TheMonk.class , true);
		ALIEN.seen.put( ElderAvatar.TheMech.class , true);
		ALIEN.seen.put( ElderAvatar.TheWarlock.class , true);
		ALIEN.seen.put( Hybrid.class , true);

		UNKNOW.seen.put( UnknowType.class , true);
		UNKNOW.seen.put( ElderAvatar.Obelisk.class , true);
		UNKNOW.seen.put( GhostPhoto.class , true);
		UNKNOW.seen.put( Goo.class , true);
		UNKNOW.seen.put( Hybrid.Mixers.class , true);
		UNKNOW.seen.put( King.DwarfKingTomb.class , true);
		UNKNOW.seen.put( Mimic.class , true);
		UNKNOW.seen.put( MonsterBox.class , true);
		UNKNOW.seen.put( PlagueDoctor.ShadowRat.class , true);
		UNKNOW.seen.put( ShadowYog.class , true);
		UNKNOW.seen.put( TimeKeeper.class , true);
		UNKNOW.seen.put( UAmulet.class , true);
		//UNKNOW.seen.put( UAmulet.DarkMirror.class , true);
		UNKNOW.seen.put( UGoo.class , true);
		UNKNOW.seen.put( UGoo.ShockGoo.class , true);
		UNKNOW.seen.put( UGoo.IceGoo.class , true);
		UNKNOW.seen.put( UGoo.FireGoo.class , true);
		UNKNOW.seen.put( UGoo.EarthGoo.class , true);
		UNKNOW.seen.put( UYog.class , true);
		UNKNOW.seen.put( Virus.class , true);
		UNKNOW.seen.put( Yog.class , true);
		UNKNOW.seen.put( Yog.Larva.class , true);
		UNKNOW.seen.put( Zot.class , true);
		UNKNOW.seen.put( ZotPhase.class , true);


		EX_MOB.seen.put( Albino.class , true);
		EX_MOB.seen.put( Acidic.class , true);
		EX_MOB.seen.put( Shielded.class , true);
		EX_MOB.seen.put( Senior.class , true);
		EX_MOB.seen.put( Bandit.class , true);
		EX_MOB.seen.put( ExBambooMob.class , true);
		EX_MOB.seen.put( ExVagrant.class , true);
		EX_MOB.seen.put( FireSuccubus.class , true);
		EX_MOB.seen.put( IceBug.class , true);
		EX_MOB.seen.put( GoldOrc.class , true);


		BOSS.seen.put( Goo.class , true);
		BOSS.seen.put( Goo.PoisonGoo.class , true);
		BOSS.seen.put( SewerHeart.class , true);
		BOSS.seen.put( SewerHeart.SewerLasher.class , true);
		BOSS.seen.put( PlagueDoctor.class , true);
		BOSS.seen.put( PlagueDoctor.ShadowRat.class , true);
		BOSS.seen.put( Tengu.class , true);
		BOSS.seen.put( TenguDen.class , true);
		BOSS.seen.put( PrisonWander.class , true);
		BOSS.seen.put( PrisonWander.SeekBombP.class , true);
		BOSS.seen.put( Tank.class , true);
		BOSS.seen.put( DM300.class , true);
		BOSS.seen.put( DM300.Tower.class , true);
		BOSS.seen.put( Hybrid.class , true);
		BOSS.seen.put( Hybrid.Mixers.class , true);
		BOSS.seen.put( SpiderQueen.class , true);
		BOSS.seen.put( SpiderQueen.SpiderWorker.class , true);
		BOSS.seen.put( SpiderQueen.SpiderMind.class , true);
		BOSS.seen.put( SpiderQueen.SpiderJumper.class , true);
		BOSS.seen.put( SpiderQueen.SpiderGold.class , true);
		BOSS.seen.put( SpiderQueen.SpiderEgg.class , true);
		BOSS.seen.put( King.class , true);
		BOSS.seen.put( King.DwarfKingTomb.class , true);
		BOSS.seen.put( ElderAvatar.class , true);
		BOSS.seen.put( ElderAvatar.Obelisk.class , true);
		BOSS.seen.put( ElderAvatar.TheHunter.class , true);
		BOSS.seen.put( ElderAvatar.TheMech.class , true);
		BOSS.seen.put( ElderAvatar.TheMonk.class , true);
		BOSS.seen.put( ElderAvatar.TheWarlock.class , true);
		BOSS.seen.put( LichDancer.class , true);
		BOSS.seen.put( LichDancer.LinkAddBomb.class , true);
		BOSS.seen.put( LichDancer.LinkXBomb.class , true);
		BOSS.seen.put( LichDancer.BatteryTomb.class , true);
		BOSS.seen.put( Yog.class , true);
		BOSS.seen.put( Yog.Larva.class , true);
		BOSS.seen.put( Yog.RottingFist.class , true);
		BOSS.seen.put( Yog.PinningFist.class , true);
		BOSS.seen.put( Yog.InfectingFist.class , true);
		BOSS.seen.put( Yog.BurningFist.class , true);
		BOSS.seen.put( ShadowYog.class , true);
		BOSS.seen.put( Otiluke.class , true);
		BOSS.seen.put( LitTower.class , true);
		BOSS.seen.put( Zot.class , true);
		BOSS.seen.put( ZotPhase.class , true);
		BOSS.seen.put( GnollKing.class , true);
		BOSS.seen.put( BanditKing.class , true);
		BOSS.seen.put( CrabKing.class , true);
		BOSS.seen.put( SkeletonKing.class , true);
		BOSS.seen.put( Dragonking.class , true);
		BOSS.seen.put( UGoo.class , true);
		BOSS.seen.put( UTengu.class , true);
		BOSS.seen.put( UDM300.class , true);
		BOSS.seen.put( UKing.class , true);
		BOSS.seen.put( UIcecorps.class , true);
		BOSS.seen.put( UIcecorps2.class , true);
		BOSS.seen.put( UYog.class , true);
		BOSS.seen.put( UAmulet.class , true);


		NPC1.seen.put( AFly.class , true);
		NPC1.seen.put( Apostle.class , true);
		NPC1.seen.put( ARealMan.class , true);
		NPC1.seen.put( AshWolf.class , true);
		NPC1.seen.put( ATV9.class , true);
		NPC1.seen.put( Bilboldev.class, true);
		NPC1.seen.put( BlackMeow.class , true);
		NPC1.seen.put( Blacksmith.class , true);
		NPC1.seen.put( Blacksmith2.class , true);
		NPC1.seen.put( BoneStar.class , true);
		NPC1.seen.put( CatSheep.class , true);
		NPC1.seen.put( Coconut.class , true);
		NPC1.seen.put( Coconut2.class , true);
		NPC1.seen.put( ConsideredHamster.class , true);
		NPC1.seen.put( Dachhack.class , true);
		NPC1.seen.put( DreamPlayer.class , true);
		NPC1.seen.put( Evan.class, true);
		NPC1.seen.put( FlyLing.class , true);
		NPC1.seen.put( FruitCat.class , true);
		NPC1.seen.put( G2159687.class , true);
		NPC1.seen.put( Ghost.class , true);
		NPC1.seen.put( GoblinPlayer.class , true);
		NPC1.seen.put( HateSokoban.class , true);
		NPC1.seen.put( HBB.class , true);
		NPC1.seen.put( HeXA.class , true);
		NPC1.seen.put( Hmdzl001.class , true);
		NPC1.seen.put( HoneyPoooot.class , true);
		NPC1.seen.put( Ice13.class, true);
		NPC1.seen.put( Imp.class , true);
		NPC1.seen.put( ImpShopkeeper.class , true);
		NPC1.seen.put( Jinkeloid.class , true);
		NPC1.seen.put( Juh9870.class , true);
		NPC1.seen.put( Kostis12345.class , true);
		NPC1.seen.put( LaJi.class , true);
		NPC1.seen.put( Leadercn.class , true);
		NPC1.seen.put( Lery.class , true);
		NPC1.seen.put( Locastan.class , true);
		NPC1.seen.put( Lyn.class , true);
		NPC1.seen.put( Lynn.class, true);
		NPC1.seen.put( MemoryOfSand.class , true);
		NPC1.seen.put( Millilitre.class , true);
		NPC1.seen.put( Mtree.class , true);
		NPC1.seen.put( NewPlayer.class , true);
		NPC1.seen.put( NutPainter.class , true);
		NPC1.seen.put( NYRDS.class , true);
		//NPC1.seen.put( OldNewStwist.class , true);
		NPC1.seen.put( Omicronrg9.class , true);
		NPC1.seen.put( OtilukeNPC.class , true);
		NPC1.seen.put( RainTrainer.class , true);
		NPC1.seen.put( RatKing.class, true);
		NPC1.seen.put( Ravenwolf.class , true);
		NPC1.seen.put( RENnpc.class , true);
		NPC1.seen.put( Rustyblade.class , true);
		NPC1.seen.put( SadSaltan.class , true);
		NPC1.seen.put( SaidbySun.class , true);
		NPC1.seen.put( SFB.class , true);
		NPC1.seen.put( Shopkeeper.class , true);
		NPC1.seen.put( Shower.class , true);
		NPC1.seen.put( SP931.class , true);
		NPC1.seen.put( StormAndRain.class , true);
		NPC1.seen.put( Tempest102.class, true);
		NPC1.seen.put( Tinkerer1.class , true);
		NPC1.seen.put( Tinkerer2.class , true);
		NPC1.seen.put( Tinkerer4.class , true);
		NPC1.seen.put( Tinkerer5.class , true);
		NPC1.seen.put( TypedScroll.class , true);
		NPC1.seen.put( Udawos.class , true);
		NPC1.seen.put( UncleS.class , true);
		NPC1.seen.put( Wandmaker.class , true);
		NPC1.seen.put( Watabou.class , true);
		NPC1.seen.put( WhiteGhost.class , true);
		NPC1.seen.put( XixiZero.class, true);

		NPC2.seen.put( GiftAFly.class , true);
		NPC2.seen.put( GiftAshWolf.class , true);
		NPC2.seen.put( GiftBaMech.class , true);
		NPC2.seen.put( GiftBegger.class , true);
		NPC2.seen.put( GiftBunnyKeeper.class , true);
		NPC2.seen.put( GiftCoconut.class , true);
		NPC2.seen.put( GiftFlyLing.class , true);
		NPC2.seen.put( GiftFruitWorker.class , true);
		NPC2.seen.put( GiftMeatSeller.class , true);
		NPC2.seen.put( GiftRen.class , true);
		NPC2.seen.put( GiftTorch.class , true);
		NPC2.seen.put( GiftAHorse.class , true);

		PET.seen.put( Abi.class , true);
		PET.seen.put( BlueGirl.class , true);
		PET.seen.put( BugDragon.class , true);
		PET.seen.put( Bunny.class , true);
		PET.seen.put( ButterflyPet.class , true);
		PET.seen.put( Chocobo.class , true);
		PET.seen.put( CocoCat.class , true);
		PET.seen.put( Datura.class , true);
		PET.seen.put( DogPet.class , true);
		PET.seen.put( Fly.class , true);
		PET.seen.put( GentleCrab.class , true);
		PET.seen.put( GoldDragon.class , true);
		PET.seen.put( GreenDragon.class , true);
		PET.seen.put(Haro.class , true);
		PET.seen.put( Kodora.class , true);
		PET.seen.put( LeryFire.class , true);
		PET.seen.put( LightDragon.class , true);
		PET.seen.put( Monkey.class , true);
		PET.seen.put( PigPet.class , true);
		PET.seen.put( RedDragon.class , true);
		PET.seen.put( RibbonRat.class , true);
		PET.seen.put( Scorpion.class , true);
		PET.seen.put( ShadowDragon.class , true);
		PET.seen.put( Snake.class , true);
		PET.seen.put( Spider.class , true);
		PET.seen.put( Stone.class , true);
		PET.seen.put( Velocirooster.class , true);
		PET.seen.put( VioletDragon.class , true);
		PET.seen.put( YearPet.class , true);
		PET.seen.put( FoxHelper.class , true);
		PET.seen.put( DwarfBoy.class , true);
		PET.seen.put( FrogPet.class , true);
		PET.seen.put( StarKid.class , true);
		PET.seen.put( LitDemon.class , true);
		PET.seen.put( BatPet.class , true);
	}

}