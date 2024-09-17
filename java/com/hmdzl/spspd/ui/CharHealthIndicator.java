/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.watabou.noosa.BitmapText;

public class CharHealthIndicator extends HealthBar {
	
	private static final int HEIGHT = 1;
	
	private Char target;
	private BitmapText hpText;
	
	public CharHealthIndicator( Char c ){
		target = c;
		GameScene.add(this);
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
		height = HEIGHT;
		hpText = new BitmapText(PixelScene.font1x);
		add(hpText);
	}
	
	@Override
	public void update() {
		super.update();
		
		if (target != null && target.isAlive() && target.sprite.visible) {
			CharSprite sprite = target.sprite;
			width = sprite.width()*(4/6f);
			x = sprite.x + sprite.width()/6f;
			y = sprite.y - 2;
			level( target );
			visible = target.HP < target.HT;
		//} else if (Dungeon.canseehp) {
		//	visible = true;
		} else {
			visible = false;
		}

		if (Dungeon.canseehp) {
			CharSprite sprite = target.sprite;
			hpText.scale.set(PixelScene.align(0.5f));
			hpText.x = sprite.x + sprite.width()/6f;;
			hpText.y = sprite.y - 6;
			hpText.text(target.HP + "" );
			hpText.visible = true;
		} else {
			hpText.visible = false;
		}

	}
	
	public void target( Char ch ) {
		if (ch != null && ch.isAlive()) {
			target = ch;
		} else {
			target = null;
		}
	}
	
	public Char target() {
		return target;
	}
}
