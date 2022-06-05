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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Dewdrop;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.weapon.melee.special.SJRBMusic;
import com.hmdzl.spspd.sprites.VagrantSprite;
import com.watabou.utils.Random;

public class Vagrant extends Mob {

	{
		spriteClass = VagrantSprite.class;

		HP = HT = 80;
		evadeSkill = 8+adj(0);

		EXP = 5;
        maxLvl = 20;
		
		loot =  new NutVegetable();
		lootChance = 0.1f;

		state = PASSIVE;

		lootOther = Generator.Category.BERRY;
		lootChanceOther = 0.05f; // by default, see die()

		properties.add(Property.HUMAN);
	}

	@Override
	public Item SupercreateLoot(){
		return new SJRBMusic();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1, 7+adj(0));
	}

	@Override
	protected boolean act() {
		if ( HP < HT && this.buff(BeOld.class) == null) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP+=10;
		}

		return super.act();
	}	
	
	@Override
	public void die(Object cause) {
		super.die(cause);
	}


	@Override
	public int hitSkill(Char target) {
		return 12;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 3);
	}

	@Override
	public void damage(int dmg, Object src) {

		if (state == PASSIVE) {
			state = HUNTING;
		}

		super.damage(dmg, src);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if (state == FLEEING) {
			Dungeon.level.drop(new Dewdrop(), pos).sprite.drop();
		}
		return damage;
	}
	
}
