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

import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Heap.Type;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.ui.ItemSlot;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
 

public class WndInfoItem extends Window {

	private static final float GAP = 2;

	private static final int WIDTH = 120;

	public WndInfoItem(Heap heap) {

		super();

		if (heap.type == Heap.Type.HEAP || heap.type == Heap.Type.FOR_SALE || heap.type == Heap.Type.FOR_LIFE) {

			Item item = heap.peek();

			int color = TITLE_COLOR;
			if (item.levelKnown && item.level > 0) {
				color = ItemSlot.UPGRADED;
			} else if (item.levelKnown && item.level < 0) {
				color = ItemSlot.DEGRADED;
			}
			fillFields(item.image(), item.glowing(), color, item.toString(),
					item.info());

		} else {

			String title;
			String info;

			if (heap.type == Type.CHEST || heap.type == Type.MIMIC) {
				title = Messages.get(this, "chest");
				info = Messages.get(this, "wont_know");
			} else if (heap.type == Type.TOMB) {
				title = Messages.get(this, "tomb");
				info = Messages.get(this, "owner");
			} else if (heap.type == Type.SKELETON) {
				title = Messages.get(this, "skeleton");
				info = Messages.get(this, "skeleton_desc");
			} else if (heap.type == Type.REMAINS) {
				title = Messages.get(this, "remains");
				info = Messages.get(this, "remains_desc");
			} else if (heap.type == Type.E_DUST) {
				title = Messages.get(this, "e_dust");
				info = Messages.get(this, "e_dust_desc");
			} else if (heap.type == Type.M_WEB) {
				title = Messages.get(this, "m_web");
				info = Messages.get(this, "m_web_desc");
			} else if (heap.type == Type.CRYSTAL_CHEST) {
				title = Messages.get(this, "crystal_chest");
				if (heap.peek() instanceof Artifact)
					info = Messages.get(this, "inside", Messages.get(this, "artifact") );
				else if (heap.peek() instanceof Wand)
					info = Messages.get(this, "inside", Messages.get(this, "wand") );
				else if (heap.peek() instanceof Ring)
					info = Messages.get(this, "inside", Messages.get(this, "ring") );
				else
					info = ""; //This shouldn't happen
			} else {
				title = Messages.get(this, "locked_chest");
				info = Messages.get(this, "need_key");
			}

			fillFields(heap.image(), heap.glowing(), TITLE_COLOR, title, info);

		}
	}

	public WndInfoItem(Item item) {

		super();

		int color = TITLE_COLOR;
		if (item.levelKnown && item.level > 0) {
			color = ItemSlot.UPGRADED;
		} else if (item.levelKnown && item.level < 0) {
			color = ItemSlot.DEGRADED;
		}

		fillFields(item.image(), item.glowing(), color, item.toString(),
				item.info());
	}

	private void fillFields(int image, ItemSprite.Glowing glowing,
			int titleColor, String title, String info) {

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(image, glowing));
		titlebar.label(Messages.titleCase( title ), titleColor);
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline txtInfo = PixelScene.renderMultiline( info, 6 );
		txtInfo.maxWidth(WIDTH);
		txtInfo.setPos(titlebar.left(), titlebar.bottom() + GAP);
		add( txtInfo );

		resize( WIDTH, (int)(txtInfo.top() + txtInfo.height()) );
	}
}
