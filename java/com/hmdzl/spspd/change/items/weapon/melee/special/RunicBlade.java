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
package com.hmdzl.spspd.change.items.weapon.melee.special;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.quest.DarkGold;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class RunicBlade extends MeleeWeapon {
	
	public static final String AC_REFORGE = "REFORGE";

	private static final String TXT_SELECT_WEAPON = "Select a weapon to upgrade";

	private static final String TXT_REFORGED = "you reforged the short sword to upgrade your %s";
	private static final String TXT_NOT_BOOMERANG = "you can't upgrade a boomerang this way";

	private static final float TIME_TO_REFORGE = 2f;

	private boolean equipped;
	
	private float upgradeChance = 0.9f;

	{
		//name = "Runic Blade";
		image = ItemSpriteSheet.RUNICBLADE;
	}

	public RunicBlade() {
		super(5, 1f, 1f,1);
		
		MIN = 0;
		MAX = 35;
		
	}

	@Override
	public Item upgrade(boolean enchant) {
		
		if (STR > 1 ){
		STR--;}
		MIN--;
		MAX+=5;
		
		return super.upgrade(enchant);
    }	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (level > 0) {
			actions.add(AC_REFORGE);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_REFORGE) {

			if (hero.belongings.weapon == this) {
				equipped = true;
				hero.belongings.weapon = null;
			} else {
				equipped = false;
				detach(hero.belongings.backpack);
			}

			curUser = hero;

			GameScene.selectItem(itemSelector, WndBag.Mode.WEAPON,
					Messages.get(RunicBlade.class, "choose"));

		} else {

			super.execute(hero, action);

		}
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && !(item instanceof Boomerang)) {
                int i=0;
				while(i<level) {
					if (i<2){
					  Sample.INSTANCE.play(Assets.SND_EVOKE);
					  ScrollOfUpgrade.upgrade(curUser);
					  evoke(curUser);
					  item.upgrade();
					} else if (Random.Float()<upgradeChance){
				        Sample.INSTANCE.play(Assets.SND_EVOKE);
				        ScrollOfUpgrade.upgrade(curUser);
				        evoke(curUser);
				        item.upgrade();
				        upgradeChance = Math.max(0.5f, upgradeChance-0.1f);
				  }
				i++;
				}
							
				curUser.spendAndNext(TIME_TO_REFORGE);

				GLog.w(Messages.get(RunicBlade.class,"reforged"));
				Badges.validateItemLevelAquired(item);
				
			}
		}
	};

}