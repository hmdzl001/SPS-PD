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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.AdamantArmor;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Tree;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CrabKingSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class CrabKing extends Mob {

	private static final int JUMP_DELAY = 5;

	{
		spriteClass = CrabKingSprite.class;
		baseSpeed = 2f;

		HP = HT = 1300;
		EXP = 20;
		evadeSkill = 30;

		properties.add(Property.FISHER);
		properties.add(Property.BOSS);
	}

	private int timeToJump = JUMP_DELAY;


	@Override
	public Item SupercreateLoot(){
		return new Tree();
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 50);
	}

	@Override
	public int hitSkill(Char target) {
		return 35;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}

	@Override
	protected boolean act() {
		boolean result = super.act();


		if (HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + 10;
			GLog.n(Messages.get(this,"heal"));
		}
		return result;
	}
	
	@Override
	public void die(Object cause) {

		super.die(cause);
		
		GameScene.bossSlain();
		
		Dungeon.depth.drop(new Gold(Random.Int(1900, 4000)), pos).sprite.drop();
		Dungeon.depth.drop(new AdamantArmor(), pos).sprite.drop();
		Dungeon.crabkingkilled=true;
		
		yell(Messages.get(this,"die"));

	}

	@Override
	protected boolean getCloser(int target) {
		if (Floor.fieldOfView[target]) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
				if (buff(Locked.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		timeToJump--;
		if (timeToJump <= 0 && Floor.adjacent(pos, enemy.pos)) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		timeToJump = JUMP_DELAY;
		
		int newPos;
		do {
			newPos = Random.Int(Floor.getLength());
		} while (!Floor.fieldOfView[newPos] || !Floor.passable[newPos]
				|| Floor.adjacent(newPos, enemy.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}

		spend(1 / speed());
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"notice"));
	}

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		
	}

}
