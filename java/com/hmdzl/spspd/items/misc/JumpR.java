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

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.InfJump;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;


public class JumpR extends Item {
	{
		//name = "Jump Shoes";
		image = ItemSpriteSheet.JUMP;
		defaultAction = AC_JUMP;
        unique = true;
		 
	}
	
	private static int JUMP_TIME = 1;
	private static final String AC_JUMP = "JUMP";
	public final int fullCharge = 40;
	public int charge = 0;
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
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (charge >= 10|| hero.buff(InfJump.class) != null){
		actions.add(AC_JUMP);
		}
		actions.remove(AC_DROP);
        actions.remove(AC_THROW);
		return actions;
	}
	
	protected CellSelector.Listener jumper = new CellSelector.Listener() {
		
		@Override
		public void onSelect(Integer target) {
			if (target != null && target != curUser.pos) {

				Ballistica route = new Ballistica(curUser.pos, target,Ballistica.PROJECTILE);
				int cell = route.collisionPos;
				int dist = Floor.distance(curUser.pos, cell);
				int range = 2;
				if (Actor.findChar( cell ) != null && cell != curUser.pos)
					cell = route.path.get(route.dist-1);

				if (dist > 2) {
					range = dist - 2;
					cell = route.path.get(route.dist - range);
				}

				final int dest = cell;
                Buff.prolong(curUser, Levitation.class,2f);
				curUser.busy();
				curUser.sprite.jump(curUser.pos, cell, new Callback() {
					@Override
					public void call() {
						curUser.move(dest);
						//Dungeon.level.press(dest, curUser);
						//Dungeon.observe();

						for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
							Char mob = Actor.findChar(curUser.pos
									+ Floor.NEIGHBOURS8[i]);
						}
						
						//CellEmitter.center(dest).burst(
						//Speck.factory(Speck.DUST), 10);
						curUser.spendAndNext(JUMP_TIME);
						if (Random.Int(10) > 3 ){
						Buff.prolong(curUser, Invisibility.class,5);}
						if(curUser.buff(InfJump.class) == null){
						charge -= 10;
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
		    if (charge < 10&& hero.buff(InfJump.class) == null)
				GLog.i(Messages.get(Jumpshoes.class, "rest"));
                else {
			    curUser = hero;
			    GameScene.selectCell(jumper);
				}
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
		info += "\n\n" + Messages.get(Jumpshoes.class, "charge",charge,fullCharge);
		return info;	
	}

	 @Override
	 public String status() {
		 return Messages.format("%d", charge /10);
	 }

	
}