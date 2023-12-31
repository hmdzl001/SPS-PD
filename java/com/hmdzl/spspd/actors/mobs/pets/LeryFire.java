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
package com.hmdzl.spspd.actors.mobs.pets;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SnowParticle;
import com.hmdzl.spspd.effects.particles.SparkParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.weapon.missiles.throwing.BottleFire;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.LerySprite;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class LeryFire extends PET implements Callback{
	
	{
		//name = "blue dragon";
		spriteClass = LerySprite.class;
		//flying=true;
		state = HUNTING;
		type = 508;
		oldcooldown=30;
		cooldown=50;

		properties.add(Property.ELEMENT);

	}
	private static final float TIME_TO_ZAP = 1f;

	@Override
	public void updateStats()  {
		HT = 150 + hero.petLevel*5;
		evadeSkill = hero.petLevel;
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Scroll ||
				item instanceof Potion;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((6+hero.petLevel), (6+hero.petLevel*4));
	}

	@Override
	public Item SupercreateLoot(){
		return new BottleFire();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(5+hero.petLevel,10+hero.petLevel*3);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 10;
	}

	@Override
	protected boolean act() {
		if (cooldown>0){
			cooldown--;
		}
		return super.act();
	}
	
	
	@Override
	protected boolean canAttack(Char enemy) {
		if (cooldown>0){
		  return Floor.adjacent(pos, enemy.pos);
		} else {
		  return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		}
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Floor.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Floor.fieldOfView[pos]
					|| Floor.fieldOfView[enemy.pos];
			if (visible) {
				sprite.zap(enemy.pos);
			} else {
				zap();
			}

			return !visible;
		}
	}

	
	private void zap() {
		spend(TIME_TO_ZAP);

		cooldown=Math.max(25,45 - hero.petLevel);
		switch (Random.Int (5)) {
			case 0: 		
			if (hit(this, enemy, true)) {			
			    int dmg = damageRoll()*2;
			    enemy.damage(dmg, this);
			    Buff.prolong(enemy, Frost.class, Frost.duration(enemy)* Random.Float(1f, 1.5f));
			    CellEmitter.get(enemy.pos).start(SnowParticle.FACTORY, 0.2f, 6);
		    } else {
			    enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		    }
			return;
			case 1:
		if (hit(this, enemy, true)) {			

			int dmg = damageRoll()*2;
			enemy.damage(dmg, this);
			
			Buff.affect(enemy,Poison.class).set(Dungeon.hero.petLevel + 1);

		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}			
				return;
			case 2:
		if (hit(this, enemy, true)) {			

			int dmg = damageRoll()*2;
			enemy.damage(dmg, this);
			
			if (Random.Int(dmg)<Dungeon.hero.petLevel){
				GameScene.add(Blob.seed(enemy.pos, 1, Fire.class));}
			
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}			
				return;
			case 3:
		if (hit(this, enemy, true)) {			

			int dmg = damageRoll()*3;
			enemy.damage(dmg, this);			
			
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}	
				return;
			case 4:
			if (hit(this, enemy, true)) {
				int dmg = damageRoll()*2;
				if (Floor.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, this);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
						//GLog.n(TXT_LIGHTNING_KILLED, name);
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}			
				return;
		}		
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public void call() {
		next();
	}
}