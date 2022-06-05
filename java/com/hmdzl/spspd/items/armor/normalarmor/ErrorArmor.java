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
package com.hmdzl.spspd.items.armor.normalarmor;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ErrorArmor extends NormalArmor {

	{
		//name = "error armor";
		image = ItemSpriteSheet.ERROR_ARMOR;
		STR = 0;
		MAX = 0;
		MIN = 0;
	}

	public ErrorArmor() {
		super(0,1f,1f,10);
	}

	@Override
	public Item upgrade(boolean hasglyph) {
		MIN --;
        MAX-=3;

		return super.upgrade(hasglyph);
	}


	@Override
	public void proc(Char attacker, Char defender, int damage) {

		switch (Random.Int (10)) {
			case 0 :
				if (attacker.properties().contains(Char.Property.BOSS) || attacker.properties().contains(Char.Property.MINIBOSS)){
					attacker.damage(Random.Int(attacker.HT/8, attacker.HT/4), this);}
				else attacker.damage(Random.Int(attacker.HT, attacker.HT * 2), this);
				attacker.sprite.emitter().burst(ShadowParticle.UP, 5);
				if (!defender.isAlive() && attacker instanceof Hero) {
					Badges.validateGrimWeapon();
				}
				break;
			case 1 :
				Buff.affect(attacker, Bleeding.class).set(5);
				break;
			case 2 :
				Buff.affect(attacker, Ooze.class);
				break;
			case 3 :
				Buff.affect(attacker, Terror.class, Terror.DURATION).object = defender.id();
				break;
			case 4 :
				if (defender.HP < defender.HT){
					defender.HP += (defender.HT)/10;
					defender.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,1);}
				break;
			case 5 :
				Buff.prolong(attacker, AttackDown.class, 5f).level(35);
				break;
			case 6 :
				Buff.prolong(attacker, ArmorBreak.class, 5f).level(35);
				break;
			case 7 :
				Buff.affect(attacker, GrowSeed.class).set(6f);
				break;
			case 8 :
				Buff.affect(attacker, Shocked.class);
				break;
			default:
				break;
		}

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}
    }
}
