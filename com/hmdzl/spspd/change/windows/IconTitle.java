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

import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.ui.HealthBar;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class IconTitle extends Component {

	private static final int FONT_SIZE = 9;

	private static final float GAP = 2;

	protected Image imIcon;
	protected RenderedTextMultiline tfLabel;
	protected HealthBar health;

	private float healthLvl = Float.NaN;

	public IconTitle() {
		super();
	}

	public IconTitle(Item item) {
		this(new ItemSprite(item.image(), item.glowing()),Messages.titleCase( item.toString() ) );

	}

	public IconTitle(Image icon, String label) {
		super();

		icon(icon);
		label(label);
	}

	@Override
	protected void createChildren() {
		imIcon = new Image();
		add(imIcon);

		tfLabel = PixelScene.renderMultiline(FONT_SIZE);
		tfLabel.hardlight(Window.TITLE_COLOR);
		add(tfLabel);

		health = new HealthBar();
		add(health);
	}

	@Override
	protected void layout() {

		health.visible = !Float.isNaN( healthLvl );

		imIcon.x = x;
		imIcon.y = y;

		tfLabel.maxWidth((int)(width - (imIcon.x + imIcon.width() + GAP)));
		tfLabel.setPos(imIcon.x + imIcon.width() + GAP, imIcon.height > tfLabel.height() ?
				imIcon.y + (imIcon.height() - tfLabel.height()) / 2 :
				imIcon.y);
		PixelScene.align(tfLabel);

		if (health.visible) {
			health.setRect( tfLabel.left(), Math.max( tfLabel.top() + tfLabel.height(), imIcon.y + imIcon.height() - health.height() ), tfLabel.maxWidth(), 0 );
			height = health.bottom();
		} else {
			height = Math.max( imIcon.height(), tfLabel.height() );
		}
	}

	public void icon(Image icon) {
		remove(imIcon);
		add(imIcon = icon);
	}

	public void label(String label) {
		tfLabel.text(label);
	}

	public void label(String label, int color) {
		tfLabel.text(label);
		tfLabel.hardlight(color);
	}

	public void color(int color) {
		tfLabel.hardlight(color);
	}

	public void health(float value) {
		health.level(healthLvl = value);
		layout();
	}
}
