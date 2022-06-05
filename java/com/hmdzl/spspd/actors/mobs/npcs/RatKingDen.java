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
import com.hmdzl.spspd.items.reward.SewerReward;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.RatKingSprite;

public class RatKingDen extends NPC {

	{
		//name = "rat king";
		spriteClass = RatKingSprite.class;

		state = SLEEPING;
		properties.add(Property.BEAST);
		properties.add(Property.BOSS);
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public float speed() {
		return 2f;
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
	public Item SupercreateLoot(){
			return new SewerReward();
	}
	
	@Override
	public boolean interact() {
		
		sprite.turnTo(pos, Dungeon.hero.pos);
		if (state == SLEEPING) {
			notice();
			yell(Messages.get(this, "not_sleeping"));
			state = WANDERING;		
			
		} else {
			yell(Messages.get(this, "what_is_it"));
		}
		return true;
	}

	@Override
	public String description() {
		return ((RatKingSprite) sprite).festive ? 
		Messages.get(this, "desc_festive")
				: super.description();
	}
}
