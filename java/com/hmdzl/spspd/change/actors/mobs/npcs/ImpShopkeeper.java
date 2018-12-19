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
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.sprites.ImpSprite;
 
import com.hmdzl.spspd.change.messages.Messages;

public class ImpShopkeeper extends Shopkeeper {

	{
		//name = "ambitious imp";
		spriteClass = ImpSprite.class;
		properties.remove(Property.HUMAN);
		properties.add(Property.DEMONIC);
		properties.add(Property.IMMOVABLE);
	}

	private boolean seenBefore = false;
	private boolean killedYog = false;

	@Override
	protected boolean act() {

		if (!seenBefore && Dungeon.visible[pos]) {
			yell(Messages.get(this, "greeting", Dungeon.hero.givenName()));
			seenBefore = true;
		}
		
		if (Statistics.amuletObtained && !killedYog && Dungeon.visible[pos]) {
			yell(Messages.get(this, "greeting2", Dungeon.hero.givenName()));
			killedYog = true;
		}

		return super.act();
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}
}
