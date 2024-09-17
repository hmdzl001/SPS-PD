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
package com.hmdzl.spspd.actors.mobs.npcs;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.ChallengeBook;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.misc.CursePhone;
import com.hmdzl.spspd.items.weapon.melee.special.Goei;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.RENSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class RENnpc extends NPC {

	{
		//name = "RENnpc";
		spriteClass = RENSprite.class;
		properties.add(Property.ELF);
    }

	private boolean first = true;

	private static final String FIRST = "first";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(FIRST, first);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		first = bundle.getBoolean(FIRST);
	}

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}	
	
	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}


	@Override
	protected Char chooseEnemy() {
		return null;
	}

	@Override
	public void damage(int dmg, Object src, int type) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

   
	
	@Override
	public boolean interact() {
		CursePhone phone = Dungeon.hero.belongings.getItem(CursePhone.class);
		sprite.turnTo(pos, Dungeon.hero.pos);
		if(first) {
			Dungeon.depth.drop(new ChallengeBook(), Dungeon.hero.pos).sprite.drop();
			yell(Messages.get(this, "yell3"));	
			first = false;
		} else if(!Dungeon.LimitedDrops.goei.dropped() && (Statistics.archersKilled > 50 && Statistics.skeletonsKilled > 50 && Statistics.albinoPiranhasKilled > 50 && Statistics.goldThievesKilled > 50)){
			Dungeon.LimitedDrops.goei.dropped();
			Dungeon.depth.drop(new Goei(), Dungeon.hero.pos).sprite.drop();
			yell(Messages.get(this, "yell5"));
		} else
		switch (Random.Int (4)) {
            case 0:
			yell(Messages.get(this, "yell1"));
			break;
			case 1:
			yell(Messages.get(this, "yell2"));
			break;
			case 2:
			yell(Messages.get(this, "yell4"));
			break;
			case 3:
				if (phone!=null){
						yell(Messages.get(this, "yell6"));
					}
				break;
		}
		return true;
	}

	@Override
	public Item SupercreateLoot(){
		return new CursePhone();
	}
}
