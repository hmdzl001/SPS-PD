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
package com.hmdzl.spspd.actors.mobs.pets;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ScorpionSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class Scorpion extends PET {
	
	{
		//name = "scorpion";
		spriteClass = ScorpionSprite.class;       
		//flying=false;
		state = HUNTING;
		level = 1;
		type = 8;
		cooldown=500;

		properties.add(Property.BEAST);
	}	

	@Override
	public void adjustStats(int level) {
		this.level = level;
		HT = 70 + level*10;
		evadeSkill = 5 + level;
	}
	



	@Override
	public int damageRoll() {		
		return Random.NormalIntRange((5+level), (5+level*3)) ;		
	}

	@Override
	protected boolean act() {
		
		if (cooldown>0){
			cooldown=Math.max(cooldown-(1+9*((level-1)/19)),0);
			if (cooldown==0) {GLog.w(Messages.get(this,"ready"));}
		}
		
		

		return super.act();
	}			
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (cooldown>0 && Random.Int(10) == 0) {
			Buff.affect(enemy, Ooze.class);			
		}
		if (cooldown==0) {
			Buff.affect(enemy, Ooze.class);	
			
			sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,	1);
			sprite.showStatus(CharSprite.POSITIVE,Integer.toString(damage));
			
			HP = Math.min(HT, HP+damage);
			
			Dungeon.hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,	1);
			Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE,Integer.toString(damage));
			
			Dungeon.hero.HP = Math.min(Dungeon.hero.HT, Dungeon.hero.HP+damage);

			damage+=damage;
			cooldown=500;
		}

		return damage;
	}

}