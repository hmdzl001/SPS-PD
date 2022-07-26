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
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Taunt;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.VaultProtectorSprite;
import com.watabou.utils.Random;

public class VaultProtector extends Mob {

	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		spriteClass = VaultProtectorSprite.class;

		EXP = 1;
		state = HUNTING;
		//flying = true;
		
		HP = HT = 400;
		evadeSkill = 10;
		
		loot = new VioletDewdrop();
		lootChance = 1f;
		
		properties.add(Property.HUMAN);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(8+Math.round(Statistics.goldThievesKilled/10), 10+Math.round(Statistics.goldThievesKilled/5));
	}

	@Override
	public int hitSkill(Char target) {
		return 40;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 20);
	}

	@Override
	protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null || buff(Taunt.class) == null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (buff(Taunt.class) == null){
			Buff.affect(this,Taunt.class);
		} else {
		enemy.damage(damageRoll(), DamageType.ENERGY_DAMAGE);
		Dungeon.gold-=Math.max(1,(int)Dungeon.gold/100);
		damage = 0;
		}
		return damage;
	}


}
