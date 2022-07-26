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

import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce2;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.FishProtectorSprite;
import com.watabou.utils.Random;

public class FishProtector extends Mob {

	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		spriteClass = FishProtectorSprite.class;

		EXP = 1;
		state = HUNTING;
		//flying = true;
		
		HP = HT = 300;
		evadeSkill = 25;
		
		loot = new VioletDewdrop();
		lootChance = 1f;

		properties.add(Property.ELEMENT);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(8+Math.round(Statistics.albinoPiranhasKilled/10), 10+Math.round(Statistics.albinoPiranhasKilled/5));
	}

	@Override
	public int hitSkill(Char target) {
		return 25;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 15);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		enemy.damage(damageRoll(), DamageType.ICE_DAMAGE);
		damage = 0;
		return damage;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Level.set(pos, Terrain.WATER);
		GameScene.updateMap(pos);
	}


	@Override
	public void add(Buff buff) {
		if (buff instanceof FrostIce) {
			if (HP < HT) {
				HP+=HT/10;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}
		} else if (buff instanceof Tar) {
			if (Level.water[this.pos])
				damage(Random.NormalIntRange(1, HT * 2 / 3), buff);
			else
				damage(Random.NormalIntRange(HT / 2, HT), buff);
		} else {
			super.add(buff);
		}
	}

	{
		immunities.add(DamageType.IceDamage.class);
		resistances.add(WandOfFreeze.class);
		resistances.add(WandOfFlow.class);
		resistances.add(EnchantmentIce2.class);
		resistances.add(EnchantmentIce.class);
	}

}
