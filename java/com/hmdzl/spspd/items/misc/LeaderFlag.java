/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.BookBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.DoorBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.StoneBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WallBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WaterBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WoodenBlock;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class LeaderFlag extends Item {

	public static final String AC_REMOVE = "REMOVE";
	public static final String AC_RECRUIT = "RECRUIT";
	public static final String AC_EXILE = "EXILE";
	public static final String AC_LEVY = "LEVY";

	private static final float TIME_TO_USE = 1f;

	{
		//name = "LeaderFlag";
		image = ItemSpriteSheet.LEADER_FLAG;
		defaultAction = AC_REMOVE;
		unique = true;
		 
	}
	
	public int charge = 1000;
	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}	
	
	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (charge >= 1000){
		actions.add(AC_LEVY);
		}
		
        if (charge >= 600){	
		actions.add(AC_RECRUIT);
		}

		if (hero.spp > hero.lvl && charge >= 600){
			actions.add(AC_EXILE);
		}

		if (charge >= 100){
		actions.add(AC_REMOVE);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {		
      if( action.equals( AC_REMOVE ) ){
      	if(charge < 100){
		  GLog.i(Messages.get(LeaderFlag.class, "need_time"));
		  return;
      	} else if(hero.spp < hero.lvl+5){
		  GLog.i(Messages.get(LeaderFlag.class, "need_charge"));
		  return;
      	} else {
			for (int n : Level.NEIGHBOURS4) {
				int c = hero.pos + n;

				if (c >= 0 && c < Level.getLength() && !(Dungeon.level.map[c] == Terrain.ENTRANCE) && !(Dungeon.level.map[c] == Terrain.EXIT)
                        && !(Dungeon.level.map[c] == Terrain.LOCKED_DOOR) && !(Dungeon.level.map[c] == Terrain.LOCKED_EXIT) && !(Dungeon.level.map[c] == Terrain.ALCHEMY)
                        && !(Dungeon.level.map[c] == Terrain.WELL)) {

					if (Dungeon.level.map[c] == Terrain.WALL && Level.insideMap(c)) {
						WallBlock wall = new WallBlock();
						Dungeon.level.drop(wall, hero.pos).sprite.drop();
						Level.set(c, Terrain.EMPTY);
						GameScene.updateMap(c);
						Dungeon.observe();
					}

					if (Dungeon.level.map[c] == Terrain.WATER && Level.insideMap(c)) {
						WaterBlock water = new WaterBlock();
						Dungeon.level.drop(water, hero.pos).sprite.drop();
						Level.set(c, Terrain.EMPTY);
						GameScene.updateMap(c);
						Dungeon.observe();

					}

					if (Dungeon.level.map[c] == Terrain.DOOR && Level.insideMap(c)) {
						DoorBlock door = new DoorBlock();
						Dungeon.level.drop(door, hero.pos).sprite.drop();
					}

					if (Dungeon.level.map[c] == Terrain.BOOKSHELF && Level.insideMap(c)) {
						BookBlock book = new BookBlock();
						Dungeon.level.drop(book, hero.pos).sprite.drop();
						Level.set(c, Terrain.EMPTY);
						GameScene.updateMap(c);
						Dungeon.observe();
					}

					if (Dungeon.level.map[c] == Terrain.BARRICADE && Level.insideMap(c)) {
						WoodenBlock wooden = new WoodenBlock();
						Dungeon.level.drop(wooden, hero.pos).sprite.drop();
						Level.set(c, Terrain.EMPTY);
						GameScene.updateMap(c);
						Dungeon.observe();
					}

					if (Dungeon.level.map[c] == Terrain.STATUE && Level.insideMap(c)) {
						StoneBlock stone = new StoneBlock();
						Dungeon.level.drop(stone, hero.pos).sprite.drop();
						Level.set(c, Terrain.EMPTY);
						GameScene.updateMap(c);
						Dungeon.observe();
					}

					if (Dungeon.level.map[c] == Terrain.GLASS_WALL && Level.insideMap(c)) {
						Level.set(c, Terrain.EMPTY);
						GameScene.updateMap(c);
						Dungeon.observe();
					}

					Level.set(c, Terrain.EMPTY);
					GameScene.updateMap(c);
					Dungeon.observe();
					hero.onOperateComplete();


				}
				curUser = hero;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				curUser.spendAndNext(1f);
			}
			charge -= 100;
			updateQuickslot();
			hero.onOperateComplete();
			return;
		}

		} else if( action.equals( AC_RECRUIT )){ 
		    charge-=600;
		    Dungeon.hero.spp += hero.lvl;
		  curUser = hero;
		  Sample.INSTANCE.play(Assets.SND_BURNING);
		  curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
		  curUser.spendAndNext(1f);
		} else if( action.equals( AC_EXILE )){ 
		    charge-=600;
		    Dungeon.gold = Math.max(0, (hero.spp - hero.lvl)*10 );
		    Dungeon.hero.spp += hero.lvl;
		  curUser = hero;
		  Sample.INSTANCE.play(Assets.SND_BURNING);
		  curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
		  curUser.spendAndNext(1f);
		} else if( action.equals( AC_LEVY )){ 
		    charge-=1000;
		    int c = Math.max((int)(hero.spp/50),1);
		    if (c > 0) {
				for (int i = 0; i < c; i++) {
					Dungeon.level.drop(Generator.random(), hero.pos).sprite.drop();
				}
			}
		  curUser = hero;
		  Sample.INSTANCE.play(Assets.SND_BURNING);
		  curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
		  curUser.spendAndNext(1f);
		   	
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public String status() {
		return Messages.format("%d", charge);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(LeaderFlag.class, "time",charge);
		return info;	
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	

}
