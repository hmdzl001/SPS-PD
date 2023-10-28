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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MagicWeak;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Flare;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.fruit.Fruit;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.AbiSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;

public class Abi extends PET {
	
	{
		//name = "FoxHelper";
		spriteClass = AbiSprite.class;
        //flying=true;
		state = HUNTING;
		type = 405;
        cooldown=5;
		oldcooldown=60;
		properties.add(Property.BEAST);
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Plant.Seed ||
				item instanceof Vegetable ||
				item instanceof Fruit;
	}


	@Override
	public void updateStats()  {
		evadeSkill = hero.petLevel;
		HT = 150 + 3*hero.petLevel;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange((int)(5+hero.petLevel*0.5), (int)(5+hero.petLevel*1.5));
	}

	@Override
	public Item SupercreateLoot(){
		return new WandOfLight();
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (cooldown > 1) {
			return super.doAttack(enemy);
		} else if (Random.Int(20) !=0 ){
			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				sprite.zap(enemy.pos);
			} else {
				zap();
			}
			return !visible;
		} else {
			superattack();
			return true;
		}

	}

	private void superattack() {
		new Flare(6, 32).color(0x33FF33, true).show(this.sprite, 2f);
		Sample.INSTANCE.play(Assets.SND_TELEPORT);
		int dmg = this.damageRoll();
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (mob.isAlive() && ! (mob instanceof PET)) {
				mob.damage(dmg * 3, ENERGY_DAMAGE);
			}
			if (!enemy.isAlive() && enemy == Dungeon.hero) {
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
			}
		}
		this.cooldown = 5;
		spend(1f);
	}

	private void zap() {
		spend(1f);

		if (hit(this, enemy, true)) {
			int dmg = this.damageRoll();
			if (Random.Int(5) == 1) {
				enemy.damage(dmg*2, DARK_DAMAGE);
				Buff.affect(enemy, MagicWeak.class,6f);
				this.cooldown = 5;
			} else {
				enemy.damage(dmg, LIGHT_DAMAGE);
				Buff.affect(enemy, Blindness.class,6f);
				this.cooldown = 5;
			}
			if (!enemy.isAlive() && enemy == Dungeon.hero) {
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
			}
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(hero.petLevel,hero.petLevel*3);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 5;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
        cooldown--;
		return damage;
	}	

}	
	