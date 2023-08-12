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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;


public class AttackShoes extends Item {
	 {
		 //name = "AttackShoes";
		 image = ItemSpriteSheet.JUMP;
		 defaultAction = AC_JUMP;
		 unique = true;

	 }

	 private static int JUMP_TIME = 2;
	 private static final String AC_JUMP = "JUMP";


	 @Override
	 public ArrayList<String> actions(Hero hero) {
		 ArrayList<String> actions = super.actions(hero);
		 actions.add(AC_JUMP);
		 actions.remove(AC_DROP);
		 actions.remove(AC_THROW);
		 return actions;
	 }

	 protected CellSelector.Listener jumper = new CellSelector.Listener() {

		 @Override
		 public void onSelect(Integer target) {
			 if (target != null && target != curUser.pos) {

				 Ballistica route = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
				 int cell = route.collisionPos;
				 int dist = Level.distance(curUser.pos, cell);
				 int range = 3;
				 //if (Actor.findChar(cell) != null && cell != curUser.pos)
					// cell = route.path.get(route.dist - 1);

				 if (dist > 3) {
					 range = dist - 3;
					 cell = route.path.get(route.dist - range);
				 }

				 final int dest = cell;
				 curUser.busy();
				 curUser.sprite.jump(curUser.pos, cell, new Callback() {
					 @Override
					 public void call() {
						 curUser.move(dest);
						 Dungeon.level.press(dest, curUser);
						 Dungeon.observe();
						 for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
							 Char mob = Actor.findChar(curUser.pos
									 + Level.NEIGHBOURS8[i]);
								if (mob != null && mob != curUser && mob.isAlive()) {
									mob.damage(30 + Dungeon.hero.lvl * 3, this);
								}
						 }

						 CellEmitter.center(dest).burst(
								 Speck.factory(Speck.DUST), 10);
						 curUser.spendAndNext(JUMP_TIME);
						 if (Random.Int(20) == 10 ){
							 Plant.Seed seed = (Plant.Seed) Generator.random(Generator.Category.SEED3);
							 Dungeon.level.plant(seed, dest);
						 }
						 updateQuickslot();
					 }
				 });
			 }
		 }
	    public String prompt() {
		return Messages.get(Jumpshoes.class, "prompt");
	    }
	};
	
	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_JUMP)) {
			curUser = hero;
			GameScene.selectCell(jumper);
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
	public String info() {
		String info = desc();
		return info;	
	}

	
}