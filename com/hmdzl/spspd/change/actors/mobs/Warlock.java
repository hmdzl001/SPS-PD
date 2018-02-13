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
package com.hmdzl.spspd.change.actors.mobs;

import java.util.HashSet;

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.food.Meat;
import com.hmdzl.spspd.change.items.potions.PotionOfHealing;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.WarlockSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Warlock extends Mob implements Callback {

	private static final float TIME_TO_ZAP = 1f;

	private static final String TXT_SHADOWBOLT_KILLED = "%s's shadow bolt killed you...";

	{
		spriteClass = WarlockSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(5, 7));
		defenseSkill = 18+adj(0);

		EXP = 11;
		maxLvl = 21;

		loot = Generator.Category.POTION;
		lootChance = 0.83f;
		
		lootOther = Generator.Category.WAND;
		lootChanceOther = 0.02f; // by default, see die()
		
		properties.add(Property.DWARF);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12, 25+adj(0));
	}

	@Override
	public int attackSkill(Char target) {
		return 25+adj(0);
	}

	@Override
	public int dr() {
		return 8+adj(1);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((WarlockSprite) sprite).zap(enemy.pos);
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
				Buff.prolong(enemy, Weakness.class, Weakness.duration(enemy));
			}

			int dmg = Random.Int(16, 24+adj(0));
			enemy.damage(dmg, this);

			if (!enemy.isAlive() && enemy == Dungeon.hero) {
				Dungeon.fail(Messages.format(ResultDescriptions.MOB));
				//GLog.n(Messages.get(this, "kill"));
			}
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

		if (loot instanceof PotionOfHealing) {

			// count/10 chance of not dropping potion
			if (Random.Int(10) - Dungeon.limitedDrops.warlockHP.count < 0) {
				return null;
			} else
				Dungeon.limitedDrops.warlockHP.count++;

		}

		return loot;
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(Death.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
