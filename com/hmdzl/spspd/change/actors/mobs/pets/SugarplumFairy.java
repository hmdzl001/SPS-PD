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
package com.hmdzl.spspd.change.actors.mobs.pets;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.SugarplumFairySprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class SugarplumFairy extends PET implements Callback{
	
	{
		//name = "sugarplum fairy";
		spriteClass = SugarplumFairySprite.class;       
		//flying=true;
		state = HUNTING;
		level = 1;
		type = 11;
		cooldown=500;

		properties.add(Property.UNKNOW);
	}
	private static final float TIME_TO_ZAP = 2f;
	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	@Override
	protected float attackDelay() {
		return 0.5f;
	}
	
	//Frames 0,2 are idle, 0,1,2 are moving, 0,3,4,1 are attack and 5,6,7 are for death 


	@Override
	public int dr(){
		return level*4;
	}			
	
	protected int regen = 1;	
	protected float regenChance = 0.2f;	
		

	@Override
	public void adjustStats(int level) {
		this.level = level;
		HT = 70 + level*10;
		defenseSkill = 5 + level;
	}
	

	@Override
	public int attackSkill(Char target) {
		return defenseSkill;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(defenseSkill/2, defenseSkill);
	}

	@Override
	protected boolean act() {
		
		if (cooldown>0){
			cooldown=Math.max(cooldown-(1+9*((level-1)/19)),0);
			if (cooldown==0) {GLog.w(Messages.get(this,"ready"));}
		}
		
		if (cooldown==0 && Level.adjacent(pos, Dungeon.hero.pos) && Random.Int(1)==0){
			
			int bless = Random.Int(1+9*((level-1)/19));
			
			Dungeon.hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,	1);
			Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE,Integer.toString(bless));
			
			Dungeon.hero.HP = Math.min(Dungeon.hero.HT, Dungeon.hero.HP+bless);
			
			if (Random.Int(20)==0){
				Dungeon.hero.HT += 1;
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this,"ht"));
				cooldown=500;
			}	
		}
		
		if (Random.Float()<regenChance && HP<HT){HP+=regen;}

		return super.act();
	}
	
	
	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		
	}


	
	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((SugarplumFairySprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);
			cooldown=500;

			if (hit(this, enemy, true)) {
				int dmg = damageRoll()*2;
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, LightningTrap.LIGHTNING);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail( Messages.format(ResultDescriptions.NAMED));
						//GLog.n(TXT_LIGHTNING_KILLED, name);
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}


	
	@Override
	public void call() {
		next();
	}
		
	

	@Override
	public void interact() {

		if (this.buff(MagicalSleep.class) != null) {
			Buff.detach(this, MagicalSleep.class);
		}
		
		if (state == SLEEPING) {
			state = HUNTING;
		}
		if (buff(Paralysis.class) != null) {
			Buff.detach(this, Paralysis.class);
		}
		
		int curPos = pos;

		moveSprite(pos, Dungeon.hero.pos);
		move(Dungeon.hero.pos);

		Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
		Dungeon.hero.move(curPos);

		Dungeon.hero.spend(1 / Dungeon.hero.speed());
		Dungeon.hero.busy();
	}

}