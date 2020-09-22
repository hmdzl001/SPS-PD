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
package com.hmdzl.spspd.items.wands;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class CannonOfMage extends DamageWand {

	{
		image = ItemSpriteSheet.CANNON_OF_MAGE;
		collisionProperties = Ballistica.MAGIC_BOLT;
		reinforced = true;
	}
	
	public int min(int lvl){
		return 1+lvl;
	}

	public int max(int lvl){
		return 5+2*lvl;
	}	

	@Override
	protected void onZap( Ballistica bolt ) {

		Char ch = Actor.findChar(bolt.collisionPos);
		if (ch != null) {



			if (ch.isAlive()){
			switch (Random.Int(7)) {
				case 0:
					ch.damage((int) (damageRoll() * (1 + 0.3 * Dungeon.hero.magicSkill())), this);
					break;
				case 1:
					Buff.affect(ch, Burning.class).reignite(ch);
					break;
				case 2:
					Buff.affect(ch, Shocked.class, 5f);
					break;
				case 3:
					Buff.affect(ch, Ooze.class);
					break;
				case 4:
					Buff.affect(ch, Frost.class, 5f);
					break;
				case 5:
					Buff.affect(ch, AttackDown.class, 10f).level(30);
					Buff.affect(ch, ArmorBreak.class, 10f).level(30);
					break;
				case 6:
					Buff.prolong(ch, Blindness.class, 5f);
					break;
				default:
					GLog.i("nothing happened");
					break;
			}
			processSoulMark(ch, chargesPerCast());
			ch.damage((int) (damageRoll() * (1 + 0.6 * Dungeon.hero.magicSkill())), this);
			}
		}
	}

	@Override
	protected int initialCharges() {
		return 7;
	}
	
	@Override
	public Item upgrade() {

		super.upgrade();
		maxCharges = 7;
		updateQuickslot();
		return this;
	}	
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.rainbow(curUser.sprite.parent, curUser.pos, bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}
