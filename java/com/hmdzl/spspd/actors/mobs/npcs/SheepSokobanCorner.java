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
package com.hmdzl.spspd.actors.mobs.npcs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.sellitem.SheepFur;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.SokobanCornerSheepSprite;

public class SheepSokobanCorner extends NPC {


{
spriteClass = SokobanCornerSheepSprite.class;
	properties.add(Property.UNKNOW);
}


@Override
protected boolean act() {
	throwItem();
	return super.act();
}

@Override
public void damage(int dmg, Object src) {
}

	@Override
	public void beckon(int cell) {
		// Do nothing
	}

@Override
public void add(Buff buff) {
}

/*  -W-1 -W  -W+1
 *  -1    P  +1
 *  W-1   W  W+1
 * 
 */

@Override
public boolean interact() {
	int curPos = pos;
	int movPos = pos;
	int width = Level.getWidth();
    boolean moved = false;
	int posDif = Dungeon.hero.pos-curPos;
	
	if (posDif==1) {
		movPos = curPos-1;
	} else if(posDif==-1) {
		movPos = curPos+1;
	} else if(posDif==width) {
		movPos = curPos-width;
	} else if(posDif==-width) {
		movPos = curPos+width;
	} 
	
	else if(posDif==-width+1) {
		movPos = curPos+width-1;
		
	} else if(posDif==-width-1) {
		movPos = curPos+width+1;
		
	} else if(posDif==width+1) {
		movPos = curPos-(width+1);
		
	} else if(posDif==width-1) {
		movPos = curPos-(width-1);
	}    
	
	if (movPos != pos && (Level.passable[movPos] || Level.avoid[movPos]) && Actor.findChar(movPos) == null){
		
		moveSprite(curPos,movPos);
		move(movPos);
		moved=true;
			
	} 
	
	if(moved){
      Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
	  Dungeon.hero.move(curPos);
	}
	Dungeon.hero.spend(1 / Dungeon.hero.speed());
	Dungeon.hero.busy();  
	return true;
    //yell(Random.element(QUOTES));
    
}
	@Override
	public Item SupercreateLoot(){
		return new SheepFur();
	}
}
