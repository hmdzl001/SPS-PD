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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.mobs.TestMob;
import com.hmdzl.spspd.actors.mobs.npcs.Leadercn;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KnowledgeBook;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.items.armor.specialarmor.TestArmor;
import com.hmdzl.spspd.items.food.meatfood.SmallMeat;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.levels.traps.damagetrap.FireDamageTrap;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Bundle;

public class LearnLevel extends Level {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
	}
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		
		      super.restoreFromBundle(bundle);

	}

  @Override
  public void create() {

	   super.create();	
   }	
 
	@Override
	public String tilesTex() {
		return Assets.TILES_PUZZLE;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	@Override
	protected boolean build() {
		
		map = LearnRoomLayouts.LEARN_ROOM.clone();
	
		decorate();

		buildFlagMaps();
		cleanWalls();
	
		entrance = 3 + WIDTH * 3;
		exit = 0 + WIDTH * 47;
		
		placeTraps();

		return true;
	}
	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {
			 for (int i = 0; i < LENGTH; i++) {
				 if (map[i] == Terrain.GROUND_A) {
					 Leadercn npc = new Leadercn();
					 mobs.add(npc);
					 npc.pos = i;
					 Actor.occupyCell(npc);
				 }
				 if (map[i] == Terrain.EMPTY_SP) {
				 //if (map[i] == Terrain.EMPTY) {
					 TestMob mob = new TestMob();
					 mobs.add(mob);
					 mob.pos = i;
					 mob.HP = 100;
					 Actor.occupyCell(mob);
				 }
			 }
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
	protected void createItems() {
 
        addWeapon(15 + WIDTH * 2);
		addArmor(16 + WIDTH * 2);

		addHeal(40 + WIDTH * 3);
		addKey(41 + WIDTH * 3);
		addDew(42 + WIDTH * 4);
		addGold(42 + WIDTH * 5);

		addWeb(37 + WIDTH * 35);
		addWeb(38 + WIDTH * 34);
		addWeb(38 + WIDTH * 36);
		addWeb(39 + WIDTH * 35);


		addMap(14 + WIDTH *18);
		addMind(15 + WIDTH *18);

		addBook(7 + WIDTH *39);
	}
	
 private void addWeapon(int pos) {
		
		Item prize;
		
		prize = new TestWeapon();
	
		drop(prize.identify(), pos).type = Heap.Type.HEAP;
	}	
	
	 private void addArmor(int pos) {
		
		Item prize;
		
		prize = new TestArmor();
		 //prize = new TestWeapon();
		drop(prize.identify(), pos).type = Heap.Type.HEAP;
	}	
	
	
	 private void addHeal(int pos) {
		Item prize;
		 prize = new PotionOfMending();
		drop(prize, pos).type = Heap.Type.CHEST;
	}

	private void addGold(int pos) {
		Item prize;
		prize = new Gold(1000);
		drop(prize, pos).type = Heap.Type.CHEST;
	}

	private void addKey(int pos) {
		Item prize;
		 prize = new IronKey(Dungeon.depth);
		drop(prize.identify(), pos).type = Heap.Type.E_DUST;
	}

	 private void addDew(int pos) {
		Item prize;
		prize = new VioletDewdrop();
		drop(prize.identify(), pos).type = Heap.Type.E_DUST;
	}

	private void addWeb(int pos) {
		Item prize;
		prize = new SmallMeat();
		drop(prize, pos).type = Heap.Type.M_WEB;
	}

	private void addMap(int pos) {
		Item prize;
		prize = new ScrollOfMagicMapping();
		drop(prize, pos).type = Heap.Type.FOR_SALE;
	}

	private void addMind(int pos) {
		Item prize;
		prize = new PotionOfMindVision();
		drop(prize, pos).type = Heap.Type.FOR_LIFE;
	}

	private void addBook(int pos) {
		Item prize;
		prize = new KnowledgeBook();
		drop(prize, pos).type = Heap.Type.HEAP;
	}


	protected void placeTraps() {
		for (int i = 0; i < LENGTH; i ++) {
			if (map[i] == Terrain.SECRET_TRAP){
				setTrap(new FireDamageTrap().hide(), i);
			}
		}
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}
		
	

}
