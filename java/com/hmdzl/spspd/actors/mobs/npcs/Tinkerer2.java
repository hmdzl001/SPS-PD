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
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.quest.Mushroom;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.LynnSprite;
 
import com.hmdzl.spspd.windows.WndQuest;
import com.hmdzl.spspd.windows.WndTinkerer2;
import com.hmdzl.spspd.messages.Messages;

public class Tinkerer2 extends NPC {

	{
		//name = "tinkerer";
		spriteClass = LynnSprite.class;
		properties.add(Property.ELF);
		properties.add(Property.IMMOVABLE);
	}

	private static final String TXT_DUNGEON = "I'm scavenging for toadstool mushrooms. "
			+ "Could you bring me any toadstool mushrooms you find? ";
	

	private static final String TXT_MUSH = "Any luck finding toadstool mushrooms, %s?";

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
		Item item = Dungeon.hero.belongings.getItem(Mushroom.class);
		Item acmrd = Dungeon.hero.belongings.getItem(ActiveMrDestructo.class);
		if (item != null && acmrd != null) {
				GameScene.show(new WndTinkerer2(this, item, acmrd));
			} else if (item != null) {
				GameScene.show(new WndTinkerer2(this, item, null));
			} else {
				tell(Messages.get(this, "tell1"));
			}
		return false;
	}

	private void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}
}
