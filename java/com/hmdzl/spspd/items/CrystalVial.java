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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class CrystalVial extends Item {


	private static final String AC_DRINK = "DRINK";
	private static final String AC_BLESS = "BLESS";
	private static final String AC_CHOOSE = "CHOOSE";

	private static final float TIME_TO_DRINK = 2f;

	private static final String TXT_STATUS = "%d";
	private static final String TXT_STATUS2 = "%d/%d";

	{
		//name = "CrystalVial";
		image = ItemSpriteSheet.CRYSTAL_VIAL;

		defaultAction = AC_CHOOSE;

	}

	public int volume = 0;
	
	private static final String VOLUME = "volume";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(VOLUME, volume);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		volume = bundle.getInt(VOLUME);
	}
	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (volume > 10) {
			actions.add(AC_DRINK);
		}
		if (volume > 50){
			actions.add(AC_BLESS);
		}   
	
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else	if (action.equals(AC_DRINK)) {
			if (volume > 0) 

                hero.HP += Math.min(hero.HT/5,hero.HT-hero.HP);
				volume -= 10;
				hero.spend(TIME_TO_DRINK);
				hero.busy();

				Sample.INSTANCE.play(Assets.SND_DRINK);
				hero.sprite.operate(hero.pos);

				updateQuickslot();
			
		} else if (action.equals(AC_BLESS) && Dungeon.dewDraw) {
			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.UPGRADEDEW,	Messages.get(DewVial.class, "select"));
		 }else {
			super.execute(hero, action);
		}
	}

  private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				upgrade(item);
				volume = volume - 50;
			}
		}
	};
	
	private void upgrade(Item item) {
		item.upgrade();
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		Badges.validateItemLevelAquired(item);
		curUser.busy();
		updateQuickslot();
		
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}


	public void fill() {
		if (volume < 50)
		volume +=5;
		updateQuickslot();
	}

	// removed as people need a bigger distinction to realize the dew vial
	// doesn't revive.
	/*
	 * private static final Glowing WHITE = new Glowing( 0xFFFFCC );
	 * 
	 * @Override public Glowing glowing() { return isFull() ? WHITE : null; }
	 */

	@Override
	public String status() {
		return Messages.format(TXT_STATUS, volume);
	}

	public String status2() {
		return Messages.format(TXT_STATUS2, volume, 50);
	}
	@Override
	public String toString() {
		return super.toString() + " (" + status2() + ")";
	}

}
