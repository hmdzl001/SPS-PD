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
package com.hmdzl.spspd.change.items.misc;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Arcane;
import com.hmdzl.spspd.change.actors.buffs.TargetShoot;
import com.hmdzl.spspd.change.actors.buffs.WarGroove;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;


public class JumpS extends Item {
	{
		//name = "Jump Shoes";
		image = ItemSpriteSheet.JUMP;
		defaultAction = AC_JUMP;
        unique = true;
		 
	}
	
	private static int JUMP_TIME = 1;
	private static final String AC_JUMP = "JUMP";
	private static final String TXT_ERROR = "Too far away";
	public final int fullCharge = 30;
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
		if (charge >= 15){
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
				Ballistica route = new Ballistica(curUser.pos, target, (Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN));
				int cell = route.collisionPos;
				int dist = Level.distance(curUser.pos, cell);
				int range = 3;
				//if (Actor.findChar( cell ) != null && cell != curUser.pos)
					//cell = route.path.get(route.dist-1);

				if (dist > 3) {
					range = dist - 3;
					cell = route.path.get(route.dist - range);
				}

				final int dest = cell;
				curUser.belongings.relord();
				curUser.busy();
				curUser.sprite.jump(curUser.pos, cell, new Callback() {
					@Override
					public void call() {
						curUser.move(dest);
						//Dungeon.level.press(dest, curUser);
						//Dungeon.observe();

						//for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
							//Char mob = Actor.findChar(curUser.pos
									//+ Level.NEIGHBOURS8[i]);
						//}
						
						//CellEmitter.center(dest).burst(
						//Speck.factory(Speck.DUST), 10);
						curUser.spendAndNext(JUMP_TIME);

						if (Random.Int(10) > 3 ){
							curUser.belongings.relord();
							Buff.affect(curUser, TargetShoot.class, 10f);
						}
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