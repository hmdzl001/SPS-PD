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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.effects.particles.FlameParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

/**
 * Created by debenhame on 19/11/2014.
 */
public class BloodImbue extends FlavourBuff {

	public static final float DURATION = 10f;

	{
		type = buffType.POSITIVE;
	}


	public void proc(Char enemy) {
		switch (Random.Int(4)){
			case 0:
			Buff.affect(enemy, Cripple.class,3);
		    break;
		    case 1: 
			Buff.affect(enemy, Roots.class,3);
			break;
			case 2:
			Buff.affect(enemy, Paralysis.class,3);
			break;
			case 3:
			break;
		}
	}	

	@Override
	public int icon() {
		return BuffIndicator.PBLOOD;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}
	
	{
		immunities.add(Paralysis.class);
		immunities.add(Roots.class);
		immunities.add(Slow.class);
		immunities.add(Bleeding.class);
		immunities.add(Weakness.class);
	}

}
