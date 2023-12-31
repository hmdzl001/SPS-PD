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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Floor;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.PointF;

public class EnemyheadSprite extends MovieClip {

	public static final int SIZE = 16;

	//private static final float DROP_INTERVAL = 0.4f;

	protected static TextureFilm film;

	public Heap heap;

	//private Glowing glowing;
	//private float phase;
	//private boolean glowUp;

	private float dropInterval;

	public EnemyheadSprite() {
		this(ItemSpriteSheet.SOMETHING);
	}

	//public EnemyheadSprite(Item item) {
		//this(item.image(), item.glowing());
	//}

	public EnemyheadSprite(int image) {
		super(Assets.ITEMS);

		if (film == null) {
			film = new TextureFilm(texture, SIZE, SIZE);
		}

	}

	public void originToCenter() {
		origin.set(SIZE / 2);
	}

	public void link() {
		link(heap);
	}

	public void link(Heap heap) {
		this.heap = heap;
		//view(heap.image(), heap.glowing());
		place(heap.pos);
	}

	@Override
	public void revive() {
		super.revive();

		speed.set(0);
		acc.set(0);
		dropInterval = 0;

		heap = null;
	}

	public PointF worldToCamera(int cell) {
		final int csize = DungeonTilemap.SIZE;

		return new PointF(cell % Floor.getWidth() * csize + (csize - SIZE) * 0.5f,
				cell / Floor.getWidth() * csize + (csize - SIZE) * 0.5f);
	}

	public void place(int p) {
		point(worldToCamera(p));
	}



}
