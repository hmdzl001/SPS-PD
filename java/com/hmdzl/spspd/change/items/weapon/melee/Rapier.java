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
package com.hmdzl.spspd.change.items.weapon.melee;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.KindOfWeapon;
import com.hmdzl.spspd.change.messages.Messages;

public class Rapier extends MeleeWeapon {

	{
		//name = "Rapier";
		image = ItemSpriteSheet.RAPIER;
	}

	public Rapier() {
		super(3, 1f, 1f, 2);
	}
	
    @Override
	public Item upgrade(boolean enchant) {
        MIN+=2;
        MAX+=2;
		return super.upgrade(enchant);
    }	
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {

			Ballistica route = new Ballistica(attacker.pos, defender.pos, Ballistica.PROJECTILE);
			int cell = route.collisionPos;
			int dist = Level.distance(attacker.pos, cell);
			if (dist == 2) {
				cell = route.path.get(route.dist - 1);
				Actor.addDelayed(new Pushing(attacker, attacker.pos, cell), -1);
				attacker.pos = cell;
				if (attacker instanceof Mob) {
					Dungeon.level.mobPress((Mob) attacker);
				} else {
					Dungeon.level.press(cell, attacker);
				}
				defender.damage(damage,this);
			}

			int DMG = damage;
			if (Random.Int(100) < 75) {
				defender.damage(Random.Int(DMG / 4, DMG / 2), this);
			}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
		if (durable() && attacker == Dungeon.hero){
			durable --;
            if (durable == 10){
                GLog.n(Messages.get(KindOfWeapon.class,"almost_destory"));
            }
			if (durable == 0){
				Dungeon.hero.belongings.weapon = null;
				GLog.n(Messages.get(KindOfWeapon.class,"destory"));
			}
		}		
	}
}
