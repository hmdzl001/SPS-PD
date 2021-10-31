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
package com.hmdzl.spspd;

import com.hmdzl.spspd.levels.Level;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.Tilemap;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

public class DungeonTilemap extends Tilemap {

	public static final int SIZE = 16;

	private static DungeonTilemap instance;

	public DungeonTilemap() {
		super(Dungeon.level.tilesTex(), new TextureFilm(
				Dungeon.level.tilesTex(), SIZE, SIZE));
		map(Dungeon.level.map, Level.getWidth());

		instance = this;
	}

	public int screenToTile(int x, int y) {
		Point p = camera().screenToCamera(x, y).offset(this.point().negate())
				.invScale(SIZE).floor();
		return p.x >= 0 && p.x < Level.getWidth() && p.y >= 0 && p.y < Level.HEIGHT ? p.x
				+ p.y * Level.getWidth()
				: -1;
	}

	@Override
	public boolean overlapsPoint(float x, float y) {
		return true;
	}

	public void discover(int pos, int oldValue) {

		final Image tile = tile(oldValue);
		tile.point(tileToWorld(pos));

		// For bright mode
		tile.rm = tile.gm = tile.bm = rm;
		tile.ra = tile.ga = tile.ba = ra;
		parent.add(tile);

		parent.add(new AlphaTweener(tile, 0, 0.6f) {
			@Override
			protected void onComplete() {
				tile.killAndErase();
				killAndErase();
			}
        });
	}

	public static PointF tileToWorld(int pos) {
		return new PointF(pos % Level.getWidth(), pos / Level.getWidth()).scale(SIZE);
	}

	public static PointF tileCenterToWorld(int pos) {
		return new PointF((pos % Level.getWidth() + 0.5f) * SIZE,
				(pos / Level.getWidth() + 0.5f) * SIZE);
	}

	public static Image tile(int index) {
		Image img = new Image(instance.texture);
		img.frame(instance.tileset.get(index));
		return img;
	}
	
	public static PointF raisedTileCenterToWorld( int pos ) {
		return new PointF(
				(pos % Level.WIDTH + 0.5f) * SIZE,
				(pos / Level.WIDTH + 0.1f) * SIZE );
	}	

	@Override
	public boolean overlapsScreenPoint(int x, int y) {
		return true;
	}
}
