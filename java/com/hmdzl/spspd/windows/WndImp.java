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
import com.hmdzl.spspd.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.quest.DwarfToken;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;


public class WndImp extends Window {

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	public WndImp(final Imp imp, final DwarfToken tokens) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(tokens.image(), null));
		titlebar.label(Messages.titleCase( tokens.name() ));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline( Messages.get(this, "message"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);

		RedButton btnReward = new RedButton(Messages.get(this, "reward")) {
			@Override
			protected void onClick() {
				takeReward(imp, tokens, Imp.Quest.reward);
			}
		};
		btnReward.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnReward);

		resize(WIDTH, (int) btnReward.bottom());
	}

	private void takeReward(Imp imp, DwarfToken tokens, Item reward) {

		hide();

		tokens.detachAll(Dungeon.hero.belongings.backpack);

		reward.identify();

		Dungeon.level.drop(reward, imp.pos).sprite.drop();

		imp.flee();

		Imp.Quest.complete();
	}
}
