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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.rings.RingOfElements;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class StoneIce extends Buff {
	
    private int pos;
	private float left;
	private static final String LEFT = "left";
	private static final String POS = "pos";
	private static final float DURATION = 8f;


	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(POS, pos);
		bundle.put(LEFT, left);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		pos = bundle.getInt(POS);
		left = bundle.getInt(LEFT);
	}
	
	{
		type = buffType.NEGATIVE;
	}	
	
	
		@Override
		public boolean attachTo(Char target) {
			pos = target.pos;
			return super.attachTo(target);
		}	
	
	public boolean act() {

		if (target.isAlive()) {
			
			if (target.pos != pos) {
				pos = 0;
				if (target.pos != -1)  pos = target.pos;
				target.damage(Math.min(1000,target.HT/20),this);
			} 
			
            Buff.detach( target, Burning.class);
	
		} else {
			detach();
		}
		
		spend(TICK);
		left -= TICK;		
		
		if (left <= 0 ) {
			detach();
		}		

		return true;
	}	

	public void reignite(Char ch) {
		left = duration(ch);
	}

	@Override
	public int icon() {
		return BuffIndicator.STONE_ICE;
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc",left);
	}

	public void set(float duration) {
		this.left = duration;
	};

	public float level() { return left; }

	public void level(int value) {
		if (left < value) {
			left = value;
		}
	}

	public static float duration(Char ch) {
		if (ch.isAlive() && (Level.water[ch.pos] && !ch.flying)){
		return DURATION;
		} else return DURATION;
		
	}	

	public void onDeath() {

		Badges.validateDeathFromFire();
		Dungeon.fail(Messages.format(ResultDescriptions.BURNING));

	}	
	
}
