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

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.artifacts.Artifact;
import com.hmdzl.spspd.change.items.artifacts.HornOfPlenty;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Hunger extends Buff implements Hero.Doom {

	private static final float STEP = 10f;

	public static final float OVERFED = 100f;
	public static final float HUNGRY = 600f; 
	public static final float STARVING = 800f; 

	private float level;
	private float partialDamage;

	private static final String LEVEL = "level";
	private static final String PARTIALDAMAGE 	= "partialDamage";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEVEL, level);
		bundle.put( PARTIALDAMAGE, partialDamage );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		level = bundle.getFloat(LEVEL);
		partialDamage = bundle.getFloat(PARTIALDAMAGE);
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {

			Hero hero = (Hero) target;

			if (isStarving()) {

			    partialDamage += target.HT/100f;
				
				if (partialDamage > 1){
					target.damage( (int)partialDamage, this);
					partialDamage -= (int)partialDamage;
				}
				
			} else {

				float newLevel = level + STEP;
				boolean statusUpdated = false;
				if (newLevel <= OVERFED && level > OVERFED) {

					GLog.n(Messages.get(this, "onoverfed"));
					statusUpdated = true;

				} else if (newLevel >= OVERFED && level < OVERFED) {

					statusUpdated = true;

				} else if (newLevel >= STARVING) {

					GLog.n(Messages.get(this, "onstarving"));
					//hero.resting = false;
					hero.damage(1, this);
					statusUpdated = true;

					hero.interrupt();

				} else if (newLevel >= HUNGRY && level < HUNGRY) {

					GLog.w(Messages.get(this, "onhungry"));
					statusUpdated = true;

				}
				level = newLevel;

				if (statusUpdated) {
					BuffIndicator.refreshHero();
				}

			}
			spend(target.buff(Shadows.class) == null ? STEP : STEP * 1.5f);

		} else {

			diactivate();

		}

		return true;
	}

	public void satisfy(float energy) {
		Artifact.ArtifactBuff buff = target
				.buff(HornOfPlenty.hornRecharge.class);
		if (buff != null && buff.isCursed()) {
			energy = Math.round(energy * 0.75f);
			GLog.n(Messages.get(this, "cursedhorn"));
		}
		if (level>=800f) {
			energy = Math.round(energy * 1.5f);
		}
		if(Dungeon.isChallenged(Challenges.ENERGY_LOST)){
			energy = Math.round(energy * 0.4f);
		}
		
	    reduceHunger( energy );
		
	}
	
	public void reduceHunger( float energy ) {

		level -= energy;
		if (level < 0) {
			level = 0;
		} else if (level > STARVING) {
			level = STARVING;
		}

		BuffIndicator.refreshHero();
	}	

	public boolean isStarving() {
		return level >= STARVING;
	}
	
	public boolean isOverfed() {
		return level <= OVERFED;
	}
	
	public boolean isHungry() {
		return  (level >= HUNGRY && level < STARVING);
	}
	
	public int hungerLevel() {
		return (int) level;
	}

	@Override
	public int icon() {
        if (level < OVERFED) {
            return BuffIndicator.OVERFED;
        } else if (level < HUNGRY) {
            return BuffIndicator.NONE;
        } else if (level < STARVING) {
			return BuffIndicator.HUNGER;
		} else {
			return BuffIndicator.STARVATION;
		}
	}

	@Override
	public String toString() {
        if (level < OVERFED) {
            return Messages.get(this, "overfed");
        } else if (level < HUNGRY) {
            return Messages.get(this, "normal");
        } else if (level < STARVING) {
            return Messages.get(this, "hungry");
        } else {
			return Messages.get(this, "starving");
		}
	}

	@Override
	public String desc() {
		String result;
		if (level < OVERFED) {
            result =  Messages.get(this, "desc_intro_overfed");
        } else if (level < HUNGRY) {
            result =  Messages.get(this, "desc_intro_normal");
        } else if (level < STARVING) {
			result = Messages.get(this, "desc_intro_hungry");
		} else {
			result = Messages.get(this, "desc_intro_starving");
		}

		result += Messages.get(this, "desc");

		return result;
	}	
	
	@Override
	public void onDeath() {

		Badges.validateDeathFromHunger();

		Dungeon.fail(Messages.format(ResultDescriptions.HUNGER));
		//GLog.n(TXT_DEATH);
	}
}
