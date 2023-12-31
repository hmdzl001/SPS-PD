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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.ThiefImp;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.FireMeat;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.FIRE_DAMAGE;

public class Burning extends Buff implements Hero.Doom {

	private static final String TXT_BURNS_UP = "%s burns up!";
	private static final String TXT_BURNED_TO_DEATH = "You burned to death...";

	private static final float DURATION = 8f;

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

			target.damage(Random.Int(1, Math.min(1000,target.HT/20)), FIRE_DAMAGE);
			Buff.detach( target, Chill.class);

			if (target instanceof Hero) {

				Hero hero = (Hero) target;
				Item item = hero.belongings.randomUnequipped();
				if (item instanceof Scroll
                    && !(item instanceof ScrollOfUpgrade || item instanceof ScrollOfMagicalInfusion)){

					item = item.detach(hero.belongings.backpack);
					GLog.w(Messages.get(this, "burnsup", item.toString()));

					Heap.burnFX(hero.pos);

				} else if (item instanceof MysteryMeat) {

					item = item.detach(hero.belongings.backpack);
					FireMeat steak = new FireMeat();
					if (!steak.collect(hero.belongings.backpack)) {
						Dungeon.depth.drop(steak, hero.pos).sprite.drop();
					}
					GLog.w(Messages.get(this, "burnsup", item.toString()));

					Heap.burnFX(hero.pos);

				}

			} else if (target instanceof ThiefImp) {

				Item item = ((ThiefImp) target).item;

				if (item instanceof Scroll &&
						!(item instanceof ScrollOfUpgrade || item instanceof ScrollOfMagicalInfusion)) {
					target.sprite.emitter().burst( ElmoParticle.FACTORY, 6 );
					((ThiefImp)target).item = null;
				}

			}

		} else {
			detach();
		}

		if (Floor.flamable[target.pos]) {
			GameScene.add(Blob.seed(target.pos, 4, Fire.class));
		}

		spend(TICK);
		left -= TICK;

		if (left <= 0 || (Floor.water[target.pos] && !target.flying)) {

			detach();
		}

		return true;
	}

	@Override
	public int icon() {
		return BuffIndicator.FIRE;
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

	public void set(float duration) {
		this.left = duration;
	}

	public float level() { return left; }

	public void level(int value) {
		if (left < value) {
			left = value;
		}
	}

	public static float duration(Char ch) {
		Tar tar = ch.buff(Tar.class);
		if (ch.isAlive() && tar!=null){
		return DURATION;
		} else return DURATION;
		
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
