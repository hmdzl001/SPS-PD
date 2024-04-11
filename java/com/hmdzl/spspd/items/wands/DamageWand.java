/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
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

package com.hmdzl.spspd.items.wands;

import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.rings.RingOfKnowledge;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

//for wands that directly damage a target
//wands with AOE effects count here (e.g. fireblast), but wands with indrect damage do not (e.g. venom, transfusion)
public abstract class DamageWand extends Wand{

	//public int magicLevel = 0;
	/*{
		for (Buff buff : curUser.buffs(RingOfMagic.Magic.class))
			magicLevel += Math.min(30,((RingOfMagic.Magic) buff).level);
		if (curUser.subClass == HeroSubClass.BATTLEMAGE)
			magicLevel += 5;
		if (curUser.heroClass == HeroClass.MAGE)
			magicLevel += 3;
		if (curUser.buff(Recharging.class)!= null)
			magicLevel += 10;
	}*/


	public int min(){
		return min(level());
	}

	public abstract int min(int lvl);

	public int max(){
		return max(level());
	}

	public abstract int max(int lvl);

	public int damageRoll(){
		Arcane arcane = curUser.buff(Arcane.class);
		int damage = Random.NormalIntRange(min(), max());
		float bonus = 0;
		for (Buff buff : curUser.buffs(RingOfKnowledge.RingKnowledge.class)) {
			bonus += Math.min(((RingOfKnowledge.RingKnowledge) buff).level,30);
		}
		if (arcane != null) {
			damage = damage * 2;
		}
		if (Random.Int(20) < 5  &&  bonus > 0 ) {
			damage = (int)(damage * ( 1.2 + 0.06 * bonus));
			hero.sprite.emitter().burst(Speck.factory(Speck.STAR),8);
		}

		 return damage;
	}

	public int damageRoll(int lvl){
		return Random.NormalIntRange(min(lvl), max(lvl));
	}

	@Override
	public String statsDesc() {
		if (levelKnown)
			return Messages.get(this, "stats_desc", min(), max());
		else
			return Messages.get(this, "stats_desc", min(0), max(0));
	}
}
