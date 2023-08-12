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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.StoneIce;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.special.SJRBMusic;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ExVagrantSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class ExVagrant extends Vagrant {

	{
		spriteClass = ExVagrantSprite.class;

		HP = HT = 100;
		evadeSkill = 10+adj(0);

		EXP = 6;
        maxLvl = 20;

		properties.add(Property.HUMAN);
	}

	@Override
	public Item SupercreateLoot(){
		return new SJRBMusic();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(5, 10+adj(0));
	}

	@Override
	protected boolean act() {
		if ( HP < HT && this.buff(BeOld.class) == null) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP+=20;
		}

		return super.act();
	}	
	
	@Override
	public int attackProc(Char enemy, int damage) {

		int reg = Math.min(damage, HT - HP);

		if (reg > 0 && this.buff(BeOld.class) == null ) {
			HP += reg;
			sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
		}

		return damage;
	}	
	
	@Override
	public int hitSkill(Char target) {
		return 15;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(3, 6);
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Level.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				Buff.affect(ch,StoneIce.class).level(5);
			}
		}

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_BLAST);
		}


	}

}
