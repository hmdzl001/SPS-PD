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
package com.hmdzl.spspd.windows;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer1;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.quest.Mushroom;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.hmdzl.spspd.utils.GLog;
 

public class WndTinkerer extends Window {

	private static final String TXT_MESSAGE = "Thanks for the Toadstool Mushroom! "
			                                  +"I can upgrade your dew vial for you. "
			                                  +"I can make it either draw out dew from certain vanquished enemies, "
			                                  +"or I can make it able to regrow the surrounding dungeon by watering with dew. ";
	
	private static final String TXT_MESSAGE_WATER = "Water with dew allows you to grow high grass around you once you have 50 drops in your vial. "
			                                        +"Watering costs 2 drops but you will be able to find more drop, nuts, berries, and seeds by trampling the grass. ";
	
	
	private static final String TXT_MESSAGE_DRAW = "Drawing out dew makes it so that mobs on special levels drop dew to fill your vial. "
			                                        +"Additionally, your character is buffed with dew charge at the start of each normal level. "
			                                        +"As long as you are dew charged, enemies drop dew to fill your vial. "
			                                        +"Each level dew charges you for a set amount of moves. "
			                                        +"Each level also has a move goal for killing all regular generated enemies. (Not special enemies like statues and piranha) "
			                                        +"Killing all regular enemies that were generated with the level clears that level. "
			                                        +"If you clear a level in less moves than the goal, the additional moves are added to your dew charge for the next level. "
			                                        +"You will need to clear the levels as fast as you can to get dew upgrades. "
			                                        +"The dew vial will also allow you to choose which item you apply upgrades to when blessing. ";
	
	private static final String TXT_WATER = "Water with Dew";
	private static final String TXT_DRAW = "Draw Out Dew";
	private static final String TXT_DRAW_INFO = "Tell me more about Draw Out Dew";

	private static final String TXT_FARAWELL = "Good luck in your quest, %s!";
	private static final String TXT_FARAWELL_DRAW = "Good luck in your quest, %s! I'll give you a head start drawing out dew!";


	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndTinkerer(final Tinkerer1 tinkerer, final Item item) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Messages.titleCase(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(Messages.get(WndTinkerer.class, "info1"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnBattle = new RedButton(Messages.get(WndTinkerer.class, "water")) {
			@Override
			protected void onClick() {
				selectUpgrade(tinkerer, 1);
			}
		};
		btnBattle.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnBattle);
		
		RedButton btnNonBattle = new RedButton(Messages.get(WndTinkerer.class, "draw")) {
			@Override
			protected void onClick() {
				selectUpgrade(tinkerer, 2);
			}
		};
		
		btnNonBattle.setRect(0, btnBattle.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNonBattle);
		
		RedButton btnNonBattle2 = new RedButton(Messages.get(WndTinkerer.class, "spinfo")) {
			@Override
			protected void onClick() {
				GameScene.show(new WndDewDrawInfo(item));				
			}
		};
		btnNonBattle2.setRect(0, btnNonBattle.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNonBattle2);

		resize(WIDTH, (int) btnNonBattle2.bottom());
	}

	private void selectUpgrade(Tinkerer1 tinkerer, int type) {

		hide();
		
		Mushroom mushroom = Dungeon.hero.belongings.getItem(Mushroom.class);
		mushroom.detach(Dungeon.hero.belongings.backpack);
		
		if (type==1){
			
			Dungeon.dewWater=true;
			
		} else if (type==2){
			
			Dungeon.dewDraw=true;
		}
		
		if (type==1){
		    tinkerer.yell(Messages.get(this, "farewell", Dungeon.hero.givenName()));
			Statistics.prevfloormoves=500;
			Buff.prolong(Dungeon.hero, Dewcharge.class, Dewcharge.DURATION+50);
	        GLog.p(Messages.get(this,"dungeon"));
		} else if (type==2){
			tinkerer.yell(Messages.get(this, "farewell", Dungeon.hero.givenName()));
			Statistics.prevfloormoves=500;
			Buff.prolong(Dungeon.hero, Dewcharge.class, Dewcharge.DURATION+50);
	        GLog.p(Messages.get(this,"dungeon"));
		}
		
		tinkerer.destroy();

		tinkerer.sprite.die();

		//Wandmaker.Quest.complete();
	}
}
