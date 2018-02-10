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
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.items.DewVial;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.quest.Mushroom;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.TinkererSprite;
 
import com.hmdzl.spspd.change.windows.WndQuest;
import com.hmdzl.spspd.change.windows.WndTinkerer;
import com.hmdzl.spspd.change.messages.Messages;

public class Tinkerer1 extends NPC {

	{
		//name = "tinkerer";
		spriteClass = TinkererSprite.class;
		properties.add(Property.HUMAN);
	    properties.add(Property.IMMOVABLE);
	}

	private static final String TXT_DUNGEON = "Oh wow, have you seen this dungeon! This is an awesome dungeon.  "
			+ "I'm here checking things out of course, but also scavenging for toadstool mushrooms. "
			+ "Could you bring me any toadstool mushrooms you find? ";
	
	private static final String TXT_DUNGEON2 = "Oh wow, have you seen this dungeon! This is an awesome dungeon.  ";

	private static final String TXT_MUSH = "Any luck finding toadstool mushrooms, %s?";

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	@Override
	public int defenseSkill(Char enemy) {
		return 1000;
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
	public void interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		Item item = Dungeon.hero.belongings.getItem(Mushroom.class);
		Item vial = Dungeon.hero.belongings.getItem(DewVial.class);
			if (item != null && vial != null) {
				GameScene.show(new WndTinkerer(this, item));
			} else if (item == null && vial != null) {
				tell(Messages.get(this, "tell1"));
			} else {
				tell(Messages.get(this, "tell2"));
			}
		
	}

	private void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}

}
