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
package com.hmdzl.spspd.change.windows;

import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.mobs.npcs.Wandmaker;
import com.hmdzl.spspd.change.items.AdamantWand;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
import com.hmdzl.spspd.change.utils.GLog;
 

public class WndWandmaker extends Window {

	private static final String TXT_MESSAGE = "Oh, I see you have succeeded! I do hope it hasn't troubled you too much. "
			+ "As I promised, you can choose one of my high quality wands.";
	private static final String TXT_BATTLE = "Battle wand";
	private static final String TXT_NON_BATTLE = "Non-battle wand";

	private static final String TXT_ADAMANT = "You might find this raw material useful later on. I'm not powerful enough to work with it.";
	private static final String TXT_WOW = "How did you make it all this way!? I have another reward for you. ";
	private static final String TXT_FARAWELL = "Good luck in your quest, %s!";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndWandmaker(final Wandmaker wandmaker, final Item item) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Messages.titleCase(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(Messages.get(this, "message"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);

		RedButton btnBattle = new RedButton(Messages.get(this, "battle")) {
			@Override
			protected void onClick() {
				selectReward(wandmaker, item, Wandmaker.Quest.wand1);
			}
		};
		btnBattle.setRect(0, message.top()+ message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnBattle);

		RedButton btnNonBattle = new RedButton(Messages.get(this, "no_battle")) {
			@Override
			protected void onClick() {
				selectReward(wandmaker, item, Wandmaker.Quest.wand2);
			}
		};
		btnNonBattle.setRect(0, btnBattle.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNonBattle);

		resize(WIDTH, (int) btnNonBattle.bottom());
	}

	private void selectReward(Wandmaker wandmaker, Item item, Wand reward) {

		hide();

		item.detach(Dungeon.hero.belongings.backpack);

		reward.identify();
		Dungeon.level.drop(reward, wandmaker.pos).sprite.drop();
		wandmaker.yell(Messages.get(this, "farewell", Dungeon.hero.givenName()));
		Dungeon.level.drop(new AdamantWand(), wandmaker.pos).sprite.drop();
		wandmaker.destroy();
		wandmaker.sprite.die();
		Wandmaker.Quest.complete();
	}
}
