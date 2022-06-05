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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class DBurning extends Buff implements Hero.Doom {

	private static final String TXT_BURNS_UP = "%s burns up!";
	private static final String TXT_BURNED_TO_DEATH = "You burned to death...";

	private static final float DURATION = 3f;

	private float left;

	private static final String LEFT = "left";

	{
		type = buffType.NEGATIVE;
	}	
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEFT, left);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		left = bundle.getFloat(LEFT);
	}

	@Override
	public boolean act() {

		if (target.isAlive()) {

			if (target instanceof Hero) {
				Buff.prolong(target, Light.class, TICK * 1.01f);
			}

			target.damage(Random.Int(1, Math.min(1000,target.HT/10)), this);
			Buff.detach( target, Chill.class);

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
	
	@Override
	public int icon() {
		return BuffIndicator.FIRE;
	}

	public void set(float duration) {
		this.left = duration;
	}

	public float level() { return left; }

	public void level(int value) {
		if (left < value) {
			left = value;
		}
	}	
	
	@Override
	public void fx(boolean on) {
		if (on) target.sprite.add(CharSprite.State.BURNING);
		else target.sprite.remove(CharSprite.State.BURNING);
	}
	
	
	@Override
	public String heroMessage() {
		return Messages.get(this, "heromsg");
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns(left));
	}

	@Override
	public void onDeath() {

		Badges.validateDeathFromFire();

		Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
		//GLog.n(TXT_BURNED_TO_DEATH);
	}
}
