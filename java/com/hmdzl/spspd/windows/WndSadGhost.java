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
import com.hmdzl.spspd.actors.mobs.npcs.Ghost;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.FetidRatSprite;
import com.hmdzl.spspd.sprites.GnollTricksterSprite;
import com.hmdzl.spspd.sprites.GreatCrabSprite;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;

public class WndSadGhost extends Window {

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndSadGhost(final Ghost ghost, final int type) {

		super();

		IconTitle titlebar = new IconTitle();
		RenderedTextMultiline message;
		switch (type) {
		case 1:
		default:
			titlebar.icon(new FetidRatSprite());
			titlebar.label( Messages.get(this, "rat_title") );
			message = PixelScene.renderMultiline(Messages.get(this, "rat")+Messages.get(this, "give_item"), 6 );
			break;
		case 2:
			titlebar.icon(new GnollTricksterSprite());
			titlebar.label(Messages.get(this, "gnoll_title"));
			message = PixelScene.renderMultiline( Messages.get(this, "gnoll")+Messages.get(this, "give_item"), 6 );
			break;
		case 3:
			titlebar.icon(new GreatCrabSprite());
			titlebar.label( Messages.get(this, "crab_title"));
			message = PixelScene.renderMultiline( Messages.get(this, "crab")+Messages.get(this, "give_item"), 6 );
			break;

		}

		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);

		RedButton btnWeapon = new RedButton( Messages.get(this, "weapon")) {
			@Override
			protected void onClick() {
				selectReward(ghost, Ghost.Quest.artifact);
			}
		};
		btnWeapon.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnWeapon);


			RedButton btnArmor = new RedButton( Messages.get(this, "armor")) {
				@Override
				protected void onClick() {
					selectReward(ghost, Ghost.Quest.ring);
				}
			};
			btnArmor.setRect(0, btnWeapon.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnArmor);
			

			RedButton btnPet = new RedButton( Messages.get(this, "pet")) {
				@Override
				protected void onClick() {
					selectReward(ghost, Ghost.Quest.pet);
				}
			};
			btnPet.setRect(0, btnArmor.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnPet);			

			resize(WIDTH, (int) btnPet.bottom());

	}

	private void selectReward(Ghost ghost, Item reward) {

		hide();

		Dungeon.level.drop(reward, ghost.pos).sprite.drop();

		ghost.yell(Messages.get(this, "farewell"));
		ghost.die(null);

		Ghost.Quest.complete();
	}
}
