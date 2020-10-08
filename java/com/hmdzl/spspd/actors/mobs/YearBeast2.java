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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Fire;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.KindOfArmor;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.armor.normalarmor.BaseArmor;
import com.hmdzl.spspd.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.items.armor.normalarmor.RubberArmor;
import com.hmdzl.spspd.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.items.eggs.YearPetEgg;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.items.weapon.melee.special.FireCracker;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.BeastYearSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class YearBeast2 extends Mob {
	protected static final float SPAWN_DELAY = 1f;
	{
		//name = "YearBeast";
		spriteClass = BeastYearSprite.class;
		baseSpeed = 1f;

		HP = HT = 1000;
		EXP = 0;
		evadeSkill = 30;
		viewDistance = 6;
		baseSpeed=1.5f;
		flying = true;
	}

	private int times=0;

	private static final String TIMES	= "times";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( TIMES, times );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		times = bundle.getInt( TIMES );
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(40, 60);
	}

	@Override
	public int hitSkill(Char target) {
		return 40;
	}


	@Override
	public boolean act() {
		times++;
		return super.act();
	}
	@Override
	protected boolean canAttack(Char enemy) {
		return Dungeon.level.distance( pos, enemy.pos ) <= 2 ;
	}
	@Override
	public int attackProc(Char enemy, int damage) {

		if (Random.Int(2) == 0) {
			Buff.affect(enemy, Burning.class).reignite(enemy);
		} else {
			Buff.affect(enemy, Frost.class);
		}

		if (Random.Int(5) == 0) {
			Buff.affect(enemy, Charm.class,4f);
		}

		if (Random.Int(5) == 0) {
		int oppositeDefender = enemy.pos + (enemy.pos - pos);
		Ballistica trajectory = new Ballistica(enemy.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
		WandOfFlow.throwChar(enemy, trajectory, 1);
		Buff.affect(enemy, Vertigo.class,3f);
		}

		if (enemy == Dungeon.hero) {
			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;
			KindOfArmor armor = hero.belongings.armor;
            if (Random.Int(10)==0){
            	if (Random.Int(2)==0) {
					if (weapon != null && !(weapon instanceof Knuckles || weapon instanceof FightGloves)
							&& !weapon.cursed) {
						hero.belongings.weapon = null;
						Dungeon.level.drop(weapon, hero.pos).sprite.drop();
						GLog.w(Messages.get(this, "disarm"));
					}
				} else {
					if (armor != null && !(armor instanceof WoodenArmor || armor instanceof RubberArmor || armor instanceof BaseArmor)
							&& !armor.cursed) {
						hero.belongings.armor = null;
						Dungeon.level.drop(armor, hero.pos).sprite.drop();
						GLog.w(Messages.get(this, "disarm"));
					}
				}
            }
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = damage;
		if (dmg > 200 && buff(GlassShield.class) == null) {
			Buff.affect(this,GlassShield.class).turns(3);
		}
		Buff.prolong(this,DefenceUp.class,2f).level(times);
		Buff.prolong(this,AttackUp.class,4f).level(times);
		return super.defenseProc(enemy, damage);
	}

	@Override
	public void damage(int dmg, Object src) {

		if(src instanceof Wand) {
			dmg = (int)(dmg/3);
		}

        if ( src instanceof FireCracker) {
            times=0;
        }

	    if (times > 50)
		times-=3;
	
		super.damage(dmg, src);
	}


	@Override
	public void die(Object cause) {
		super.die(cause);
		Dungeon.level.drop(new YearPetEgg(), pos).sprite.drop();
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}



	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Fire.class);
		IMMUNITIES.add(Vertigo.class);
		IMMUNITIES.add(Burning.class);
	}

}
