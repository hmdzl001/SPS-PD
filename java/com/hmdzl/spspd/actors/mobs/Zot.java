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
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.SoulCollect;
import com.hmdzl.spspd.items.misc.AutoPotion.AutoHealPotion;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ZotSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Zot extends Mob {

	private static final int JUMP_DELAY = 10;

	{
		spriteClass = ZotSprite.class;
		baseSpeed = 0.5f;

		HP = HT = 25000;
		//HP = HT = 25;
		EXP = 20;
		evadeSkill = 40;
		//evadeSkill = 0;		
		
		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(75, 125);
		//return Random.NormalIntRange(1, 2);
	}

	@Override
	public int hitSkill(Char target) {
		return 90;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(25, 45);
		//return 0;
	}

	@Override
	protected boolean act() {
		
		if (paralysed > 0) {
			yell(Messages.get(this,"pain"));
			
			if(!checkEyes()){
				ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

				for (int i = 0; /*i < Level.NEIGHBOURS4.length*/ i < 2; i++) {
					int p = Dungeon.hero.pos + Floor.NEIGHBOURS4[i];
					if (Actor.findChar(p) == null
							&& (Floor.passable[p] || Floor.avoid[p])) {
						spawnPoints.add(p);
					}
				}

				if (spawnPoints.size() > 0 && Random.Int(10) == 0) {
					MagicEye eye = new MagicEye();
					eye.pos = Random.element(spawnPoints);

					GameScene.add(eye);
					Actor.addDelayed(new Pushing(eye, pos, eye.pos), -1);
				}
			}
			
			if (HP < HT) {
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				HP = HP + 5;
			}
		}

		
		boolean result = super.act();

		int regen = Dungeon.hero.buff(AutoHealPotion.class) != null ? 1 : Random.Int(2,5);
				
		
		if (HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + regen;			
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {

		yell(Messages.get(this,"die"));

		super.die(cause);

		GameScene.bossSlain();
		//Dungeon.level.locked=false;
		Dungeon.zotkilled=true;
        Dungeon.depth.drop(new SoulCollect(), pos).sprite.drop();
		for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[Dungeon.depth.mobs.size()])) {
			if (mob instanceof ZotPhase || mob instanceof MagicEye) {
				mob.die(null);
			}
		}
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
		if (buff(Silent.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
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
		
		if (!checkPhases()){
			ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
				int p = pos + Floor.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Floor.passable[p] || Floor.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {
				ZotPhase zot = new ZotPhase();
				zot.pos = Random.element(spawnPoints);

				GameScene.add(zot);
				Actor.addDelayed(new Pushing(zot, pos, zot.pos), -1);
			}
		}
		
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
	
	private boolean checkPhases(){
		boolean check = false;
		int phases = 0;
		for (Mob mob : Dungeon.depth.mobs) {
			if (mob != null && mob instanceof ZotPhase) {
				phases++;
				if (phases>6){
				check=true;
		        }			
			}
		}
		return check;
	    
	}

	private boolean checkEyes(){
		boolean check = false;
		int phases = 0;
		for (Mob mob : Dungeon.depth.mobs) {
			if (mob != null && mob instanceof MagicEye) {
				phases++;
				if (phases>10){
				  check=true;
				}
		}			
	  }
		return check;
	}
	
	@Override
	public void damage(int dmg, Object src, int type) {
		
		if(!checkPhases()){
			ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
				int p = Dungeon.hero.pos + Floor.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Floor.passable[p] || Floor.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {
				MagicEye eye = new MagicEye();
				eye.pos = Random.element(spawnPoints);

				GameScene.add(eye);
				Actor.addDelayed(new Pushing(eye, pos, eye.pos), -1);
			}
		}
		
		super.damage(dmg, src,type);
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
		
		immunities.add(EnchantmentDark.class);
		immunities.add(Terror.class);
		immunities.add(Amok.class);
		immunities.add(Charm.class);
		immunities.add(Sleep.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(ScrollOfPsionicBlast.class);
		immunities.add(Vertigo.class);
		immunities.add(Paralysis.class);
	}
}
