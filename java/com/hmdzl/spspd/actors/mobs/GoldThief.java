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
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.AncientCoin;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.SacrificeBook;
import com.hmdzl.spspd.items.challengelists.CityChallenge;
import com.hmdzl.spspd.items.food.completefood.GoldenNut;
import com.hmdzl.spspd.items.reward.CityReward;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.GoldThiefSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GoldThief extends Mob {

	protected static final String TXT_STOLE = "%s stole %s gold from you!";
	private static final String TXT_KILLCOUNT = "Gold Thief Kill Count: %s";

	public Item item;

	{
		spriteClass = GoldThiefSprite.class;

		HP = HT = 100+Statistics.goldThievesKilled;
		evadeSkill = 26;

		EXP = 1;
		
		FLEEING = new Fleeing();
		
		properties.add(Property.ELF);
	}

	private int goldtodrop = 0;
	
	private static final String GOLDTODROP = "goldtodrop";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(GOLDTODROP, goldtodrop);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		goldtodrop = bundle.getInt(GOLDTODROP);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 30+Statistics.goldThievesKilled/2);
	}

	@Override
	protected float attackDelay() {
		return 0.75f;
	}

	@Override
	public void die(Object cause) {
		
		super.die(cause);
		Statistics.goldThievesKilled++;
		GLog.w(Messages.get(Mob.class,"killcount",Statistics.goldThievesKilled));
		

		if (item != null) {
			Dungeon.depth.drop(item, pos).sprite.drop();
		}		
		
		if ( Dungeon.dungeondepth <27) {
			Dungeon.depth.drop(new CityChallenge(), pos).sprite.drop();
			explodeDew(pos);				
		} else {
			explodeDew(pos);
		}
		
	if(Statistics.goldThievesKilled == 25) {
		Dungeon.depth.drop(new AncientCoin(), pos).sprite.drop();
	}

	if(Statistics.goldThievesKilled == 50) {
		Dungeon.depth.drop(new SacrificeBook(), pos).sprite.drop();
	}	
	
	if(Statistics.goldThievesKilled == 100) {
		Dungeon.depth.drop(new CityReward(), pos).sprite.drop();
	}		
	
	if (Statistics.goldThievesKilled == 100 && Statistics.skeletonsKilled>99 
			&& Statistics.albinoPiranhasKilled>99 && Statistics.archersKilled>99){
	    Dungeon.depth.drop(new GoldenNut(), pos).sprite.drop();
	}

	}

	@Override
	protected Item createLoot() {
		return new Gold(Random.NormalIntRange(goldtodrop+50, goldtodrop+100));
	}

	@Override
	public int hitSkill(Char target) {
		return 40;
	}

	@Override
	public int drRoll() {
		return 13+Math.round((Statistics.goldThievesKilled+1/10)+1);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (item == null && enemy instanceof Hero && steal((Hero) enemy)) {
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if (state == FLEEING) {
			Dungeon.depth.drop(new Gold(), pos).sprite.drop();
		}

		return damage;
	}

	protected boolean steal(Hero hero) {
							
			Gold gold = new Gold(Random.Int(100, 300));
			if (gold.quantity() > 0) {
				goldtodrop = Math.min((gold.quantity()+100),Dungeon.gold);
				Dungeon.gold -= goldtodrop;
				GLog.w(Messages.get(GoldThief.class, "stole", gold.quantity()));
				return true;
			} else {
			   return false;
		}
	}

	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				sprite.showStatus(CharSprite.NEGATIVE,  Messages.get(Mob.class, "rage"));
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
}
