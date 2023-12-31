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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.BookBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.DoorBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.StoneBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WallBlock;
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

public class DiamondPickaxe extends MeleeWeapon {

	public static final String AC_MINE = "MINE";

	public static final float TIME_TO_MINE = 6;

	{
		//name = "pickaxe";
		image = ItemSpriteSheet.DIAMOND_PICKAXE;
		defaultAction = AC_MINE;
	}
	
	public DiamondPickaxe() {
		super(3, 2f, 0.5f, 2);
		MIN = 2;
		MAX = 8;
		unique = true;
		reinforced = true;
	}	

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_MINE);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {

		if (action.equals(AC_MINE)) {
			if (Dungeon.hero.isHungry() || Dungeon.hero.isStarving()){
				GLog.i(Messages.get(this, "break"));
				return;
			}
            if (Random.Int(3) == 0) {
                for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
                    mob.beckon(hero.pos);
                }
				GLog.n(Messages.get(this,"noise"));
			}
			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {

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


                            if (Random.Int(60)==1){
								Dungeon.depth.drop(new Gold(50), hero.pos).sprite.drop();
							} else Dungeon.depth.drop(wall, hero.pos).sprite.drop();

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

							if (Random.Int(60)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.SEED), hero.pos).sprite.drop();
							} else Dungeon.depth.drop(door, hero.pos).sprite.drop();

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

							if (Random.Int(60)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.SCROLL), hero.pos).sprite.drop();
							} else Dungeon.depth.drop(book, hero.pos).sprite.drop();

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}  else if (Dungeon.depth.map[pos] == Terrain.GLASS_WALL && Floor.insideMap(pos)) {

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

							if (Random.Int(60)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.MUSHROOM), hero.pos).sprite.drop();
							} else Dungeon.depth.drop(wooden, hero.pos).sprite.drop();

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}  else if(Dungeon.depth.map[pos] == Terrain.BARRICADE && Floor.insideMap(pos)) {

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

							if (Random.Int(60)==1){
								Dungeon.depth.drop(Generator.random(Generator.Category.MUSHROOM), hero.pos).sprite.drop();
							} else Dungeon.depth.drop(wooden, hero.pos).sprite.drop();

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

							if (Random.Int(60)==1){
								Dungeon.depth.drop(Generator.random(), hero.pos).sprite.drop();
							} else Dungeon.depth.drop(stone, hero.pos).sprite.drop();

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
		} else {

			super.execute(hero, action);

		}
	}

	public Item upgrade(boolean enchant) {
		
		MIN+=1;
        MAX+=1;
		super.upgrade(enchant);
		return this;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		if (Random.Int(10) < 1 && defender.isAlive() ) {
			defender.damage(damage, attacker);
		}
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}

		if ( defender.HP <= damage && ((Mob) defender).firstitem) {
			((Mob) defender).firstitem = false;
			if (Random.Int(6) == 0) {
				Dungeon.depth.drop(Generator.random(), defender.pos).sprite.drop();
			}
		}
	}

	/*@Override
	public String info() {
		return "This is a large and sturdy tool for breaking rocks. Probably it can be used as a weapon.";
	}*/
}
