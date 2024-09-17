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
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.FloatingText2;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.artifacts.HornOfPlenty;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;

public class Hunger extends Buff implements Hero.Doom {

	private static final float STEP = 10f;

	public static final float OVERFED = 150f;
	public static final float HUNGRY =  650f;
	public static final float STARVING = 800f;
	public static final float MAX_HUNGER = 1000f;

	private float hungerlevel;
	private float partialDamage;

	private static final String HUNGERLEVEL = "hungerlevel";
	private static final String PARTIALDAMAGE 	= "partialDamage";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(HUNGERLEVEL, hungerlevel);
		bundle.put( PARTIALDAMAGE, partialDamage );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		hungerlevel = bundle.getFloat(HUNGERLEVEL);
		partialDamage = bundle.getFloat(PARTIALDAMAGE);
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {

			Hero hero = (Hero) target;

			if (!isOverStarving()) {
				
				float newLevel = hungerlevel + STEP;
				boolean statusUpdated = false;
				if (newLevel <= OVERFED && hungerlevel > OVERFED) {

					GLog.n(Messages.get(this, "onoverfed"));
					statusUpdated = true;

				} else if (newLevel >= OVERFED && hungerlevel < OVERFED) {

					statusUpdated = true;

				} else if (newLevel >= STARVING && hungerlevel < STARVING) {

					GLog.n(Messages.get(this, "onstarving"));
					//hero.resting = false;
					hero.damage(1, this,3);
					statusUpdated = true;

					hero.interrupt();

				} else if (newLevel >= HUNGRY && hungerlevel < HUNGRY) {

					GLog.w(Messages.get(this, "onhungry"));
					statusUpdated = true;

				}
				hungerlevel = newLevel;

				if (statusUpdated) {
					BuffIndicator.refreshHero();
				}

			}
			
             if (isStarving()) {

			    partialDamage += target.HT/100f;
				
				if (partialDamage > 1){
					target.damage( (int)partialDamage, this,3);
					partialDamage -= (int)partialDamage;
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
		if (buff != null && buff.isCursed() && energy > 0) {
			energy = Math.round(energy * 0.75f);
			GLog.n(Messages.get(this, "cursedhorn"));
		}
		if (hungerlevel <150f && energy > 0) {
			energy = Math.round(energy * 0.5f);
		}

		if (hungerlevel >150f && hungerlevel <650f && energy > 0) {
			energy = Math.round(energy * 0.8f);
		}

		if (hungerlevel >=800f && energy > 0) {
			energy = Math.round(energy * 1.2f);
		}
		if(Dungeon.isChallenged(Challenges.ENERGY_LOST) && energy > 0){
			energy = Math.round(energy * 0.4f);
		}

		Dungeon.hero.sprite.showStatusWithIcon(CharSprite.EAT_SOME, "+" + energy, FloatingText2.HUNGER);
	    reduceHunger( energy );
		
	}
	
	public void reduceHunger( float energy ) {

		hungerlevel -= energy;
		if (hungerlevel < 1) {
			hungerlevel = 1;
		} else if (hungerlevel > MAX_HUNGER) {
			hungerlevel = MAX_HUNGER;
		}

		BuffIndicator.refreshHero();
	}	

	public boolean isOverStarving() {
		return hungerlevel >= MAX_HUNGER;
	}	
	
	public boolean isStarving() {
		return hungerlevel >= STARVING;
	}
	
	public boolean isOverfed() {
		return hungerlevel <= OVERFED;
	}
	
	public boolean isHungry() {
		return  (hungerlevel >= HUNGRY && hungerlevel < STARVING);
	}
	
	public int hungerLevel() {
		return (int) hungerlevel;
	}

	@Override
	public int icon() {
        if (hungerlevel < OVERFED) {
            return BuffIndicator.OVERFEED;
        } else if (hungerlevel < HUNGRY) {
            return BuffIndicator.NONE;
        } else if (hungerlevel < STARVING) {
			return BuffIndicator.HUNGER;
		} else {
			return BuffIndicator.STARVATION;
		}
	}

	@Override
	public String toString() {
        if (hungerlevel < OVERFED) {
            return Messages.get(this, "overfed");
        } else if (hungerlevel < HUNGRY) {
            return Messages.get(this, "normal");
        } else if (hungerlevel < STARVING) {
            return Messages.get(this, "hungry");
        } else {
			return Messages.get(this, "starving");
		}
	}

	@Override
	public String desc() {
		String result;
		if (hungerlevel < OVERFED) {
            result =  Messages.get(this, "desc_intro_overfed");
        } else if (hungerlevel < HUNGRY) {
            result =  Messages.get(this, "desc_intro_normal");
        } else if (hungerlevel < STARVING) {
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

		//Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
		//GLog.n(TXT_DEATH);
	}
}
