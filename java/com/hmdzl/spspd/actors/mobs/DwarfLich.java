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

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.weapon.melee.TrickSand;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.DwarfLichSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;

public class DwarfLich extends Mob {
	
	private static final float SPAWN_DELAY = 2f;

	{
		spriteClass = DwarfLichSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(7, 5));
		evadeSkill = 24+adj(1);
	
		EXP = 14;
		maxLvl = 30;
		
		loot = new PotionOfHealing();
		lootChance = 0.3f;
		
		lootOther = new Blackberry();
		lootChanceOther = 0.3f;
		
		properties.add(Property.UNDEAD);
		properties.add(Property.MAGICER);
		properties.add(Property.DWARF);
	}

	@Override
	public Item SupercreateLoot(){
		return new TrickSand();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 32);
	}

	@Override
	public int hitSkill(Char target) {
		return 36+adj(1);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 15);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT);
		return !Floor.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
		}

	@Override
	protected boolean getCloser(int target) {
		if (state == HUNTING) {
			return enemySeen && getFurther(target);
		} else {
			return super.getCloser(target);
		}
	}
	@Override
	public void die(Object cause) {
		RedWraith.spawnAt(this.pos);
		super.die(cause);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		
		enemy.damage(damageRoll()/2,  DARK_DAMAGE );
		damage = damage/2;

		return damage;
	}		
	
	public static void spawnAround(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}
	
	public static DwarfLich spawnAt(int pos) {
		
		DwarfLich d = new DwarfLich();  
    	
			d.pos = pos;
			d.state = d.HUNTING;
			GameScene.add(d, SPAWN_DELAY);

		return d;
    
    }
	
	{
		
		resistances.add(Poison.class);
	}


}
