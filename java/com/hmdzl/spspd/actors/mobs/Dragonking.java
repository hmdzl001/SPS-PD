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

import java.util.HashSet;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.DragonkingSprite;
import com.watabou.utils.Random;
import com.hmdzl.spspd.actors.blobs.ShockWeb;


public class Dragonking extends Mob {
	{
		spriteClass = DragonkingSprite.class;
		baseSpeed = 1f;

		HP = HT = 100;
		EXP = 1;
		evadeSkill = 0;

		properties.add(Property.DRAGON);
		properties.add(Property.BOSS);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(0, 1);
	}

	@Override
	public int hitSkill(Char target) {
		return 1;
	}

	@Override
	public int drRoll() {
		return 0;
	}

	@Override
	public void die(Object cause) {

		super.die(cause);
		GameScene.bossSlain();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		UGoo.spawnAt(pos);

	}

	@Override
	public void move(int step) {
		GameScene.add(Blob.seed(pos, Random.Int(5, 7), ShockWeb.class));
		super.move(step);
	}		
	
	@Override
	protected boolean doAttack(Char enemy) {

		return super.doAttack(enemy);

	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(EnchantmentDark.class);
		
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
