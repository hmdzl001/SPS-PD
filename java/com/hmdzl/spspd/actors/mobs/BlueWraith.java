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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BlueWraithSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class BlueWraith extends Wraith  {
	
	{
		spriteClass = BlueWraithSprite.class;

		HP = HT = 250;
		evadeSkill = 24;
		baseSpeed = 2f;

		EXP = 20;
		
		loot = Generator.random(Generator.Category.SEED);
		lootChance = 1.0f; // by default, see die()
		
		properties.add(Property.UNDEAD);
		
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			Buff.affect(enemy, Vertigo.class, 5f);
			Buff.affect(enemy, Terror.class, Terror.DURATION).object = enemy.id();
		}

		return damage;
	}

	@Override
	public void adjustStats(int level) {
		this.level = level;
		evadeSkill = 24;
		enemySeen = true;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 90);
	}

	@Override
	public int hitSkill(Char target) {
		return 46;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 25);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Level.distance( pos, enemy.pos ) <= 2 ;
	}		
	
	public static BlueWraith spawnAt(int pos) {
		
        BlueWraith b = new BlueWraith();  
        b.adjustStats(Dungeon.depth);
 		b.pos = pos;
 		b.state = b.HUNTING;
 		GameScene.add(b, SPAWN_DELAY);

 		b.sprite.alpha(0);
 		b.sprite.parent.add(new AlphaTweener(b.sprite, 1, 0.5f));

 		b.sprite.emitter().burst(ShadowParticle.CURSE, 5);

 		return b;
     
		
	}
	
}
