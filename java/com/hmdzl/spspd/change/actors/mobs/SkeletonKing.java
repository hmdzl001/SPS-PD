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

import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.items.AdamantWeapon;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.change.items.wands.WandOfFirebolt;

import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.SkeletonKingSprite;
import com.watabou.utils.Random;

public class SkeletonKing extends Mob {

	{
		spriteClass = SkeletonKingSprite.class;

		HP = HT = 2000;
		evadeSkill = 30;

		EXP = 10;
		maxLvl = 20;

		flying = true;

		loot = new PotionOfLiquidFlame();
		lootChance = 0.1f;

		properties.add(Property.UNDEAD);
		properties.add(Property.BOSS);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 40);
	}

	@Override
	public int hitSkill(Char target) {
		return 25;
	}

	@Override
	public int drRoll() {
		return 25;
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Weakness.class) == null) {
			state = HUNTING;
		}
		return result;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			if(enemy == Dungeon.hero){
			 Buff.prolong(enemy, Weakness.class, Weakness.duration(enemy));
			state = FLEEING;
			}
		}

		return damage;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return Dungeon.level.distance( pos, enemy.pos ) <= 3;
	}
	
	@Override
	public void die(Object cause) {
        super.die(cause);

		GameScene.bossSlain();
		Dungeon.level.drop(new Gold(Random.Int(1900, 4000)), pos).sprite.drop();
		Dungeon.level.drop(new AdamantWeapon(), pos).sprite.drop();
		Dungeon.skeletonkingkilled=true;
			
		
		
		yell(Messages.get(this,"die"));
					
	}
	

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,  "notice", Dungeon.hero.givenName()));
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(Fire.class);
		IMMUNITIES.add(WandOfFirebolt.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
