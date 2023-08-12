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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.quest.DarkGold;
import com.hmdzl.spspd.items.sellitem.VIPcard;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.GoldCollectorSprite;
import com.watabou.utils.Random;

public class GoldCollector extends Mob {

	{
		spriteClass = GoldCollectorSprite.class;

		HP = HT = 75+Math.min(425,(int)(Dungeon.gold/10));
		evadeSkill = 5;
		baseSpeed = 2f;
		//flying = true;

		//state = WANDERING;
		
		properties.add(Property.GOBLIN);
	}

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new Gold(100) ,new VIPcard(),new MasterThievesArmband());
	}

	@Override
	public int damageRoll() {
		return Random.Int(5,20);
	}

	@Override
	public int hitSkill(Char target) {
		return (int)(Dungeon.gold/100);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		int golddrop = (int)(Dungeon.gold/10);
		if (!skilluse && enemy == Dungeon.hero) {
			skilluse = true;
			Buff.affect(this,ShieldArmor.class).level((int)(Dungeon.gold/20));
			Dungeon.gold -=golddrop;
			enemy.sprite.showStatus(CharSprite.NEUTRAL,"-" + golddrop);
		}
		return damage;
	}

	@Override
	public int drRoll() {
		return 0;
	}

	@Override
	public void die(Object cause) {

       int n = Random.Int(5);

       for( int i = 0; i < n; i++ ) {
		   Dungeon.level.drop(new DarkGold(), pos).sprite.drop();
	   }
		super.die(cause);

	}

}
