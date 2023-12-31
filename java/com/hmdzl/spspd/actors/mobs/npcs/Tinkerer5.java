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
import com.hmdzl.spspd.items.food.meatfood.FireMeat;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.Xavier251998Sprite;
import com.hmdzl.spspd.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Tinkerer5 extends NPC {

	{
		//name = "villager";
		spriteClass = Xavier251998Sprite.class;
		properties.add(Property.HUMAN);
	}

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
	}
	
	private boolean first=true;
	
	private static final String FIRST = "first";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(FIRST, first);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		first = bundle.getBoolean(FIRST);
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}


	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}


	@Override
	public boolean interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		    	
	      if(first) {
		       first=false;
		       tell(Messages.get(this, "tell3"));		
		       Dungeon.depth.drop(new FireMeat(), Dungeon.hero.pos).sprite.drop();
	        } else if (Random.Int(2)==0) {
				tell(Messages.get(this, "tell1"));
			} else {
				tell(Messages.get(this, "tell2"));
			}	

        return true;			
		
	}

	private void tell(String text ) {
		GameScene.show(new WndQuest(this,text ));
	}
}
