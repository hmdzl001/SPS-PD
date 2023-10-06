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
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.items.ConchShell;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.SacrificeBook;
import com.hmdzl.spspd.items.bombs.FishingBomb;
import com.hmdzl.spspd.items.food.completefood.GoldenNut;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.reward.CaveReward;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.AlbinoPiranhaSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class AlbinoPiranha extends Mob {
	
	private static final String TXT_KILLCOUNT = "Albino Piranha Kill Count: %s";

	{
		spriteClass = AlbinoPiranhaSprite.class;

		baseSpeed = 1f;

		EXP = 5;
		
		loot = new Meat();
		lootChance = 0.1f;	
		
		properties.add(Property.FISHER);
	}

	public AlbinoPiranha() {
		super();

		HP = HT = 20 + Statistics.albinoPiranhasKilled*2;
		evadeSkill = 40 + Statistics.albinoPiranhasKilled/5;
	}

	protected boolean checkwater(int cell){
		return Level.water[cell];		
	}
	
		
	@Override
	protected boolean act() {
		
		if (!Level.water[pos]) {
			die(null);
			return true;
					
				
		} else {
			// this causes pirahna to move away when a door is closed on them.
			Dungeon.level.updateFieldOfView(this);
			enemy = chooseEnemy();
			if (state == this.HUNTING
					&& !(enemy.isAlive() && Level.fieldOfView[enemy.pos] && enemy.invisible <= 0)) {
				state = this.WANDERING;
				int oldPos = pos;
				int i = 0;
				do {
					i++;
					target = Dungeon.level.randomDestination();
					if (i == 100)
						return true;
				} while (!getCloser(target));
				moveSprite(oldPos, pos);
				return true;
			}
	
			return super.act();
		}
	}

	@Override
	public Item SupercreateLoot(){
		return new FishingBomb();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Statistics.albinoPiranhasKilled/2, 4 + Statistics.albinoPiranhasKilled);
	}

	@Override
	public int hitSkill(Char target) {
		return 20 + Statistics.albinoPiranhasKilled;
	}

	@Override
	public int drRoll() {
		return Statistics.albinoPiranhasKilled;
	}

	@Override
	public void die(Object cause) {
		
		super.die(cause);	
		
		Statistics.albinoPiranhasKilled++;
		GLog.w(Messages.get(Mob.class,"killcount",Statistics.albinoPiranhasKilled));
			
		
		explodeDew(pos);
		if(Random.Int(105-Math.min(Statistics.albinoPiranhasKilled,100))==0){
		  Dungeon.level.drop(new NutVegetable(), pos).sprite.drop();
		}
		
	if(Statistics.albinoPiranhasKilled == 25) {
			Dungeon.level.drop(new ConchShell(), pos).sprite.drop();
	}
		
	if(Statistics.albinoPiranhasKilled == 50) {
		Dungeon.level.drop(new SacrificeBook(), pos).sprite.drop();
	}	
	
	if(Statistics.albinoPiranhasKilled == 100) {
		Dungeon.level.drop(new CaveReward(), pos).sprite.drop();
	}		
	
	if (Statistics.goldThievesKilled>99 && Statistics.skeletonsKilled>99 
			&& Statistics.albinoPiranhasKilled == 100 && Statistics.archersKilled>99){
	    Dungeon.level.drop(new GoldenNut(), pos).sprite.drop();
	}	
		
		


	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	protected boolean getCloser(int target) {

		if (rooted) {
			return false;
		}

		int step = Dungeon.findPath(this, pos, target, Level.water,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean getFurther(int target) {
		int step = Dungeon.flee(this, pos, target, Level.water,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	{
		immunities.add(Burning.class);
		immunities.add(Paralysis.class);
		immunities.add(ToxicGas.class);
		immunities.add(Roots.class);
		immunities.add(Frost.class);
	}

}
