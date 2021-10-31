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
package com.hmdzl.spspd.levels;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Alchemy;
import com.hmdzl.spspd.actors.blobs.Alter;
import com.hmdzl.spspd.actors.blobs.WellWater;
import com.hmdzl.spspd.actors.mobs.AdultDragonViolet;
import com.hmdzl.spspd.actors.mobs.BombBug;
import com.hmdzl.spspd.actors.mobs.Piranha;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.TestMob;
import com.hmdzl.spspd.actors.mobs.TestMob2;
import com.hmdzl.spspd.actors.mobs.npcs.AFly;
import com.hmdzl.spspd.actors.mobs.npcs.ARealMan;
import com.hmdzl.spspd.actors.mobs.npcs.Apostle;
import com.hmdzl.spspd.actors.mobs.npcs.DreamPlayer;
import com.hmdzl.spspd.actors.mobs.npcs.GoblinPlayer;
import com.hmdzl.spspd.actors.mobs.npcs.HoneyPoooot;
import com.hmdzl.spspd.actors.mobs.npcs.Ice13;
import com.hmdzl.spspd.actors.mobs.npcs.Juh9870;
import com.hmdzl.spspd.actors.mobs.npcs.Kostis12345;
import com.hmdzl.spspd.actors.mobs.npcs.Lynn;
import com.hmdzl.spspd.actors.mobs.npcs.Millilitre;
import com.hmdzl.spspd.actors.mobs.npcs.NutPainter;
import com.hmdzl.spspd.actors.mobs.npcs.OldNewStwist;
import com.hmdzl.spspd.actors.mobs.npcs.Omicronrg9;
import com.hmdzl.spspd.actors.mobs.npcs.OtilukeNPC;
import com.hmdzl.spspd.actors.mobs.npcs.RENnpc;
import com.hmdzl.spspd.actors.mobs.npcs.RainTrainer;
import com.hmdzl.spspd.actors.mobs.npcs.SadSaltan;
import com.hmdzl.spspd.actors.mobs.npcs.SaidbySun;
import com.hmdzl.spspd.actors.mobs.npcs.Shopkeeper;
import com.hmdzl.spspd.actors.mobs.npcs.Shower;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer4;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer5;
import com.hmdzl.spspd.actors.mobs.npcs.Udawos;
import com.hmdzl.spspd.actors.mobs.npcs.TypedScroll;
import com.hmdzl.spspd.actors.mobs.npcs.G2159687;
import com.hmdzl.spspd.actors.mobs.npcs.ConsideredHamster;
import com.hmdzl.spspd.actors.mobs.npcs.NYRDS;
import com.hmdzl.spspd.actors.mobs.npcs.Evan;
import com.hmdzl.spspd.actors.mobs.npcs.UncleS;
import com.hmdzl.spspd.actors.mobs.npcs.Watabou;
import com.hmdzl.spspd.actors.mobs.npcs.Bilboldev;
import com.hmdzl.spspd.actors.mobs.npcs.HBB;
import com.hmdzl.spspd.actors.mobs.npcs.SFB;
import com.hmdzl.spspd.actors.mobs.npcs.Jinkeloid;
import com.hmdzl.spspd.actors.mobs.npcs.Rustyblade;
import com.hmdzl.spspd.actors.mobs.npcs.HeXA;
import com.hmdzl.spspd.actors.mobs.npcs.SP931;
import com.hmdzl.spspd.actors.mobs.npcs.Lery;
import com.hmdzl.spspd.actors.mobs.npcs.Lyn;
import com.hmdzl.spspd.actors.mobs.npcs.Coconut;
import com.hmdzl.spspd.actors.mobs.npcs.FruitCat;
import com.hmdzl.spspd.actors.mobs.npcs.Locastan;
import com.hmdzl.spspd.actors.mobs.npcs.Tempest102;
import com.hmdzl.spspd.actors.mobs.npcs.Dachhack;
import com.hmdzl.spspd.actors.mobs.npcs.MemoryOfSand;
import com.hmdzl.spspd.actors.mobs.npcs.StormAndRain;
import com.hmdzl.spspd.actors.mobs.npcs.HateSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.AliveFish;
import com.hmdzl.spspd.actors.mobs.npcs.LaJi;
import com.hmdzl.spspd.actors.mobs.npcs.WhiteGhost;
import com.hmdzl.spspd.actors.mobs.npcs.XixiZero;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.AdamantArmor;
import com.hmdzl.spspd.items.AdamantRing;
import com.hmdzl.spspd.items.AdamantWand;
import com.hmdzl.spspd.items.AdamantWeapon;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.misc.SkillOfAtk;
import com.hmdzl.spspd.items.misc.SkillOfDef;
import com.hmdzl.spspd.items.misc.SkillOfMig;
import com.hmdzl.spspd.items.quest.DarkGold;


import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.melee.special.Brick;
import com.hmdzl.spspd.items.weapon.melee.special.FireCracker;
import com.hmdzl.spspd.items.weapon.melee.special.HookHam;
import com.hmdzl.spspd.items.weapon.melee.special.KeyWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Lollipop;
import com.hmdzl.spspd.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.items.weapon.melee.special.RunicBlade;
import com.hmdzl.spspd.items.weapon.melee.special.SJRBMusic;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Tree;
import com.hmdzl.spspd.items.weapon.missiles.MiniMoai;
import com.hmdzl.spspd.items.weapon.missiles.MoneyPack;
import com.hmdzl.spspd.items.weapon.missiles.PocketBall;
import com.hmdzl.spspd.levels.features.Chasm;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.levels.features.HighGrass;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.plants.Phaseshift;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.Starflower;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.level;

public class ShadowEaterLevel extends Level {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
		special=false;
	}

  @Override
  public void create() {
	  
	   super.create();	
   }	
  
   	
  @Override
	protected void createItems() {

	  Mob painter = new NutPainter();
	  painter.pos = 16 + WIDTH * 21;
	  mobs.add(painter);

	}
	@Override
	public void press(int cell, Char ch) {

		TimekeepersHourglass.timeFreeze timeFreeze = null;

		if (ch != null)
			timeFreeze = ch.buff(TimekeepersHourglass.timeFreeze.class);

		boolean trap = false;
		boolean interrupt = false;



		switch (map[cell]) {			
					
        
		case Terrain.HIGH_GRASS:
			HighGrass.trample(this, cell, ch);
			break;

		case Terrain.WELL:
			WellWater.affectCell(cell);
			break;

		case Terrain.ALCHEMY:
		         Alchemy alchemy = new Alchemy();
                level.blobs.put( Alchemy.class, alchemy );
			break;
			
		case Terrain.PEDESTAL:
			if (ch == null ) {
				Alter.transmute(cell);
			}
			break;

		case Terrain.DOOR:
			Door.enter(cell);
			break;
		}

		if (trap){

			if (Dungeon.visible[cell])
				Sample.INSTANCE.play(Assets.SND_TRAP);

			if (ch == Dungeon.hero)
				Dungeon.hero.interrupt();

			set(cell, Terrain.INACTIVE_TRAP);
			GameScene.updateMap(cell);					
		} 
		
		if (interrupt){

			Dungeon.hero.interrupt();
			GameScene.updateMap(cell);					
		} 

		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(ch);
		}
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_TOWN;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	@Override
	protected boolean build() {
		
		map = TownLayouts.TOWN_LAYOUT.clone();
		decorate();

		buildFlagMaps();
		cleanWalls();
			
		entrance = 25 + WIDTH * 21;
		exit = 5 + WIDTH * 40;


		return true;
	}
	@Override
	protected void decorate() {

	}

	@Override
	protected void createMobs() {

	}
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(PrisonLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(PrisonLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(PrisonLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}

}
