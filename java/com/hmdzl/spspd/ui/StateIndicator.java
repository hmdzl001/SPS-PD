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
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.windows.WndMessage;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.SparseArray;

public class StateIndicator extends Component {

	public static final int NONE = -1;

	public static final int HP_ICON = 0;
	public static final int STR_ICON = 1;
	public static final int EXP_ICON = 2;
	public static final int TIME_ICON = 3;
	public static final int GOLD_ICON = 4;
	public static final int ACU_ICON = 5;
	public static final int DEX_ICON = 6;
	public static final int MIG_ICON = 7;
	public static final int HUNGER_ICON = 8;
	public static final int MELEE_CRI_ICON = 9;
	public static final int RANGE_CRI_ICON = 10;
	public static final int MAGIC_CRI_ICON= 11;
	public static final int MOVE_ICON = 12;
	public static final int ATK_DELAY_ICON = 13;
	public static final int LUCKY_ICON = 14;
	public static final int SPEED_ICON = 15;
	public static final int KILL_ICON = 16;
	public static final int FINISH_GAME_ICON = 17;
	public static final int HT_ICON = 18;
	public static final int DAMAGE_REDUCE_ICON = 19;
	public static final int ATTACK_RANGE_ICON = 20;
	public static final int WAND_CHARGE_ICON = 21;
	public static final int HIDEN_ICON = 22;

	public static final int SIZE = 7;

	//protected Image icon;

	private SmartTexture texture;
	private TextureFilm film;

	private SparseArray<StateIndicator.StateIcon> icons = new SparseArray<StateIndicator.StateIcon>();
	
	public StateIndicator(){
		super();
	}

	protected void createChildren() {
		texture = TextureCache.get( Assets.STATE_ICON );
		film = new TextureFilm( texture, SIZE, SIZE );
	}

	@Override
	protected void layout() {
		clear();
		SparseArray<StateIndicator.StateIcon> newIcons = new SparseArray<StateIndicator.StateIcon>();

		for (Integer key : icons.keyArray()) {
			if (newIcons.get( key ) == null) {
				Image icon = icons.get( key ).icon;
				//icon.origin.set( SIZE / 2 );
				add( icon );
				add( new AlphaTweener( icon, 0, 0.6f ) {
					@Override
					protected void updateValues( float progress ) {
						super.updateValues( progress );
						image.scale.set( 1 + 5 * progress );
					}
				} );
			}
		}

		icons = newIcons;
	}

	private class StateIcon extends Button {
		public Image icon;

		private BitmapText BuffText;

		public StateIcon( StateIndicator type ){
			super();
			icon = new Image( texture );
			icon.frame( film.get( type.icon()) );
			add( icon );
		}

		@Override
		protected void layout() {
			super.layout();
			icon.x = this.x+1;
			icon.y = this.y+2;

			BuffText = new BitmapText( PixelScene.font1x );
			add( BuffText );

		}

		@Override
		protected void onTouchDown() {
			if (icon != null) icon.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}

		@Override
		protected void onTouchUp() {
			if (icon != null) icon.resetColor();
		}

		@Override
		protected void onClick() {
			GameScene.show(new WndMessage("???"));
		}
		
		
	}

	public int icon() {
		return StateIndicator.NONE;
	}

}
