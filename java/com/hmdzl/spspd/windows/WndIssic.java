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
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.medicine.Powerpill;
import com.hmdzl.spspd.items.weapon.melee.block.GoblinShield;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;


public class WndIssic extends Window {

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	public WndIssic() {

		super();

		Powerpill key = new Powerpill();


		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(key.image(), null));
		titlebar.label(Messages.titleCase(key.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(Messages.get(this, "message"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnReward = new RedButton(Messages.get(this, "buy")) {
			@Override
			protected void onClick() {
                if (Dungeon.hero.HP > 150){
					Dungeon.hero.HP-=100;
					Gold gs = new Gold(Random.Int(500,3000));
					gs.doPickUp(Dungeon.hero);
					hide();
				} else {
					GLog.w(Messages.get(WndHotel.class,"more_gold"));
				    hide();
				}
			}
		};
		btnReward.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnReward);

		resize(WIDTH, (int) btnReward.bottom());
	}
}