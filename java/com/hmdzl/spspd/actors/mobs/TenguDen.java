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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.items.AdamantRing;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.misc.BigBattery;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.TenguSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class TenguDen extends Mob {

	private static final int JUMP_DELAY = 5;
	protected static final float SPAWN_DELAY = 2f;
	
	{
		spriteClass = TenguSprite.class;
		baseSpeed = 2f;

		HP = HT = 2000;
		EXP = 20;
		evadeSkill = 30;

		properties.add(Property.HUMAN);
		properties.add(Property.BOSS);
	}

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 25);
	}

	@Override
	public int hitSkill(Char target) {
		return 28;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 20);
	}

	@Override
	public Item SupercreateLoot(){
			return new BigBattery().identify();
	}	
	
	
	@Override
	public void die(Object cause) {

		super.die(cause);
		
		GameScene.bossSlain();
	    Dungeon.tengudenkilled=true;	
	    yell(Messages.get(this,"die"));
	   	
	    Dungeon.level.drop(new AdamantRing(), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(1900, 4000)), pos).sprite.drop();

	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target]) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
				if (buff(Locked.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		timeToJump--;
		if (timeToJump <= 0 ) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if(Random.Int(15)==0){
			Buff.affect(enemy, Burning.class).set(5f);
			enemy.sprite.emitter().burst(FlameParticle.FACTORY, 5);
		}
		if(Random.Int(20)==0){
			Buff.affect(enemy, Slow.class, 3f);
		}

		if(Random.Int(30)==0){
			Buff.prolong(enemy, Paralysis.class, 2f);
		}

		return damage;
	}


	private void jump() {
		timeToJump = JUMP_DELAY;

		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (!Level.passable[newPos]
				|| Level.adjacent(newPos, Dungeon.hero.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}

		spend(1 / speed());
		
		if (Dungeon.level.mobs.size()<7){
		 Assassin.spawnAt(pos);
		}
		
		
	}

	public static TenguDen spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			TenguDen w = new TenguDen();
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
	
	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"notice"));
	}

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		//resistances.add(EnchantmentDark.class);	
	}

}
