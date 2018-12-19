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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.ui.ItemSlot;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.Window;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.messages.Messages;
 
import com.watabou.noosa.BitmapTextMultiline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WndItem extends Window {

	private static final float BUTTON_WIDTH = 36;
	private static final float BUTTON_HEIGHT = 16;

	private static final float GAP = 2;

	private static final int WIDTH = 120;
	
	public WndItem(final WndBag owner, final Item item) {

		super();
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), item.glowing()));
		titlebar.label(Messages.titleCase(item.toString()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		if (item.levelKnown && item.level > 0) {
			titlebar.color(ItemSlot.UPGRADED);
		} else if (item.levelKnown && item.level < 0) {
			titlebar.color(ItemSlot.DEGRADED);
		}
		
		RenderedTextMultiline info = PixelScene.renderMultiline( item.info(), 6 );
		info.maxWidth(WIDTH);
		info.setPos(titlebar.left(), titlebar.bottom() + GAP);
		add( info );

		float y = info.top() + info.height() + GAP;
		float x = 0;

		if (Dungeon.hero.isAlive() && owner != null) {
			ArrayList<RedButton> line = new ArrayList<>();
			for (final String action : item.actions(Dungeon.hero)) {

				RedButton btn = new RedButton(Messages.get(item, "ac_" + action), 8 ) {
					@Override
					protected void onClick() {
						item.execute(Dungeon.hero, action);
						hide();
						owner.hide();
					};
				};
				btn.setSize(Math.max(BUTTON_WIDTH, btn.reqWidth()),
						BUTTON_HEIGHT);
				if (x + btn.width() > WIDTH) {
					x = 0;
					y += BUTTON_HEIGHT + GAP;
				}
				btn.setPos(x, y);
				add(btn);

				if (action == item.defaultAction) {
					btn.textColor(TITLE_COLOR);
				}

				x += btn.width() + GAP;
			}
		}

		resize(WIDTH, (int) (y + (x > 0 ? BUTTON_HEIGHT : 0)));
	}
	private static void layoutButtons(ArrayList<RedButton> line, float extraWidth, float y){
		if (line == null || line.size() == 0 || extraWidth == 0) return;
		if (line.size() == 1){
			line.get(0).setSize(line.get(0).width()+extraWidth, BUTTON_HEIGHT);
			line.get(0).setPos( 0 , y );
			return;
		}
		ArrayList<RedButton> lineByWidths = new ArrayList<>(line);
		Collections.sort(lineByWidths, widthComparator);
		RedButton smallest, middle, largest;
		smallest = lineByWidths.get(0);
		middle = lineByWidths.get(1);
		largest = null;
		if (lineByWidths.size() == 3) {
			largest = lineByWidths.get(2);
		}

		float btnDiff = middle.width() - smallest.width();
		smallest.setSize(smallest.width() + Math.min(btnDiff, extraWidth), BUTTON_HEIGHT);
		extraWidth -= btnDiff;
		if (extraWidth > 0) {
			if (largest == null) {
				smallest.setSize(smallest.width() + extraWidth / 2, BUTTON_HEIGHT);
				middle.setSize(middle.width() + extraWidth / 2, BUTTON_HEIGHT);
			} else {
				btnDiff = largest.width() - smallest.width();
				smallest.setSize(smallest.width() + Math.min(btnDiff, extraWidth/2), BUTTON_HEIGHT);
				middle.setSize(middle.width() + Math.min(btnDiff, extraWidth/2), BUTTON_HEIGHT);
				extraWidth -= btnDiff*2;
				if (extraWidth > 0){
					smallest.setSize(smallest.width() + extraWidth / 3, BUTTON_HEIGHT);
					middle.setSize(middle.width() + extraWidth / 3, BUTTON_HEIGHT);
					largest.setSize(largest.width() + extraWidth / 3, BUTTON_HEIGHT);
				}
			}
		}

		float x = 0;
		for (RedButton btn : line){
			btn.setPos( x , y );
			x += btn.width()+1;
		}
	}

	private static Comparator<RedButton> widthComparator = new Comparator<RedButton>() {
		@Override
		public int compare(RedButton lhs, RedButton rhs) {
			if (lhs.width() < rhs.width()){
				return -1;
			} else if (lhs.width() == rhs.width()){
				return 0;
			} else {
				return 1;
			}
		}
	};
}
