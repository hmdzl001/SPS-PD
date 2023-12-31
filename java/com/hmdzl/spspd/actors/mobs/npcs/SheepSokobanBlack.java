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
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.sellitem.SheepFur;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.SokobanBlackSheepSprite;

public class SheepSokobanBlack extends NPC {

{
spriteClass = SokobanBlackSheepSprite.class;
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
public void add(Buff buff) {
}

	@Override
	public void beckon(int cell) {
		// Do nothing
	}

@Override
public boolean interact() {
	int traps = Dungeon.depth.countFleeceTraps(pos, 5);
	int newPos = -1;
	int curPos = pos;
	int count = 100;
	int dist = 6;
	boolean moved = false;
	
	if (traps>0){
	
	  do {
		 newPos = Dungeon.depth.randomRespawnCellSheep(pos, 5);
		 dist = Floor.distance(newPos, pos);
		 if (count-- <= 0) {
			break;
		 }
	  } while (newPos == -1);
	
	}

	if (newPos == -1) {
		
		
		//yell( Messages.get(Sheep.class, Random.element(LINE_KEYS)));
		//yell("pos = " + dist);		
		destroy();
		sprite.killAndErase();
		sprite.emitter().burst(ShadowParticle.UP, 5);
		moved=true;
  
	} else {
		//yell( Messages.get(Sheep.class, Random.element(LINE_KEYS)));
		//yell("pos = " + dist);
		Actor.freeCell(pos);
		CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		pos = newPos;
		move(pos);
		moved=true;
	}	

	if(moved){
	    Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
		Dungeon.hero.move(curPos);
	}
	
	Dungeon.hero.spend(1 / Dungeon.hero.speed());
	Dungeon.hero.busy();  
    return true;	
 }
	@Override
	public Item SupercreateLoot(){
		return new SheepFur();
	}
}
