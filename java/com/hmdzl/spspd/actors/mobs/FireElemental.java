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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire2;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.FireElementalSprite;
import com.watabou.utils.Random;

public class FireElemental extends Mob {

	{
		spriteClass = FireElementalSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 20+adj(0);

		EXP = 15;
		maxLvl = 30;

		flying = true;

		loot = new PotionOfLiquidFlame();
		lootChance = 0.1f;
		
		lootOther = new WandOfFirebolt();
		lootChanceOther = 0.02f; // by default, see die()
		
		properties.add(Property.ELEMENT);
		properties.add(Property.MAGICER);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(16, 20+adj(1));
	}

	@Override
	public int hitSkill(Char target) {
		return 25+adj(1);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, Burning.class).reignite(enemy);
		}
		
		enemy.damage(damage, DamageType.FIRE_DAMAGE);
		damage = 0;

		return damage;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return Level.distance( pos, enemy.pos ) <= 2 ;
	}		

	@Override
	public void add(Buff buff) {
		if (buff instanceof Burning) {
			if (HP < HT) {
				HP++;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}
		} else if (buff instanceof Frost || buff instanceof Chill) {
			if (Level.water[this.pos])
				damage(Random.NormalIntRange(HT / 2, HT), buff);
			else
				damage(Random.NormalIntRange(1, HT * 2 / 3), buff);
		} else {
			super.add(buff);
		}
	}

	{
		immunities.add(Burning.class);
		immunities.add(Fire.class);
		immunities.add(WandOfFirebolt.class);
		immunities.add(EnchantmentFire2.class);
		immunities.add(EnchantmentFire.class);
		immunities.add(DamageType.FireDamage.class);
	}

}
