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
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.items.AdamantRing;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ThiefKingSprite;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class ThiefKing extends Mob implements Callback {

	
	{
		spriteClass = ThiefKingSprite.class;

		HP = HT = 2000;
		evadeSkill = 28;

		EXP = 60;
		flying = true;

		loot = Generator.Category.SCROLL;
		lootChance = 1f;

		properties.add(Property.ELF);
		properties.add(Property.BOSS);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 70);
	}

	@Override
	public int hitSkill(Char target) {
		return 25;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(6, 14);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		if (buff(Locked.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}

	@Override
	public void die(Object cause) {
		
        super.die(cause);

		GameScene.bossSlain();
		Dungeon.level.drop(new AdamantRing(), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(1900, 4000)), pos).sprite.drop();
		
		
		Dungeon.banditkingkilled=true;

		yell(Messages.get(this,"die"));
						
	}
	
	
	@Override
	public void call() {
		next();
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"notice",Dungeon.hero.givenName()));
	}

	{
		resistances.add(LightningTrap.Electricity.class);
	}

}
