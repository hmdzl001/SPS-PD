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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.PuddingCup;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.sellitem.DevUpPlan;
import com.hmdzl.spspd.items.wands.WandOfTest;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CoconutSprite;

public class Leadercn extends NPC {

	{
		//name = Messages.get(this,"name");
		spriteClass = CoconutSprite.class;
		properties.add(Property.MECH);
		
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
	public Item SupercreateLoot(){
		return new DevUpPlan();
	}
	@Override
	protected Char chooseEnemy() {
		return null;
	}

	@Override
	public void damage(int dmg, Object src) {
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
		switch (Dungeon.sacrifice) {
            case 0:
			yell(Messages.get(this, "yell1"));
				Dungeon.level.drop(new IronKey(Dungeon.depth), Dungeon.hero.pos).sprite.drop();
			break;
			case 1:
			yell(Messages.get(this, "yell2"));
			break;
			case 2:
			yell(Messages.get(this, "yell3"));
			break;	
            case 3:
			yell(Messages.get(this, "yell4"));
			break;			
            case 4:
			yell(Messages.get(this, "yell5"));
			Dungeon.level.drop(new StoneOre(), Dungeon.hero.pos).sprite.drop();
			break;			
            case 5:
			yell(Messages.get(this, "yell6"));
			Dungeon.level.drop(new WandOfTest().identify(), Dungeon.hero.pos).sprite.drop();
			break;			
            case 6:
			yell(Messages.get(this, "yell7"));
			Dungeon.level.drop(new DungeonBomb(), Dungeon.hero.pos).sprite.drop();
			break;			
            case 7:
			yell(Messages.get(this, "yell8"));
				Dungeon.level.drop(new PuddingCup(), this.pos).sprite.drop();
			break;						
		}
		Dungeon.sacrifice++;
		this.destroy();
		this.sprite.die();
		return true;
	}
}
