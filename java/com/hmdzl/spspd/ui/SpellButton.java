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
package com.hmdzl.spspd.ui;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Chrome;
import com.hmdzl.spspd.scenes.PixelScene;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.RenderedText;

public class SpellButton extends Button {

	protected NinePatch bg;
	protected RenderedText text;
	protected Image icon1;
	protected Image icon2;
	protected Image icon3;
	protected Image icon4;
	protected Image icon5;

	public SpellButton(String label) {
		super();

		text.text(label);
		//text.measure();
	}

	@Override
	protected void createChildren() {
		super.createChildren();

		bg = Chrome.get(Chrome.Type.BUTTON);
		add(bg);

		text = PixelScene.renderText(9);
		add(text);
	}

	@Override
	protected void layout() {

		super.layout();

		bg.x = x;
		bg.y = y;
		bg.size(width, height);

		text.x = x + (int) (width - text.width()) / 2;
		text.y = y + (int) (height - text.baseLine()) / 2;

		if (icon1 != null) {
			icon1.x = x + text.x - icon1.width() - 2;
			icon1.y = y + (height - icon1.height()) / 2;
		}
		if (icon2 != null) {
			icon2.x = x + text.x - icon2.width() - 2;
			icon2.y = y + (height - icon2.height()) / 2;
		}
		if (icon3 != null) {
			icon3.x = x + text.x - icon3.width() - 2;
			icon3.y = y + (height - icon3.height()) / 2;
		}
		if (icon4 != null) {
			icon4.x = x + text.x - icon4.width() - 2;
			icon4.y = y + (height - icon4.height()) / 2;
		}
		if (icon5 != null) {
			icon5.x = x + text.x - icon5.width() - 2;
			icon5.y = y + (height - icon5.height()) / 2;
		}
	}

	@Override
	protected void onTouchDown() {
		bg.brightness(1.2f);
		Sample.INSTANCE.play(Assets.SND_CLICK);
	}

	@Override
	protected void onTouchUp() {
		bg.resetColor();
	}

	public void enable(boolean value) {
		active = value;
		text.alpha(value ? 1.0f : 0.3f);
	}

	public void text(String value) {
		text.text(value);
		//text.measure();
		layout();
	}

	public void textColor(int value) {
		text.hardlight(value);
	}

	public void icon(Image icon) {
		if (this.icon1 != null) {
			remove(this.icon1);
		}
		this.icon1 = icon;
		if (this.icon1 != null) {
			add(this.icon1);
			layout();
		}
	}

	public float reqWidth() {
		return text.width() + 4;
	}

	public float reqHeight() {
		return text.baseLine() + 4;
	}
}
