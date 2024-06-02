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
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.bags.HeartOfScarecrow;
import com.hmdzl.spspd.sprites.ScarecrowSprite;
import com.watabou.utils.Bundle;

public class TestMob extends Mob {
	

	private boolean skill = false;

	{
		spriteClass = ScarecrowSprite.class;

		HP = HT = 100000;
		evadeSkill = 0;

		state = PASSIVE;

		properties.add(Property.PLANT);

		//properties.add(Property.ELEMENT);
	}


	@Override
    public boolean act() {
        //if( HP < HT && skill == false ) {
			//Buff.affect(this,AttackUp.class,5f).level(20);
			//Buff.affect(this,DefenceUp.class,5f).level(80);
           // skill = true;
       // }
        return super.act();
    }

	@Override
	public void damage(int dmg, Object src) {

		super.damage(dmg, src);
		//if (dmg >5) Buff.affect(this,ShieldArmor.class).level(20);
		//Buff.prolong(this,Levitation.class,10f);
	}

	@Override
	public int damageRoll() {
		return 10;
	}

	@Override
	public int hitSkill(Char target) {
		return 100;
	}

	@Override
	public int drRoll() {
		return 6;
	}

	//@Override
	//public int defenseProc(Char enemy, int damage) {
	//	Buff.affect(enemy,Terror.class,6f).object = this.id();
	//	return super.defenseProc(enemy, damage);
//	}
	
	@Override
	public void beckon(int cell) {
		// Do nothing
	}
	
	@Override
	public void die(Object cause) {
		super.die(cause);

		Dungeon.depth.drop(new HeartOfScarecrow(), pos).sprite.drop();
		explodeDew(pos);

		//UIcecorps2.spawnAt(pos);

		for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
			if (mob instanceof TestMob && mob.isAlive())
				mob.HP+=10;
				Buff.affect(mob, ShieldArmor.class).level(1000);
		}

	}

	{
		//WEAKNESS.add(Burning.class);
		//WEAKNESS.add(WandOfFirebolt.class);
		//WEAKNESS.add(TestWeapon.class);
		//resistances.add(Hero.class);
		//resistances.add(Wand.class);
		immunities.add(Terror.class);
		immunities.add(Amok.class);
		immunities.add(Charm.class);
		immunities.add(Sleep.class);
		immunities.add(Vertigo.class);
	}

	private final String SKILL = "skill";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SKILL, skill);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		skill = bundle.getBoolean(SKILL);
	}	

}
