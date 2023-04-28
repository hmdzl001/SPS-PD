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
package com.hmdzl.spspd.sprites;

import android.graphics.Bitmap;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.PointF;

public class HeroSkinSprite extends MovieClip {

	public static final int SIZE = 15;

	protected static TextureFilm film;

	public HeroSkinSprite() {
		this(HeroSkinSpriteSheet.WARRIOR);
	}

	public HeroSkinSprite(Item item) {
		this(item.image());
	}

	public HeroSkinSprite(int image) {
		super(Assets.HERO_SKIN);

		if (film == null) {
			film = new TextureFilm(texture, SIZE, SIZE);
		}

		view(image);
	}

	public void originToCenter() {
		origin.set(SIZE / 2);
	}

	public void link() {
		link();
	}

	@Override
	public void revive() {
		super.revive();

		speed.set(0);
		acc.set(0);

	}

	public PointF worldToCamera(int cell) {
		final int csize = DungeonTilemap.SIZE;

		return new PointF(cell % Level.getWidth() * csize + (csize - SIZE) * 0.5f,
				cell / Level.getWidth() * csize + (csize - SIZE) * 0.5f);
	}

	public void place(int p) {
		point(worldToCamera(p));
	}

	public HeroSkinSprite view(int image) {
		frame(film.get(image));
		return this;
	}

	public static int pick(int index, int x, int y) {
		Bitmap bmp = TextureCache.get(Assets.ITEMS).bitmap;
		int rows = bmp.getWidth() / SIZE;
		int row = index / rows;
		int col = index % rows;
		return bmp.getPixel(col * SIZE + x, row * SIZE + y);
	}

	public static class Glowing {

		public static final ItemSprite.Glowing WHITE = new ItemSprite.Glowing(0xFFFFFF, 0.6f);

		public float red;
		public float green;
		public float blue;
		public float period;

		public Glowing(int color) {
			this(color, 1f);
		}

		public Glowing(int color, float period) {
			red = (color >> 16) / 255f;
			green = ((color >> 8) & 0xFF) / 255f;
			blue = (color & 0xFF) / 255f;

			this.period = period;
		}
	}

}
