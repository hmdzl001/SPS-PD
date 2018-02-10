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
package com.hmdzl.spspd.change.actors.mobs.pets;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.BunnySprite;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.FairySprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Bunny extends PET{
	
	{
		//name = "bunny";
		spriteClass = BunnySprite.class;       
		state = HUNTING;
		level = 1;
		type = 9;
		cooldown=500;

		properties.add(Property.BEAST);
	}
	protected int regen = 20;	
	protected float regenChance = 0.1f;	
		

	@Override
	public void adjustStats(int level) {
		this.level = level;
		HT = 70 + level*10;
		defenseSkill = 5 + level;
	}
	


	@Override
	public int dr(){
		return level*4;
	}
	
	@Override
	public int attackSkill(Char target) {
		return defenseSkill;
	}

	@Override
	public int damageRoll() {
		
		int dmg=0;
		if (cooldown==0){
			dmg=Random.NormalIntRange((5+level)*5, (5+level*3)*4); 
			cooldown=500;
		} else {
			dmg=Random.NormalIntRange((5+level), (5+level*3)) ;
		}
		return dmg;
			
	}

	@Override
	protected boolean act() {
		
		if (cooldown>0){
			cooldown=Math.max(cooldown-(1+9*((level-1)/19)),0);
			if (cooldown==0) {GLog.w(Messages.get(this,"ready"));}
		}
		
		if (Random.Float()<regenChance && HP<HT){HP+=regen;}

		return super.act();
	}			


@Override
public void interact() {

	if (this.buff(MagicalSleep.class) != null) {
		Buff.detach(this, MagicalSleep.class);
	}
	
	if (state == SLEEPING) {
		state = HUNTING;
	}
	if (buff(Paralysis.class) != null) {
		Buff.detach(this, Paralysis.class);
	}
	
	int curPos = pos;

	moveSprite(pos, Dungeon.hero.pos);
	move(Dungeon.hero.pos);

	Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
	Dungeon.hero.move(curPos);

	Dungeon.hero.spend(1 / Dungeon.hero.speed());
	Dungeon.hero.busy();
}



}