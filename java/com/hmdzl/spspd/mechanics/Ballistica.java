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
package com.hmdzl.spspd.mechanics;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.levels.Floor;

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
	public static final int PASS_LIGHT = 8; //ballistica won't stop on terrain(LOS blocking, impassable, etc.)

	public static final int PROJECTILE =  	STOP_TARGET	| STOP_CHARS | STOP_TERRAIN;

	public static final int MAGIC_BOLT =    STOP_CHARS  | STOP_TERRAIN;

	public static final int WONT_STOP =  0;

	public static int[] trace = new int[Math.max(Floor.getWidth(), Floor.getLength())];
	public static int distance;

	public Ballistica( int from, int to, int params ){
		sourcePos = from;
		build(from, to, 
		(params & STOP_TARGET) > 0, 
		(params & STOP_CHARS) > 0, 
		(params & STOP_TERRAIN) > 0,
		(params & PASS_LIGHT) > 0);
		
		if (collisionPos != null){
			dist = path.indexOf( collisionPos );
		} else if (!path.isEmpty()) {
			collisionPos = path.get( dist=path.size()-1 );
		} else {
			path.add(from);
			collisionPos = from;
			dist = 0;
		}
	}
	
	private void build( int from, int to, boolean stopTarget, boolean stopChars, boolean stopTerrain, boolean passlight ) {
		int w = Floor.getWidth();

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
		while (Floor.insideMap(cell)) {

			if (collisionPos == null
					&& stopTerrain
					&& cell != sourcePos
					&& !Dungeon.depth.passable[cell]
					&& !Dungeon.depth.avoid[cell]
					&& Actor.findChar(cell) == null) {
				collide(path.get(path.size() - 1));
			}

			if (collisionPos == null
					&& passlight
					&& cell != sourcePos
					&& !Dungeon.depth.passable[cell]
					&& !Dungeon.depth.avoid[cell]
					&& !Dungeon.depth.lightpass[cell]
					&& Actor.findChar(cell) == null) {
				collide(path.get(path.size() - 1));
			}
			
            path.add(cell);
			
			
			if (collisionPos == null && stopTerrain && cell != sourcePos && Dungeon.depth.solid[cell]) {
				//if (passlight && (Dungeon.depth.passable[cell] || Dungeon.depth.map[cell] == Terrain.GLASS_WALL)) {
				//if ( passlight && (Dungeon.depth.passable[cell] || Dungeon.depth.avoid[cell] || Dungeon.depth.lightpass[cell] )) {
					//do nothing
				//} else {
					collide(cell);
				//}
			}
			if (collisionPos == null && cell != sourcePos && stopChars && Actor.findChar( cell ) != null) {
				collide(cell);
			}
			if (collisionPos == null && cell == to && stopTarget){
				collide(cell);
			}

			if (collisionPos == null && cell == to && passlight && Actor.findChar( cell ) != null ){
				if ((Dungeon.depth.passable[cell] || Dungeon.depth.avoid[cell] || Dungeon.depth.lightpass[cell] )) {
					//do nothing
				} else {
					collide(cell);
				}
			}
			
			//if we're in a wall, collide with the previous cell along the path.
			//if (passlight && cell != sourcePos && !Floor.passable[cell] && !( Floor.avoid[cell] || Dungeon.depth.map[cell] == Terrain.GLASS_WALL)) {
			//	collide(path.get(path.size() - 1));
			//}

			//if (stopTerrain && cell != sourcePos && !Floor.passable[cell] && !Floor.avoid[cell] ) {
			//	collide(path.get(path.size() - 1));
			//}

			

			//if ((stopTerrain && cell != sourcePos && Floor.losBlockHigh[cell])
			//		|| (cell != sourcePos && stopChars && Actor.findChar( cell ) != null)
			//		|| (cell == to && stopTarget)

			//		){
			//	collide(cell);
			//}

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
			ShatteredPixelDungeon.reportException(e);
			return new ArrayList<>();
		}
	}

	
}
