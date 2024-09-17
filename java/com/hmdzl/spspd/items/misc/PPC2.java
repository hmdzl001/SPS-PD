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
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.mindbuff.AmokMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.CrazyMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.GoldMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.HarmMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.HopeMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.KeepMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.LoseMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.SleepMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.TerrorMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.WeakMind;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.BookBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.DoorBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.StoneBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WallBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WaterBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WoodenBlock;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PPC2 extends Item {

	public static final String AC_TRY = "TRY";
	public static final String AC_HEAL = "HEAL";
	public static final String AC_MIND = "MIND";

	public static final float TIME_TO_MINE = 3;
	{
		//name = "PPC2";
		image = ItemSpriteSheet.SPECIAL_PC;
		defaultAction = AC_TRY;
		unique = true;
		 
	}
	
	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
	
		actions.add(AC_TRY);
		if (hero.spp > hero.lvl*2) {
			actions.add(AC_HEAL);
		}
		actions.add(AC_MIND);
		
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}
	

	
	@Override
	public void execute( final Hero hero, String action ) {
		if (action.equals(AC_TRY)) {
			if (Dungeon.hero.isHungry() || Dungeon.hero.isStarving()){
				GLog.i(Messages.get(this, "break"));
				return;
			}

			for (int i = 0; i < Floor.NEIGHBOURS4.length; i++) {

				final int pos = hero.pos + Floor.NEIGHBOURS8[i];
				if (Dungeon.depth.map[pos] == Terrain.WALL && Floor.insideMap(pos)) {

					hero.spend(TIME_TO_MINE);
					hero.busy();

					hero.sprite.attack(pos, new Callback() {

						@Override
						public void call() {

							CellEmitter.center(pos).burst(
									Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);

							Floor.set(pos, Terrain.EMBERS);
							GameScene.updateMap(pos);

							WallBlock wall = new WallBlock();


                            if (Random.Int(30)==1){
								Dungeon.depth.drop(new Gold(50), hero.pos).sprite.drop();
								Dungeon.depth.drop(wall, hero.pos).sprite.drop();
							}
							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}  else if (Dungeon.depth.map[pos] == Terrain.DOOR && Floor.insideMap(pos)) {

					hero.spend(TIME_TO_MINE);
					hero.busy();

					hero.sprite.attack(pos, new Callback() {

						@Override
						public void call() {

							CellEmitter.center(pos).burst(
									Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);

							Floor.set(pos, Terrain.EMBERS);
							GameScene.updateMap(pos);

							DoorBlock door = new DoorBlock();

							if (Random.Int(30)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.SEED), hero.pos).sprite.drop();
								Dungeon.depth.drop(door, hero.pos).sprite.drop();
							}

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}  else if (Dungeon.depth.map[pos] == Terrain.BOOKSHELF && Floor.insideMap(pos)) {

					hero.spend(TIME_TO_MINE);
					hero.busy();

					hero.sprite.attack(pos, new Callback() {

						@Override
						public void call() {

							CellEmitter.center(pos).burst(
									Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);

							Floor.set(pos, Terrain.EMBERS);
							GameScene.updateMap(pos);

							BookBlock book = new BookBlock();

							if (Random.Int(30)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.SCROLL), hero.pos).sprite.drop();
								Dungeon.depth.drop(book, hero.pos).sprite.drop();
							}

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}  else if (Dungeon.depth.map[pos] == Terrain.BARRICADE && Floor.insideMap(pos)) {

					hero.spend(TIME_TO_MINE);
					hero.busy();

					hero.sprite.attack(pos, new Callback() {

						@Override
						public void call() {

							CellEmitter.center(pos).burst(
									Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);

							Floor.set(pos, Terrain.EMBERS);
							GameScene.updateMap(pos);

							WoodenBlock wooden = new WoodenBlock();

							if (Random.Int(15)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.MUSHROOM), hero.pos).sprite.drop();
								Dungeon.depth.drop(wooden, hero.pos).sprite.drop();
							}

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}  else if (Dungeon.depth.map[pos] == Terrain.WATER && Floor.insideMap(pos)) {

					hero.spend(TIME_TO_MINE);
					hero.busy();

					hero.sprite.attack(pos, new Callback() {

						@Override
						public void call() {

							CellEmitter.center(pos).burst(
									Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);

							Floor.set(pos, Terrain.EMBERS);
							GameScene.updateMap(pos);

							WaterBlock water = new WaterBlock();

							if (Random.Int(30)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.SEED), hero.pos).sprite.drop();
								Dungeon.depth.drop(water, hero.pos).sprite.drop();
							}  

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}  else if (Dungeon.depth.map[pos] == Terrain.STATUE && Floor.insideMap(pos)) {

					hero.spend(TIME_TO_MINE);
					hero.busy();

					hero.sprite.attack(pos, new Callback() {

						@Override
						public void call() {

							CellEmitter.center(pos).burst(
									Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);

							Floor.set(pos, Terrain.EMBERS);
							GameScene.updateMap(pos);

							StoneBlock stone = new StoneBlock();

							if (Random.Int(30)==1){
								Dungeon.depth.drop(Generator.random(), hero.pos).sprite.drop();
								Dungeon.depth.drop(stone, hero.pos).sprite.drop();
							}

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}
			}
			GLog.w(Messages.get(this,"no_thing"));

		}
		
		 if (action.equals(AC_HEAL)) {

		 	    switch (Random.Int(11)){
					case 0:
						if (Dungeon.hero.buff(HopeMind.class) != null);
						else {Buff.affect(Dungeon.hero,HopeMind.class);
							break;}
					case 1:
						if (Dungeon.hero.buff(KeepMind.class) != null);
						else {Buff.affect(Dungeon.hero,KeepMind.class);
							break;}
					case 2:
                        if (Dungeon.hero.buff(AmokMind.class) != null);
                        else {Buff.affect(Dungeon.hero,AmokMind.class);
                        break;}
                    case 3:
                        if (Dungeon.hero.buff(CrazyMind.class) != null);
                        else {Buff.affect(Dungeon.hero,CrazyMind.class);
                        break;}
					case 4:
						if (Dungeon.hero.buff(WeakMind.class) != null);
						else {Buff.affect(Dungeon.hero,WeakMind.class);
							break;}
                    case 5:
                        if (Dungeon.hero.buff(LoseMind.class) != null);
                        else {Buff.affect(Dungeon.hero,LoseMind.class);
                        break;}
                    case 6:
                        if (Dungeon.hero.buff(TerrorMind.class) != null);
                        else {Buff.affect(Dungeon.hero,TerrorMind.class);
                        break;}
					case 7:
						if (Dungeon.hero.buff(GoldMind.class) != null);
						else {Buff.affect(Dungeon.hero, GoldMind.class);
							break;}
					case 8:
						if (Dungeon.hero.buff(SleepMind.class) != null);
						else {Buff.affect(Dungeon.hero, SleepMind.class);
							break;}
					case 9:
						if (Dungeon.hero.buff(HarmMind.class) != null);
						else {Buff.affect(Dungeon.hero, HarmMind.class);
							break;}
                    case 10:
                        Buff.affect(Dungeon.hero,Bless.class,20f);
                        break;
                }
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spp -= hero.lvl*2;
		    }

		
			if (action.equals(AC_MIND)) {
				hero.sprite.operate(hero.pos);
				hero.busy();
				if (Dungeon.hero.buff(CrazyMind.class) != null) {
					Buff.detach(Dungeon.hero, CrazyMind.class);
                    Dungeon.hero.HP += Dungeon.hero.HT / 5;
                    Dungeon.hero.spp += Dungeon.hero.lvl-1;
				} else if (Dungeon.hero.buff(WeakMind.class) != null) {
					Buff.detach(Dungeon.hero, WeakMind.class);
                    Dungeon.hero.HP += Dungeon.hero.HT / 5;
                    Dungeon.hero.spp += Dungeon.hero.lvl-1;
				} else if (Dungeon.hero.buff(AmokMind.class) != null) {
					Buff.detach(Dungeon.hero, AmokMind.class);
                    Dungeon.hero.HP += Dungeon.hero.HT / 5;
                    Dungeon.hero.spp += Dungeon.hero.lvl-1;
				} else if (Dungeon.hero.buff(TerrorMind.class) != null) {
					Buff.detach(Dungeon.hero, TerrorMind.class);
                    Dungeon.hero.HP += Dungeon.hero.HT / 5;
                    Dungeon.hero.spp += Dungeon.hero.lvl-1;
				} else if (Dungeon.hero.buff(LoseMind.class) != null) {
					Buff.detach(Dungeon.hero, LoseMind.class);
                    Dungeon.hero.HP += Dungeon.hero.HT / 5;
                    Dungeon.hero.spp += Dungeon.hero.lvl-1;
				} else if (Dungeon.hero.buff(HarmMind.class) != null) {
					Buff.detach(Dungeon.hero, HarmMind.class);
					Dungeon.hero.HP += Dungeon.hero.HT / 5;
					Dungeon.hero.spp += Dungeon.hero.lvl-1;
				} else if (Dungeon.hero.buff(SleepMind.class) != null) {
					Buff.detach(Dungeon.hero, SleepMind.class);
					Dungeon.hero.HP += Dungeon.hero.HT / 5;
					Dungeon.hero.spp += Dungeon.hero.lvl-1;
				}
                //Dungeon.level.drop(new MindArrow(5), hero.pos).sprite.drop();
			} else {
				super.execute(hero, action);

		}

	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int price() {
		return 30 * quantity;
	}
}
