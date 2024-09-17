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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.WarlockSprite;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;

public class Warlock extends Mob implements Callback {

	private static final float TIME_TO_ZAP = 1f;

	private static final String TXT_SHADOWBOLT_KILLED = "%s's shadow bolt killed you...";

	{
		spriteClass = WarlockSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(5, 7));
		evadeSkill = 18+adj(0);

		EXP = 11;
		maxLvl = 30;

		loot = Generator.Category.POTION;
		lootChance = 0.83f;
		
		lootOther = Generator.Category.WAND;
		lootChanceOther = 0.02f; // by default, see die()
		
		properties.add(Property.DWARF);
		properties.add(Property.MAGICER);
	}

	@Override
	public Item SupercreateLoot(){
		return new Egg();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12, 24+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 25+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(4, 8);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		
		enemy.damage(damage/2, DARK_DAMAGE,2);
		damage = damage/2;

		return damage;
	}		
	
	@Override
	protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Floor.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Floor.fieldOfView[pos]
					|| Floor.fieldOfView[enemy.pos];
			if (visible) {
				sprite.zap(enemy.pos);
			} else {
				zap();
			}

			return !visible;
		}
	}

	private void zap() {
		spend(TIME_TO_ZAP);

		if (hit(this, enemy, true)) {
			if (enemy == Dungeon.hero && Random.Int(2) == 0) {
				Buff.prolong(enemy, STRdown.class,10f);
			}

			int dmg = Random.Int(16, 24+adj(0));
			enemy.damage(dmg, DARK_DAMAGE,2);

			//if (!enemy.isAlive() && enemy == Dungeon.hero) {
			//	Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n(Messages.get(this, "kill"));
			//}
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public void call() {
		next();
	}

	@Override
	public Item createLoot() {
		Item loot = super.createLoot();
		return loot;
	}

	{
		resistances.add(EnchantmentDark.class);
	}
}
