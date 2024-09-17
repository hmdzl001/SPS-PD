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
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.challengelists.IceChallenge;
import com.hmdzl.spspd.items.sellitem.BottleFlower;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.JinkeloidSprite;
import com.watabou.utils.Random;

public class Jinkeloid extends NPC {

	{
		//name = "In vitro rose";
		//name = Messages.get(this,"name");
		spriteClass = JinkeloidSprite.class;
		properties.add(Property.ELEMENT);
	}

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}	
	
	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}


	@Override
	protected Char chooseEnemy() {
		return null;
	}

	@Override
	public void damage(int dmg, Object src, int type) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

   
	
	@Override
	public boolean interact() {
		
		sprite.turnTo(pos, Dungeon.hero.pos);
		switch (Random.Int (2)) {
            case 0:
			yell(Messages.get(this, "yell1"));
			break;
			case 1:
			//yell("Developer is in there... 576115291 ");
			yell(Messages.get(this, "yell2"));
			break;
		}
		if (Statistics.archersKilled > 99 && Statistics.skeletonsKilled > 99 && Statistics.albinoPiranhasKilled > 99 && Statistics.goldThievesKilled > 99){
			yell(Messages.get(this, "yell3"));
			Dungeon.depth.drop(new IceChallenge(), Dungeon.hero.pos).sprite.drop();
		}
		return true;
	}
	@Override
	public Item SupercreateLoot(){
		return new BottleFlower();
	}
}
