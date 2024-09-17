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


import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Flag;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.FishCracker;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.HBBSprite;
import com.watabou.utils.Random;

public class HBB extends NPC {

	{
		//name = "Rawberry";
		name = Messages.get(this,"name");
		spriteClass = HBBSprite.class;
		properties.add(Property.BEAST);
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
		switch (Random.Int (4)) {
            case 0:
			yell(Messages.get(this, "yell1"));	
			break;
			case 1:
			yell(Messages.get(this, "yell2"));
			break;
			case 2:
			yell(Messages.get(this, "yell3"));
			break;
			case 3:
				if (Badges.checkOtilukeRescued())
				Dungeon.depth.drop(new Flag(), Dungeon.hero.pos).sprite.drop();
			break;
		}
		return false;
	}
	@Override
	public Item SupercreateLoot(){
		return new FishCracker();
	}
}
