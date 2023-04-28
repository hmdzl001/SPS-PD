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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.ItemSlot;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;

public class WndLifeTradeItem extends Window {

	private static final float GAP = 2;
	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 16;

	public WndLifeTradeItem(final Heap heap, boolean canBuy) {

		super();

		Item item = heap.peek();

		float pos = createDescription(item, true);

		final int price = price(item);

		if (canBuy) {

			RedButton btnBuy = new RedButton( Messages.get(this, "buy", price)) {
				@Override
				protected void onClick() {
					hide();
					lifebuy(heap);
				}
			};
			btnBuy.setRect(0, pos + GAP, WIDTH, BTN_HEIGHT);
			btnBuy.enable(price < Dungeon.hero.TRUE_HT);
			add(btnBuy);

			RedButton btnCancel = new RedButton(Messages.get(this, "cancel")) {
				@Override
				protected void onClick() {
					hide();
				}
			};

		    btnCancel.setRect(0, btnBuy.bottom() + GAP, WIDTH, BTN_HEIGHT);

			add(btnCancel);

			resize(WIDTH, (int) btnCancel.bottom());

		} else {

			resize(WIDTH, (int) pos);

		}
	}

	@Override
	public void hide() {
		super.hide();
	}

	private float createDescription(Item item, boolean forSale) {

		// Title
		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), item.glowing()));
		titlebar.label(forSale ? 
		Messages.get(this, "sale", item.toString(), price( item ) ) :
		Messages.titleCase( item.toString() ) );
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		// Upgraded / degraded
		if (item.levelKnown && item.level > 0) {
			titlebar.color(ItemSlot.UPGRADED);
		} else if (item.levelKnown && item.level < 0) {
			titlebar.color(ItemSlot.DEGRADED);
		}

		// Description
		RenderedTextMultiline info = PixelScene.renderMultiline( item.info(), 6 );
		info.maxWidth(WIDTH);
		info.setPos(titlebar.left(), titlebar.bottom() + GAP);
		add( info );

		return info.bottom();
	}
	
	
	private int price(Item item) {
		int price = Dungeon.hero.heroClass == HeroClass.FOLLOWER ?
				8 : 10 ;
		return price;
	}

	private void lifebuy(Heap heap) {

		Hero hero = Dungeon.hero;
		Item item = heap.pickUp();

		int price = price(item);
		Dungeon.hero.TRUE_HT -= price;
		Dungeon.hero.updateHT(true);

		if (!item.doPickUp(hero)) {
			Dungeon.level.drop(item, heap.pos).sprite.drop();
		}
	}
}
