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
package com.hmdzl.spspd.change.mechanics;

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.levels.Level;

import java.util.ArrayList;
import java.util.List;

public class Ballistica {

	public ArrayList<Integer> path = new ArrayList<>();
	public Integer sourcePos = null;
	public Integer collisionPos = null;
	public Integer dist = 0;

	//parameters to specify the colliding cell
	public static final int STOP_TARGET = 1; //ballistica will stop at the target cell
	public static final int STOP_CHARS = 2; //ballistica will stop on first char hit
	public static final int STOP_TERRAIN = 4; //ballistica will stop on terrain(LOS blocking, impassable, etc.)

	public static final int PROJECTILE =  	STOP_TARGET	| STOP_CHARS	| STOP_TERRAIN;

	public static final int MAGIC_BOLT =    STOP_CHARS  | STOP_TERRAIN;

	public static final int WONT_STOP =     0;

	public static int[] trace = new int[Math.max(Level.getWidth(), Level.HEIGHT)];
	public static int distance;

	public Ballistica( int from, int to, int params ){
		sourcePos = from;
		build(from, to, (params & STOP_TARGET) > 0, (params & STOP_CHARS) > 0, (params & STOP_TERRAIN) > 0);
		if (collisionPos != null)
			dist = path.indexOf( collisionPos );
		else
			collisionPos = path.get( dist=path.size()-1 );
	}
	
	private void build( int from, int to, boolean stopTarget, boolean stopChars, boolean stopTerrain ) {
		int w = Level.WIDTH;

		int x0 = from % w;
		int x1 = to % w;
		int y0 = from / w;
		int y1 = to / w;

		int dx = x1 - x0;
		int dy = y1 - y0;

		int stepX = dx > 0 ? +1 : -1;
		int stepY = dy > 0 ? +1 : -1;

		dx = Math.abs( dx );
		dy = Math.abs( dy );

		int stepA;
		int stepB;
		int dA;
		int dB;

		if (dx > dy) {

			stepA = stepX;
			stepB = stepY * w;
			dA = dx;
			dB = dy;

		} else {

			stepA = stepY * w;
			stepB = stepX;
			dA = dy;
			dB = dx;

		}

		int cell = from;

		int err = dA / 2;
		while (Level.insideMap(cell)) {

			//if we're in a wall, collide with the previous cell along the path.
			if (stopTerrain && cell != sourcePos && !Level.passable[cell] && !Level.avoid[cell]) {
				collide(path.get(path.size() - 1));
			}

			path.add(cell);

			if ((stopTerrain && cell != sourcePos && Level.losBlockHigh[cell])
					|| (cell != sourcePos && stopChars && Actor.findChar( cell ) != null)
					|| (cell == to && stopTarget)){
				collide(cell);
			}

			cell += stepA;

			err += dB;
			if (err >= dA) {
				err = err - dA;
				cell = cell + stepB;
			}
		}
	}
	
	//we only want to record the first position collision occurs at.
	private void collide(int cell){
		if (collisionPos == null)
			collisionPos = cell;
	}

	//returns a segment of the path from start to end, inclusive.
	//if there is an error, returns an empty arraylist instead.
	public List<Integer> subPath(int start, int end){
		try {
			end = Math.min( end, path.size()-1);
			return path.subList(start, end+1);
		} catch (Exception e){
			return new ArrayList<>();
		}
	}

	
}
