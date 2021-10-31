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
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.NoodlemireSprite;

import com.hmdzl.spspd.windows.WndQuest;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.watabou.utils.Bundle;

public class Tinkerer4 extends NPC {

	{
		//name = "villager";
		spriteClass = NoodlemireSprite.class;
		properties.add(Property.HUMAN);
	}

	private static final String TXT_DUNGEON = "Otiluke? He came through here a few years ago.\n\n"
			                                   +"There used to be a mine to the Southwest of the town. "
			                                   +"It was overrun by demons and Otiluke sealed it off.\n\n"
			                                   +"We have not seen him since.";
	
	
	private static final String TXT_DUNGEON2 = "The mine was covered up by bushes in the Southwest corner of the town.  ";


	@Override
	protected boolean act() {
		throwItem();
		return super.act();
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
	protected Char chooseEnemy() {
		return null;
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
		
			if (first){
				tell(Messages.get(this, "tell1"));
				first=false;
			} else {
				tell(Messages.get(this, "tell2"));
			}	
         return true;			
		
	}

	private void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}

}
