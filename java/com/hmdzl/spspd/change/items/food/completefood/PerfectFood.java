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
package com.hmdzl.spspd.change.items.food.completefood;

import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.buffs.Bless;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Levitation;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class PerfectFood extends CompleteFood {
	
	{
		//name = "perfect food";
		image = ItemSpriteSheet.ERROR_FOOD;
		energy = 600;
		hornValue = 10;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			hero.HT = hero.HT + (Random.Int(4, 8));
			Buff.affect(hero, BerryRegeneration.class).level(5);
			Buff.affect(hero, Bless.class,5f);
			Buff.affect(hero, Light.class,50f);
			hero.HP = Math.min(hero.HP + hero.HT/10, hero.HT);
			Buff.affect(hero, Haste.class,5f);
			Buff.affect(hero, Levitation.class,5f);
			Buff.affect( hero, Recharging.class, 2f ); //half of a charge
			ScrollOfRecharging.charge( hero );
            hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 4);
		}
	}

	@Override
	public int price() {
		return 50 * quantity;
	}
	
}
