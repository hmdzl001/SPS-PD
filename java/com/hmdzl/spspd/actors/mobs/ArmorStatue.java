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
import com.hmdzl.spspd.Journal;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.StatueSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ArmorStatue extends Mob {

	{
		spriteClass = StatueSprite.class;

		EXP = 50 + Dungeon.dungeondepth * 2;
		state = PASSIVE;
		
		properties.add(Property.ELEMENT);
	}

	private Armor armor;

	public ArmorStatue() {
		super();

		do {
			armor = (Armor) Generator.random(Generator.Category.ARMOR);
		} while (!(armor instanceof NormalArmor) || armor.level < 0);

		armor.identify();
		armor.inscribe(Armor.Glyph.random());

		HP = HT = 15 + Dungeon.dungeondepth * 5;
	}

	private static final String ARMOR = "armor";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ARMOR, armor);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		armor = (Armor) bundle.get(ARMOR);
	}

	@Override
	protected boolean act() {
		if (Dungeon.visible[pos]) {
			Journal.add(Journal.Feature.STATUE);
		}
		return super.act();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(3/2*Dungeon.dungeondepth, 5/2* Dungeon.dungeondepth);
	}

	@Override
	public int hitSkill(Char target) {
		return  9 + Dungeon.dungeondepth * 3;
	}

	@Override
	public int evadeSkill(Char target) {
		return (int) ((4 + Dungeon.dungeondepth) * armor.DEX);
	}


	@Override
	protected float attackDelay() {
		return 1;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(armor.MIN, armor.MAX);
	}

	public int magicdrRoll() {
		return Random.NormalIntRange(armor.M_MIN, armor.M_MAX);
	}

	//@Override
	//protected boolean canAttack(Char enemy) {
	//	return Level.distance( pos, enemy.pos ) <= weapon.RCH;
	//}
	
	@Override
	public void damage(int dmg, Object src,int type) {

		if (state == PASSIVE) {
			state = HUNTING;
		}

		super.damage(dmg, src,type);
	}


	@Override
	public int defenseProc(Char enemy, int damage) {
		armor.proc(this, enemy, damage);
		return damage;
	}

	@Override
	public void beckon(int cell) {
		// Do nothing
	}

	@Override
	public void die(Object cause) {
		Dungeon.depth.drop(armor, pos).sprite.drop();
		super.die(cause);
	}

	@Override
	public void destroy() {
		Journal.remove(Journal.Feature.STATUE);
		super.destroy();
	}

	@Override
	public boolean reset() {
		state = PASSIVE;
		return true;
	}

	@Override
	public String description() {
		String info = Messages.get(this, "desc");
		info += "\n" + Messages.get(this, "armor", armor.name());
		return info;
	}
	
	@Override
	public void add(Buff buff) {
		 if (buff instanceof Locked || buff instanceof Silent) {
				damage(Random.NormalIntRange(1, HT * 2 / 3), buff,3);
		} else {
			super.add(buff);
		}
	}	

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		//resistances.add(EnchantmentDark.class);
		
	}
}
