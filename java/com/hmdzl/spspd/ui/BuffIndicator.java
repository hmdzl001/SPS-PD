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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.windows.WndInfoBuff;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.SparseArray;

public class BuffIndicator extends Component {

	public static final int NONE = -1;
	public static final int OVERFEED = 0;
	public static final int HUNGER = 1;
	public static final int STARVATION = 2;
	public static final int HT_UP = 3;
	public static final int ATTACK_UP = 4;
	public static final int DEFENCE_UP = 5;
	public static final int MAGIC_UP = 6;
	public static final int PHYSICS_SHIELD = 7;
	public static final int MAGIC_SHIELD = 8;
	public static final int ALL_SHIELD = 9;
	public static final int MECH_ARMOR = 10;
	public static final int STR_UP = 11;
	public static final int FIRE_1 = 12;
	public static final int ICE_1 = 13;
	public static final int EARTH_1 = 14;
	public static final int SHOCK_1 = 15;
	public static final int LIGHT_1 = 16;
	public static final int DARK_1 = 17;
	
	public static final int FURY = 18;
	public static final int STAR_BLESS = 19;
	public static final int HASTE = 20;
	public static final int DAMAGE_DELAY = 21;
	public static final int ATK_DOWN = 22;
	public static final int DEFENCE_DOWN = 23;
	public static final int MAGIC_DOWN = 24;
	public static final int POISON = 25;
	public static final int BLEEDING = 26;
	public static final int BE_CORRUPT = 27;
	public static final int BE_OLD = 28;
	public static final int STR_DOWN = 29;
	public static final int FIRE_2 = 30;
	public static final int ICE_2 = 31;
	public static final int EARTH_2 = 32;
	public static final int SHOCK_2 = 33;
	public static final int LIGHT_2 	= 34;
	public static final int DARK_2   = 35;
	
	public static final int COMBO     = 36;
	public static final int GLASS_SHIELD   = 37;
	public static final int CHARM = 38;
	public static final int REGEN_HEAL = 39;
	public static final int INVISIBLE = 40;
	public static final int MIND_VISION = 41;
	public static final int NATURE_BLESS = 42;	
	public static final int THORNS = 43;
	public static final int NEEDLING = 44;
	public static final int RECHARGING = 45;
	public static final int NOTICE = 46;
	public static final int DROWSY = 47;
	public static final int FIRE_3 = 48;
	public static final int ICE_3 = 49;
	public static final int EARTH_3 = 50;
	public static final int SHOCK_3 = 51;
	public static final int LIGHT_3  = 52;
	public static final int  DARK_3 = 53;
	
	public static final int SELF_DESTORY = 54;
	public static final int SILENT = 55;
	public static final int DISARM = 56;
	public static final int AMOK = 57;
	public static final int TARGET = 58;
	public static final int BLINDNESS = 59;
	public static final int DEWCHARGE = 60;
	public static final int FORESIGHT = 61;
	public static final int VERTIGO = 62;
	public static final int LOCKED = 63;
	public static final int MAGIC_WEAK = 64;
	public static final int MAGIC_SLEEP = 65;
	public static final int TAROIL = 66;
	public static final int ROOTS = 67;
	public static final int NATURE_SHIELD = 68;
	public static final int PARALYSIS = 69;
	public static final int TORCH_LIGHT = 70;
	public static final int COUNTDOWN = 71;
	
	public static final int MAGIC_MIRROR = 72;
	public static final int EX_PROTECT = 73;
	public static final int SMASH_HIT = 74;
	public static final int CLIMB_HIT = 75;
	public static final int MUSIC_RHYTHM = 76;
	public static final int MUSIC_RHYTHM2 = 77;
	public static final int MUSIC_WARGROOVE = 78;
	public static final int MUSIC_FLOWER = 79;
	public static final int LEVITATION = 80;
	public static final int BLOCK_SHIELD = 81;
	public static final int NO_FUSHIGI = 82;
	public static final int GOLDTOUCH = 83;
	public static final int EATER_FEED = 84;
	public static final int INF_JUMP = 85;
	public static final int CRIPPLE = 86;
	public static final int TAUNT = 87;
	public static final int SLOW_SPEED = 88;
	public static final int GAS_IMM = 89;
	
	public static final int FAITH_HOLY = 90;
	public static final int FAITH_DEMON = 91;
	public static final int FAITH_BALANCE = 92;
	public static final int FAITH_NATURE = 93;
	public static final int FAITH_MECH = 94;
	public static final int CRAZY_MIND = 95;
	public static final int FIGHT_MIND = 96;


	public static final int SIZE = 7;

	/*private static BuffIndicator heroInstance;

	private SmartTexture texture;
	private TextureFilm film;

	private SparseArray<Image> icons = new SparseArray<Image>();

	private Char ch;

	public BuffIndicator(Char ch) {
		super();

		this.ch = ch;
		if (ch == Dungeon.hero) {
			heroInstance = this;
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		if (this == heroInstance) {
			heroInstance = null;
		}
	}

	@Override
	protected void createChildren() {
		texture = TextureCache.get(Assets.BUFFS_SMALL);
		film = new TextureFilm(texture, SIZE, SIZE);
	}

	@Override
	protected void layout() {
		clear();

		SparseArray<Image> newIcons = new SparseArray<Image>();

		for (Buff buff : ch.buffs()) {
			int icon = buff.icon();
			if (icon != NONE) {
				Image img = new Image(texture);
				img.frame(film.get(icon));
				img.x = x + members.size() * (SIZE + 2);
				img.y = y;
				add(img);

				newIcons.put(icon, img);
			}
		}

		for (Integer key : icons.keyArray()) {
			if (newIcons.get(key) == null) {
				Image icon = icons.get(key);
				icon.origin.set(SIZE / 2);
				add(icon);
				add(new AlphaTweener(icon, 0, 0.6f) {
					@Override
					protected void updateValues(float progress) {
						super.updateValues(progress);
						image.scale.set(1 + 5 * progress);
					};
				});
			}
		}

		icons = newIcons;
	}

	public static void refreshHero() {
		if (heroInstance != null) {
			heroInstance.layout();
		}
	}*/
	private static BuffIndicator heroInstance;
	
	private SmartTexture texture;
	private TextureFilm film;
	
	private SparseArray<BuffIcon> icons = new SparseArray<BuffIcon>();
	
	private Char ch;
	
	public BuffIndicator( Char ch ) {
		super();
		
		this.ch = ch;
		if (ch == Dungeon.hero) {
			heroInstance = this;
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		if (this == heroInstance) {
			heroInstance = null;
		}
	}
	
	@Override
	protected void createChildren() {
		texture = TextureCache.get( Assets.BUFFS_SMALL );
		film = new TextureFilm( texture, SIZE, SIZE );
	}
	
	@Override
	protected void layout() {
		clear();
		
		SparseArray<BuffIcon> newIcons = new SparseArray<BuffIcon>();

		int maxIconsPerRow = ShatteredPixelDungeon.landscape() ? 12 : 6;

		for (Buff buff : ch.buffs()) {
			if (buff.icon() != NONE) {
				BuffIcon icon = new BuffIcon( buff );
				icon.setRect(x + (members.size() % maxIconsPerRow) * (SIZE + 2),
						y + (members.size()/maxIconsPerRow) * (SIZE + 6),
						9, 12);
				add(icon);
				newIcons.put( buff.icon(), icon );
			}
		}


		for (Integer key : icons.keyArray()) {
			if (newIcons.get( key ) == null) {
				Image icon = icons.get( key ).icon;
				icon.origin.set( SIZE/2 );
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

	private class BuffIcon extends Button {

		private Buff buff;

		public Image icon;

		private BitmapText BuffText;

		public BuffIcon( Buff buff ){
			super();
			this.buff = buff;

			icon = new Image( texture );
			icon.frame( film.get( buff.icon() ) );
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
		public void update() {
			super.update();
			String status = buff.status();
			if (ch == Dungeon.hero && status != null) {
				BuffText.scale.set(PixelScene.align(0.5f));
				BuffText.x = icon.x + SIZE / 2 - BuffText.width() / 2;
				BuffText.y = icon.y + SIZE;
				BuffText.hardlight(0xCACFC2);
				BuffText.text(status + "");
				BuffText.visible = true;
			} else {
				BuffText.visible = false;
			}
		}

		@Override
		protected void onClick() {
			GameScene.show(new WndInfoBuff(buff));
		}
	}
	
	public static void refreshHero() {
		if (heroInstance != null) {
			heroInstance.layout();
		}
	}

}
