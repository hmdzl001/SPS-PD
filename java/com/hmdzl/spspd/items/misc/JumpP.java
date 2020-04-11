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

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Rhythm2;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Bundle;

import static com.hmdzl.spspd.Dungeon.hero;


public class JumpP extends Item {
	{
		//name = "Jump Shoes";
		image = ItemSpriteSheet.JUMP;
		defaultAction = AC_JUMP;
		unique = true;
		 
	}

	private static int JUMP_TIME = 1;
	private static final String AC_JUMP = "JUMP";
	public final int fullCharge = 20;
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
		if (charge >= 10){
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

				Ballistica route = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
				int cell = route.collisionPos;
				int dist = Level.distance(curUser.pos, cell);
				int range = 3;
				if (Actor.findChar( cell ) != null && cell != curUser.pos)
					cell = route.path.get(route.dist-1);

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
						
						//CellEmitter.center(dest).burst(
						//Speck.factory(Speck.DUST), 10);
						curUser.spendAndNext(JUMP_TIME);
						Buff.affect(curUser, GlassShield.class).turns(1);
						Buff.affect(curUser, Rhythm.class,5f);
						if(hero.subClass == HeroSubClass.SUPERSTAR){
							//Buff.affect(curUser,Rhythm.class,50);
							Buff.affect(curUser,Rhythm2.class,5);
						}
						//Buff.affect(curUser, Rhythm2.class,10f);
						charge -= 10;
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
		    if (charge < 10)
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
			 return Messages.format("%d", (int)charge/10);
	 }
	
}