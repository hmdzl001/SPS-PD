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

import java.util.HashSet;

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.TenguSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class TenguEscape extends Mob {

	private static final int JUMP_DELAY = 20;
	private static final int JUMPS_TO_ESCAPE = 20;
	protected static final float SPAWN_DELAY = 2f;
	
	{
		spriteClass = TenguSprite.class;
		baseSpeed = 1f;

		HP = HT = 100;
		EXP = 10;
		defenseSkill = 30;

		properties.add(Property.HUMAN);
		properties.add(Property.BOSS);
	}

	private int timeToJump = JUMP_DELAY;
	private int escapeCount = JUMPS_TO_ESCAPE;
	private int jumps=0;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 18);
	}

	@Override
	public int attackSkill(Char target) {
		return 20;
	}

	@Override
	public int dr() {
		return 15;
	}

	
	
	
	@Override
	public void die(Object cause) {

		//super.die(cause);

		if (jumps>=JUMPS_TO_ESCAPE){
	    	yell(Messages.get(this,"escape"));
	    	     } else {
		    yell(Messages.get(this,"late", Dungeon.hero.givenName()));
		    if(!Dungeon.limitedDrops.tengukey.dropped()) {
			Dungeon.limitedDrops.tengukey.drop();
			Dungeon.level.drop(new TenguKey(), pos).sprite.drop();
		}
	    	     }
		destroy();
		sprite.killAndErase();
		CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);	
					
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target] && jumps<JUMPS_TO_ESCAPE) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		timeToJump--;
		if (timeToJump <= 0 && jumps<JUMPS_TO_ESCAPE) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		timeToJump = JUMP_DELAY;
		escapeCount = JUMPS_TO_ESCAPE;
		
		
		//GLog.i("%s! ",(JUMPS_TO_ESCAPE-jumps));

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
		
		jumps++;
		
		if (jumps>=JUMPS_TO_ESCAPE){
			HP=1;
			Buff.affect(this,Poison.class).set(Poison.durationFactor(this) * 2);
		}
		
	}

	public static TenguEscape spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			TenguEscape w = new TenguEscape();
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

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
