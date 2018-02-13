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
package com.hmdzl.spspd.change.actors.mobs;

import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.levels.traps.SummoningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.food.Meat;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.hmdzl.spspd.change.sprites.RatSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class TestMob extends Mob {
	

	private boolean skill = false;

	{
		spriteClass = ErrorSprite.class;

		HP = HT = 500;
		defenseSkill = 0;
		
		properties.add(Property.UNKNOW);
	}


	@Override
    public boolean act() {
        if( HP < HT && skill == false ) {
			Buff.affect(this,AttackUp.class,5f).level(20);
			Buff.affect(this,DefenceUp.class,5f).level(80);
            skill = true;
        }
        return super.act();
    }

	@Override
	public void damage(int dmg, Object src) {

		super.damage(dmg, src);
		Buff.prolong(this,ArmorBreak.class,10f).level(50);
	}

	@Override
	public int damageRoll() {
		return 10;
	}

	@Override
	public int attackSkill(Char target) {
		return 100;
	}

	@Override
	public int dr() {
		return 0;
	}
	
	private final String SKILL = "skill";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SKILL, skill);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		skill = bundle.getBoolean(SKILL);
	}	

}
