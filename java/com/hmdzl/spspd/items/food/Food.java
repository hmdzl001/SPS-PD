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
package com.hmdzl.spspd.items.food;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.WarGroove;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.SpellSprite;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Food extends Item {

	//private static final float TIME_TO_EAT = 3f;

	public static final String AC_EAT = "EAT";

	public float energy = Hunger.HUNGRY;

	public int hornValue = 3;

	public float eattime = 3;

	{
		stackable = true;
		//name = "ration of food";
		image = ItemSpriteSheet.RATION;
		defaultAction = AC_EAT;
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
			if (hero.buff(Locked.class) != null ) {
				GLog.w(Messages.get(Food.class, "locked"));
				return;
			}
			curUser = Dungeon.hero;

				if (!(Dungeon.hero.heroClass == HeroClass.FOLLOWER) || Random.Int(10)>=1 ){
				   detach(hero.belongings.backpack);
				}
				hero.buff(Hunger.class).satisfy(energy);
				int healEnergy = Math.max(7, Math.round(energy / 40));
				switch (hero.heroClass) {
					case WARRIOR:
						if (hero.HP < hero.HT) {
							hero.HP = Random.Int(hero.HP + Random.Int(3, healEnergy), hero.HT);
							hero.sprite.emitter()
									.burst(Speck.factory(Speck.HEALING), 1);
						}
						break;
					case MAGE:
						Buff.affect(hero, Recharging.class, 4f);
						ScrollOfRecharging.charge(hero);
						break;
					case ROGUE:
						Buff.affect(hero, AttackUp.class, 10f).level(30);
						Buff.affect(hero, Light.class, 5f);
						break;
					case HUNTRESS:
						Dungeon.depth.drop(new YellowDewdrop(), hero.pos).sprite.drop();
						break;
					case PERFORMER:
						Buff.affect(hero, WarGroove.class);
						break;
					case SOLDIER:
						Buff.affect(hero, HasteBuff.class, 5f);
						break;
					case FOLLOWER:
                        Dungeon.gold+=10;
						break;
				    case ASCETIC:
                       Buff.affect(hero, MagicArmor.class).level(10);
						break;
				}
			    hero.spend(eattime);
				hero.sprite.operate(hero.pos);
				hero.busy();
				SpellSprite.show(hero, SpellSprite.FOOD);
				Sample.INSTANCE.play(Assets.SND_EAT);

			    doEat();
				Statistics.foodEaten++;
				Badges.validateFoodEaten();


		} else {

			super.execute(hero, action);

		}
	}

	public void doEat() {

	}

	@Override
	public String info() {

		String info = desc();

		info += "\n\n" + Messages.get( Food.class, "energy", energy);

		return info;
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
