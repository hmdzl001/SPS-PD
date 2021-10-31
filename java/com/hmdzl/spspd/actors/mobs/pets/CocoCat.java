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
import com.hmdzl.spspd.items.bombs.BuildBomb;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.CoconutSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

public class CocoCat extends PET {
	
	{
		//name = "scorpion";
		spriteClass = CoconutSprite.class;       
		//flying=false;
		state = HUNTING;
		level = 1;
		type = 13;
		cooldown=500;

		properties.add(Property.BEAST);

	}	
			
		
	

	@Override
	public void adjustStats(int level) {
		this.level = level;
		HT = 10000;
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
		
		

		if (level > 5){
		yell(Messages.get(this,"thanks"));
		destroy();
		//sprite.killAndErase();
		//CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
		Dungeon.hero.haspet=false;
		}
		
		return super.act();

	}			
	
	@Override
	protected boolean canAttack(Char enemy) {

		return Level.distance( pos, enemy.pos ) <= 4 ;
	
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (cooldown==0) {
		BuildBomb bomb = new BuildBomb();
		bomb.explode(enemy.pos);
		cooldown=500;
		}

		return damage;
	}

	/*private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(EnchantmentDark.class);
		IMMUNITIES.add(EnchantmentDark.class);
		IMMUNITIES.add(Ooze.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Vertigo.class);
		IMMUNITIES.add(Paralysis.class);
	    IMMUNITIES.add(Bleeding.class);
		IMMUNITIES.add(CorruptGas.class);
		
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}*/


}