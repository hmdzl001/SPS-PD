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
package com.hmdzl.spspd.items.medicine;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.SpellSprite;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Pill extends Item {

	private static final float TIME_TO_EAT = 1f;

	public static final String AC_EAT = "EAT";
	public String sname = "PILL" ;

	{
		stackable = true;
		//name = "pill";
		image = ItemSpriteSheet.NULLWARN;
		defaultAction = AC_EAT;
		sname = "P";
		 
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (hero.buff(Locked.class) == null){
		actions.add(AC_EAT);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EAT)) {
			curUser = Dungeon.hero;
			if (hero.buff(Locked.class) != null ) {
				GLog.w(Messages.get(Food.class, "locked"));
			} else {
				if (!(Dungeon.hero.heroClass == HeroClass.FOLLOWER) || Random.Int(10)>=1 ){
				   detach(hero.belongings.backpack);
				}
				/*int healEnergy = Math.max(7, Math.round(energy / 40));
				switch (hero.heroClass) {
					case WARRIOR:
						if (hero.HP < hero.TRUE_HT) {
							hero.HP = Math.min(hero.HP + Random.Int(3, healEnergy), hero.TRUE_HT);
							hero.sprite.emitter()
									.burst(Speck.factory(Speck.HEALING), 1);
						}
						break;
					case MAGE:
						Buff.affect(hero, Recharging.class, 4f);
						ScrollOfRecharging.charge(hero);
						if (hero.HP < hero.TRUE_HT) {
							hero.HP = Math.min((hero.HP + Random.Int(1, 3)), hero.TRUE_HT);
							hero.sprite.emitter()
									.burst(Speck.factory(Speck.HEALING), 1);
						}
						break;
					case ROGUE:
						if (hero.HP < hero.TRUE_HT) {
							hero.HP = Math.min((hero.HP + Random.Int(1, 3)), hero.TRUE_HT);
							hero.sprite.emitter()
									.burst(Speck.factory(Speck.HEALING), 1);
						}
					case HUNTRESS:
						if (hero.HP < hero.TRUE_HT) {
							hero.HP = Math.min((hero.HP + Random.Int(1, 3)), hero.TRUE_HT);
							hero.sprite.emitter()
									.burst(Speck.factory(Speck.HEALING), 1);
						}
						break;
					case PERFORMER:
						if (hero.HP < hero.TRUE_HT) {
							hero.HP = Math.min((hero.HP + Random.Int(1, 3)), hero.TRUE_HT);
							hero.sprite.emitter()
									.burst(Speck.factory(Speck.HEALING), 1);
						}
						break;						
				}
*/
				doEat2();
				hero.sprite.operate(hero.pos);
				hero.busy();
				SpellSprite.show(hero, SpellSprite.FOOD);
				Sample.INSTANCE.play(Assets.SND_EAT);

				hero.spend(TIME_TO_EAT);

				Statistics.foodEaten++;
				Badges.validateFoodEaten();
			}
		} else {

			super.execute(hero, action);

		}
	}

	public void doEat2() {

	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 5 * quantity;
	}
}
