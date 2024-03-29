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
package com.hmdzl.spspd.effects;

import android.opengl.GLES20;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.DungeonTilemap;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.Arrays;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class Lightning extends Group {

	private static final float DURATION = 0.3f;
	
	private float life;

	private List<Arc> arcs;
	
	private Callback callback;

	public Lightning(int from, int to, Callback callback){
		this(Arrays.asList(new Arc(from, to)), callback);
	}
	
	public Lightning( List<Arc> arcs, Callback callback ) {
		
		super();

		this.arcs = arcs;
		for (Arc arc : this.arcs)
			add(arc);

		this.callback = callback;
		
		life = DURATION;
		
		Sample.INSTANCE.play( Assets.SND_LIGHTNING );
	}
	
	private static final double A = 180 / Math.PI;
	
	@Override
	public void update() {
		if ((life -= Game.elapsed) < 0) {
			
			killAndErase();
			if (callback != null) {
				callback.call();
			}
			
		} else {
			
			float alpha = life / DURATION;
			
			for (Arc arc : arcs) {
				arc.alpha(alpha);
			}

			super.update();
		}
	}
	
	@Override
	public void draw() {
		GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
		super.draw();
		GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
	}

	//A lightning object is meant to be loaded up with arcs.
	//these act as a means of easily expressing lighting between two points.
	public static class Arc extends Group {

		private Image arc1, arc2;

		//starting and ending x/y values
		private PointF start, end;

		public Arc(int from, int to){
			start = DungeonTilemap.tileCenterToWorld(from);
			end = DungeonTilemap.tileCenterToWorld(to);

			arc1 = new Image(Effects.get(Effects.Type.LIGHTNING));
			arc1.x = start.x - arc1.origin.x;
			arc1.y = start.y - arc1.origin.y;
			arc1.origin.set( 0, arc1.height()/2 );
			add( arc1 );

			arc2 = new Image(Effects.get(Effects.Type.LIGHTNING));
			arc2.origin.set( 0, arc2.height()/2 );
			add( arc2 );

		}

		public void alpha(float alpha) {
			arc1.am = arc2.am = alpha;
		}

		@Override
		public void update() {
			float x2 = (start.x + end.x) / 2 + Random.Float( -4, +4 );
			float y2 = (start.y + end.y) / 2 + Random.Float( -4, +4 );

			float dx = x2 - start.x;
			float dy = y2 - start.y;
			arc1.angle = (float)(Math.atan2( dy, dx ) * A);
			arc1.scale.x = (float)Math.sqrt( dx * dx + dy * dy ) / arc1.width;

			dx = end.x - x2;
			dy = end.y - y2;
			arc2.angle = (float)(Math.atan2( dy, dx ) * A);
			arc2.scale.x = (float)Math.sqrt( dx * dx + dy * dy ) / arc2.width;
			arc2.x = x2 - arc2.origin.x;
			arc2.y = y2 - arc2.origin.x;
		}
	}
}
