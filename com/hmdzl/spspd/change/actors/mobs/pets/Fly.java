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

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.FlySprite;
import com.hmdzl.spspd.change.sprites.SteelBeeSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Fly extends PET {
	
	{
		//name = "Fly";
		spriteClass = FlySprite.class;
        //flying=true;
		state = HUNTING;
		level = 1;
		type = 17;
		
		properties.add(Property.BEAST);

	}
	
	protected int regen = 1;
	protected float regenChance = 0.1f;
	

	@Override
	public int dr(){
		return level*3;
	}		
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		adjustStats(level);
	}

	@Override
	public void adjustStats(int level) {
		this.level = level;
		defenseSkill = 5 + level;
		HT = 90 + level*5;
	}
	

	@Override
	public int attackSkill(Char target) {
		return defenseSkill;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10, (10+level*2));
	}

	@Override
	protected boolean act() {		
		if (Random.Float()<regenChance && HP<HT){HP+=regen;}

		return super.act();
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			Buff.affect(enemy, Slow.class, 5f);
		}

		return damage;
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