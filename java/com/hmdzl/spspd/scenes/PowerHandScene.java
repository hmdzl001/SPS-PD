/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.scenes;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.effects.Flare;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.Random;

public class PowerHandScene extends PixelScene {
	
	private static final int WIDTH			= 120;
	private static final int BTN_HEIGHT		= 18;
	private static final float SMALL_GAP	= 2;
	private static final float LARGE_GAP	= 8;

	private Image pudding_cup;
	
	@Override
	public void create() {
		super.create();

		RenderedTextMultiline text = null;

		text = renderMultiline( Messages.get(this, "text"), 8 );
		text.maxWidth(WIDTH);
		add( text );


		pudding_cup = new Image(Assets.PUDDING_CUP);
		add(pudding_cup);


		RedButton btnExit = new RedButton( Messages.get(this, "exit") ) {
			@Override
			protected void onClick() {
				Dungeon.win(Messages.format(ResultDescriptions.WIN2));
				Dungeon.deleteGame( Dungeon.hero.heroClass, true );
				Game.switchScene( RankingsScene.class);
			}
		};
		btnExit.setSize( WIDTH, BTN_HEIGHT );
		add( btnExit );

		RedButton btnStay = new RedButton(Messages.get(this, "stay")) {
			@Override
			protected void onClick() {
				InterlevelScene.mode = InterlevelScene.Mode.CHAOS;
				Game.switchScene(InterlevelScene.class);
			}
		};
		btnStay.setSize(WIDTH, BTN_HEIGHT);
		add(btnStay);
		
		float height;

			height = pudding_cup.height + LARGE_GAP + text.height() + LARGE_GAP + btnExit.height() + SMALL_GAP;

		pudding_cup.x = (Camera.main.width - pudding_cup.width) / 2;
		pudding_cup.y = (Camera.main.height - height) / 2;
			align(pudding_cup);

			text.setPos((Camera.main.width - text.width()) / 2, pudding_cup.y + pudding_cup.height + LARGE_GAP);
			align(text);
			
			btnExit.setPos( (Camera.main.width - btnExit.width()) / 2, text.top() + text.height() + LARGE_GAP );
		btnStay.setPos( btnExit.left(), btnExit.bottom() + SMALL_GAP );

		new Flare( 8, 48 ).color( 0xFFDDBB, true ).show( pudding_cup, 0 ).angularSpeed = +30;
		
		fadeIn();
	}
	
	//@Override
	//protected void onBackPressed() {
		//InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
		//Game.switchScene( InterlevelScene.class );
	//}
	
	private float timer = 0;
	
	@Override
	public void update() {
		super.update();
		
		if ((timer -= Game.elapsed) < 0) {
			timer = Random.Float( 0.5f, 5f );
			
			Speck star = (Speck)recycle( Speck.class );
			star.reset( 0, pudding_cup.x + 10.5f, pudding_cup.y + 5.5f, Speck.DISCOVER );
			add( star );
		}
	}

}
