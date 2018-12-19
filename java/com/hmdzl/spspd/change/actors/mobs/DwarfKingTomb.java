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
package com.hmdzl.spspd.change.actors.mobs;

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.items.ArmorKit;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.DwarfKingTombSprite;
import com.watabou.utils.Random;

public class DwarfKingTomb extends Mob  {

	{
		spriteClass = DwarfKingTombSprite.class;

		HP = HT = 1000;
		evadeSkill = 5;

		EXP = 10;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new RedDewdrop();
		lootChance = 0.05f;

		properties.add(Property.MECH);
		properties.add(Property.BOSS);
	}
	
	@Override
	public void beckon(int cell) {
		// Do nothing
	}
	
	@Override
	public void add(Buff buff) {
	}
	
	
	@Override
	public int damageRoll() {
		return 0;
	}
	
	@Override
	public int hitSkill(Char target) {
		return 0;
	}

	@Override
	public int drRoll() {
		return 18;
		
	}
	
	
	public boolean checkKing(){
		
		int kingAlive=0;
		if(Dungeon.level.mobs!=null){
       for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof King){
				kingAlive++;
			   }
			}
		}
       if (kingAlive>0){
		return true;
       } else {
      return false;
       }
	}
	
	@Override
	public void damage(int dmg, Object src) {
		if(checkKing()){
			yell(Messages.get(this , "impossible"));
		} else {
		super.damage(dmg, src);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {
		
        super.die(cause);
		
		for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
			if (mob instanceof DwarfLich || mob instanceof King || mob instanceof King.Undead || mob instanceof Wraith) {
				mob.die(cause);
			}
		}
		
		GameScene.bossSlain();
		Dungeon.level.drop(new ArmorKit(), pos).sprite.drop();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(4900, 10000)), pos).sprite.drop();

		

		Badges.validateBossSlain();
	
	}

		
}
