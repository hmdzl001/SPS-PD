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
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer2;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.quest.Mushroom;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.items.summon.Mobile;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;


public class WndTinkerer2 extends Window {

	private static final String TXT_MESSAGE1 = "Thanks for the Toadstool Mushroom! "
			                                  +"In return, I can upgrade your mr destructo for you, "
			                                  +"or I can recharge your old one and give you another. ";
	
	private static final String TXT_MESSAGE2 = "Thanks for the Toadstool Mushroom! "
                                               +"In return, I can upgrade your mr destructo for you. "
                                               +"You can also have this other one I've managed to fix up. ";
	
	private static final String TXT_MESSAGE3 = "Thanks for the Toadstool Mushroom! "
                                               +"In return, you can have this Mr Destructo if you like. ";

	private static final String TXT_UPGRADE = "Upgrade my Mr Destructo";
	private static final String TXT_RECHARGE = "Recharge my Mr Desructo";
	private static final String TXT_NEW = "I'll take the new Mr Desructo";

	private static final String TXT_FARAWELL = "Good luck in your quest, %s!";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;
	
	
	public WndTinkerer2(final Tinkerer2 tinkerer, final Item item, final Item mrd) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Messages.titleCase(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene.renderMultiline(Messages.get(this, "info"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnMr = new RedButton(Messages.get(this, "mr")) {
			@Override
			protected void onClick() {
					selectMr(tinkerer);
				}
		};
		btnMr.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add(btnMr);

		RedButton btnCall = new RedButton(Messages.get(this, "call")) {
			@Override
			protected void onClick() {
				selectCall(tinkerer);
			}
		};
		btnCall.setRect(0, btnMr.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnCall);

		RedButton btnMob = new RedButton(Messages.get(this, "mob")) {
			@Override
			protected void onClick() {
				selectMob(tinkerer);
			}
		};
		btnMob.setRect(0, btnCall.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnMob);

		resize(WIDTH, (int) btnMob.bottom());

	}

	private void selectMr(Tinkerer2 tinkerer) {

		hide();
		
		Mushroom mushroom = Dungeon.hero.belongings.getItem(Mushroom.class);
		mushroom.detach(Dungeon.hero.belongings.backpack);
		
		Dungeon.dewNorn = true;
		
		ActiveMrDestructo mrd = new ActiveMrDestructo();	

		Dungeon.level.drop(mrd, Dungeon.hero.pos).sprite.drop();
	
		tinkerer.yell( Messages.get(this, "farewell", Dungeon.hero.givenName()));
		tinkerer.destroy();

		tinkerer.sprite.die();

		//Wandmaker.Quest.complete();
	}
	private void selectCall(Tinkerer2 tinkerer) {

		hide();

		Mushroom mushroom = Dungeon.hero.belongings.getItem(Mushroom.class);
		mushroom.detach(Dungeon.hero.belongings.backpack);

		Dungeon.dewNorn = true;

		FairyCard fc = new FairyCard();

		Dungeon.level.drop(fc, Dungeon.hero.pos).sprite.drop();

		tinkerer.yell( Messages.get(this, "farewell", Dungeon.hero.givenName()));
		tinkerer.destroy();

		tinkerer.sprite.die();

		//Wandmaker.Quest.complete();
	}
	private void selectMob(Tinkerer2 tinkerer) {

		hide();

		Mushroom mushroom = Dungeon.hero.belongings.getItem(Mushroom.class);
		mushroom.detach(Dungeon.hero.belongings.backpack);

		Dungeon.dewNorn = true;

		Mobile mob = new Mobile();

		Dungeon.level.drop(mob, Dungeon.hero.pos).sprite.drop();

		tinkerer.yell( Messages.get(this, "farewell", Dungeon.hero.givenName()));
		tinkerer.destroy();

		tinkerer.sprite.die();

		//Wandmaker.Quest.complete();
	}
}
