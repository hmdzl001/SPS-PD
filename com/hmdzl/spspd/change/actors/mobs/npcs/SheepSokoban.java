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
package com.hmdzl.spspd.change.actors.mobs.npcs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.SokobanSheepSprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Random;

public class SheepSokoban extends NPC {


{
spriteClass = SokobanSheepSprite.class;
properties.add(Property.UNKNOW);
}


@Override
protected boolean act() {
	throwItem();
	return super.act();
}

@Override
public void add(Buff buff) {
}

@Override
public void damage(int dmg, Object src) {
}

/*  -W-1 -W  -W+1
 *  -1    P  +1
 *  W-1   W  W+1
 * 
 */

@Override
public void interact() {
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
	
	if (movPos != pos && (Level.passable[movPos] || Level.avoid[movPos]) && Actor.findChar(movPos) == null){
		
		moveSprite(curPos,movPos);
		move(movPos);
		moved=true;
			
	} 
	
	if(moved){
      Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
	  Dungeon.hero.move(curPos);
	}
	
    //yell(Random.element(QUOTES));
    
}

}
