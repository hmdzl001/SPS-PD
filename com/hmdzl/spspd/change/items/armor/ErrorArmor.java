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
package com.hmdzl.spspd.change.items.armor;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Random;

public class ErrorArmor extends Armor {

	{
		//name = "error armor";
		image = ItemSpriteSheet.NULLWARN;
		bones = false; 
		STR = 0;
		DR = 0;
	}

	public ErrorArmor() {
		super(0);
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {

		switch (Random.Int (10)) {
			case 0 :
				if (attacker.properties().contains(Char.Property.BOSS) || attacker.properties().contains(Char.Property.MINIBOSS)){
					attacker.damage(Random.Int(attacker.HT/8, attacker.HT/4), this);}
				else attacker.damage(Random.Int(attacker.HT, attacker.HT * 2), this);
				attacker.sprite.emitter().burst(ShadowParticle.UP, 5);
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
					defender.HP += (int)((defender.HT)/10);
					defender.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,1);}
				break;
			case 5 :
				break;
			case 6 :
				break;
			case 7 :
				break;
			case 8 :
				break;
			default:
				break;
		}

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}		;
		return damage;
	};
}
