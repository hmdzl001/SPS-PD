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
package com.hmdzl.spspd.items.weapon.missiles.arrows;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.damageblobs.IceEffectDamage;
import com.hmdzl.spspd.actors.blobs.effectblobs.FrostCloud;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class IceFruit extends Arrows {

	{
		//name = "incendiary dart";
		image = ItemSpriteSheet.SEED_ICECAP;

	}


	public IceFruit() {
		this(1);
	}

	public IceFruit(int number) {
		super(10,10);
		quantity = number;
	}

	//private static RangeWeapon bow;

	//private void updateRangeWeapon(){
	//	if (Dungeon.hero.belongings.weapon instanceof RangeWeapon){
	//		bow = (RangeWeapon) Dungeon.hero.belongings.weapon;
	//	} else {
	//		bow = null;
	//	}
	//}

	@Override
	protected void onThrow(int cell) {
		Char enemy = Actor.findChar(cell);
		if (enemy == null || enemy == curUser) {
			GameScene.add(Blob.seed(cell, 4, FrostCloud.class));
			for (int n : Floor.NEIGHBOURS8) {
				int c = cell + n;
				if (c >= 0 && c < Floor.getLength()) {
					GameScene.add(Blob.seed(c, 4, IceEffectDamage.class));
				}
			}
		} else
			super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		Buff.affect(defender, FrostIce.class).set(5f);
		if (enchant != null)
			enchant.proc(this, attacker, defender, damage);
		super.proc(attacker, defender, damage);
	}

}
