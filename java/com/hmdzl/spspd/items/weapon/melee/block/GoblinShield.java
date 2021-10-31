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
package com.hmdzl.spspd.items.weapon.melee.block;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Drowsy;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GoblinShield extends MeleeWeapon {

	{
		//name = "GoblinShield";
		image = ItemSpriteSheet.GOBLIN_SHIELD;
	}

	public GoblinShield() {
		super(3, 1f, 1f, 1);
	}

	@Override
	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 1;
		return super.upgrade(enchant);
	}

	public int charge = 0;
	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}



	@Override
	public void proc(Char attacker, Char defender, int damage) {

		int DMG = damage;

		if (attacker.buff(EnergyArmor.class) == null){
		    Buff.affect(attacker, EnergyArmor.class).level(attacker.HT/8);
		}

		charge ++;

		if (charge > 10) {
			charge = 0;
			switch (Random.Int(16)) {
				case 0:
					break;
				case 1:
					Buff.affect(defender, Burning.class).reignite(defender);
					break;
				case 2:
					Buff.affect(defender, Frost.class, 3f);
					break;
				case 3:
					GameScene.add(Blob.seed(defender.pos, 20, TarGas.class));
					break;
				case 4:
					GameScene.add(Blob.seed(defender.pos, 30, ConfusionGas.class));
					break;
				case 5:
					GameScene.add(Blob.seed(defender.pos, 30, ToxicGas.class));
					break;
				case 6:
					GameScene.add(Blob.seed(defender.pos, 20, ParalyticGas.class));
					break;
				case 7:
					Buff.prolong(defender, Amok.class, 5f);
					break;

				case 8:
					Buff.affect(defender, Drowsy.class);
					break;

				case 9:
					Buff.affect(defender, Terror.class, Terror.DURATION).object = attacker.id();
					break;

				case 10:
					Buff.prolong(defender, Paralysis.class, 3f);
					break;

				case 11:
					Buff.prolong(defender, Blindness.class, 5f);
					break;

				case 12:
					Dungeon.level.plant((Plant.Seed) Generator.random(Generator.Category.SEED), defender.pos);
					break;

				case 13:
					attacker.HP += Math.min(attacker.HT, attacker.HP + damage);
					break;
				case 14:
					Buff.prolong(attacker, Recharging.class, 20f);
					break;
				case 15:
					level++;
					break;
			}

		}
		
		if (enchantment != null) {
		enchantment.proc(this, attacker, defender, damage);
		}

	}


	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(this, "charge",charge);
		return info;
	}


}
