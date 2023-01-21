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
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Bone;
import com.hmdzl.spspd.items.RedDewdrop;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.challengelists.PrisonChallenge;
import com.hmdzl.spspd.items.food.completefood.GoldenNut;
import com.hmdzl.spspd.items.reward.PrisonReward;
import com.hmdzl.spspd.items.scrolls.ScrollOfSacrifice;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.MossySkeletonSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class MossySkeleton extends Mob {

	private static final String TXT_KILLCOUNT = "Mossy Skeleton Kill Count: %s";

	{
		spriteClass = MossySkeletonSprite.class;

		HP = HT = 90+(10*Random.NormalIntRange(7, 10));
		evadeSkill = 20;

		EXP = 1;
		
		baseSpeed = 0.5f+(Math.min(1f, Statistics.skeletonsKilled/50));

		loot = new YellowDewdrop();
		lootChance = 0.5f; // by default, see die()
			
		lootOther= new RedDewdrop();
		lootChanceOther = 0.1f; // by default, see die()
		
		properties.add(Property.UNDEAD);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20+Math.round(Statistics.skeletonsKilled/10), 45+Math.round(Statistics.skeletonsKilled/5));
		
	}

	@Override
	protected float attackDelay() {
		return 2f-(Math.min(1.5f, Statistics.skeletonsKilled/50));
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, BeOld.class).set(20);
		}
		return damage;
	}
	
	@Override
	public void die(Object cause) {

		super.die(cause);
		
		Statistics.skeletonsKilled++;
		GLog.w(Messages.get(Mob.class,"killcount", Statistics.skeletonsKilled));
		
		if ( Dungeon.depth<27) {
			Dungeon.level.drop(new PrisonChallenge(), pos).sprite.drop();
			explodeDew(pos);				
		} else {
			explodeDew(pos);
		}
		
		if(Statistics.skeletonsKilled == 25) {
			Dungeon.level.drop(new Bone(), pos).sprite.drop();
		}
		
	if(Statistics.skeletonsKilled == 50) {
		Dungeon.level.drop(new ScrollOfSacrifice(), pos).sprite.drop();
	}	
	
	if(Statistics.skeletonsKilled == 100) {
		Dungeon.level.drop(new PrisonReward(), pos).sprite.drop();
	}		
	
	if (Statistics.goldThievesKilled>99 && Statistics.skeletonsKilled == 100 
			&& Statistics.albinoPiranhasKilled>99 && Statistics.archersKilled>99){
	    Dungeon.level.drop(new GoldenNut(), pos).sprite.drop();
	}	
	
		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_BONES);
		}
	}

	
	

	@Override
	public int hitSkill(Char target) {
		return 28;
	}

	@Override
	public int drRoll() {
		return 10+Statistics.skeletonsKilled/5;
	}

	{
		//immunities.add(EnchantmentDark.class);
	}

}
