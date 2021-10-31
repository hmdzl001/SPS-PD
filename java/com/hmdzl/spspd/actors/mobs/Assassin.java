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

import java.util.HashSet;

import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.melee.special.TekkoKagi;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.AssassinSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class Assassin extends Mob {
	
	protected static final float SPAWN_DELAY = 2f;
	
	{
		spriteClass = AssassinSprite.class;
		baseSpeed = 2f;

		HP = HT = 80+(5*Random.NormalIntRange(2, 5));
		EXP = 10;
		maxLvl = 18;
		evadeSkill = 15;
		
		loot = new StoneOre();
		lootChance = 0.2f;

		properties.add(Property.HUMAN);
		
	}

	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10, 23);
	}

	@Override
	public int hitSkill(Char target) {
		return 25;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}
	@Override
	protected float attackDelay() {
		return 0.75f;
	}

	@Override
	public void die(Object cause) {	
	
	super.die(cause);
	Statistics.assassinsKilled++;
	GLog.w(Messages.get(Mob.class,"killcount", Statistics.assassinsKilled));
	
	if (Statistics.assassinsKilled == 100){
		Dungeon.level.drop(new TekkoKagi(), pos).sprite.drop();
	}
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
				if (buff(Locked.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}
	
	public static Assassin spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			Assassin w = new Assassin();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);

			//w.sprite.alpha(0);
			//w.sprite.parent.add(new AlphaTweener(w.sprite, 1, 0.5f));

			return w;
  			
		} else {
			return null;
		}
	}

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		//resistances.add(EnchantmentDark.class);
	}
}
