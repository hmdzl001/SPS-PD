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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.Weapon.Enchantment;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.SentinelSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MineSentinel extends Mob {
	

	{
		spriteClass = SentinelSprite.class;

		EXP = 25;
		state = PASSIVE;
		
		properties.add(Property.MECH);
	}
	private static final int REGENERATION = 100;

	private Weapon weapon;

	public MineSentinel() {
		super();

		do {
			weapon = (Weapon) Generator.random(Generator.Category.OLDWEAPON);
		} while (!(weapon instanceof MeleeWeapon) || weapon.level < 0);

		weapon.identify();
		weapon.enchant(Enchantment.randomLow());
		weapon.upgrade(10);
		

		HP = HT = 400 + Dungeon.dungeondepth * 10;
		//HP = HT = 5;
		evadeSkill = 15;
		//evadeSkill = 2;
	}

	private static final String WEAPON = "weapon";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(WEAPON, weapon);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		weapon = (Weapon) bundle.get(WEAPON);
	}

	@Override
	protected boolean act() {
		
		Hero hero = Dungeon.hero;
		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
		
		
		if(state==HUNTING){
			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
				int p = pos + Floor.NEIGHBOURS8[i];
				Char ch = Actor.findChar(p);
				if (ch != null && ch instanceof MineSentinel &&  Random.Int(10)<2) {
					ch.damage(1, this,3);
					if (((Mob)ch).state==PASSIVE){
						((Mob)ch).state = HUNTING;
					}
				 break;
				}
			}
			
		}
		
		if (!heroNear() && Random.Float() < 0.50f && state==HUNTING){
			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
				int p = hero.pos + Floor.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Floor.passable[p] || Floor.avoid[p])) {
					spawnPoints.add(p);
				}
			}
			
			if (spawnPoints.size() > 0) {
				int newPos;
				newPos=Random.element(spawnPoints);
				Actor.freeCell(pos);
				CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
				pos = newPos;
				sprite.place(pos);
				sprite.visible = Dungeon.visible[pos];
			}
			
		} else {
		
		 if (HP<(HT/4) && Random.Float() < 0.50f && state!=PASSIVE){
			int newPos = -1;
				for (int i = 0; i < 20; i++) {
				newPos = Dungeon.depth.randomRespawnCellMob();
				if (newPos != -1) {
					break;
				}
			}
			if (newPos != -1) {
				Actor.freeCell(pos);
				CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
				pos = newPos;
				sprite.place(pos);
				sprite.visible = Dungeon.visible[pos];
				HP += REGENERATION;
			}					
			
		 }
		}
		return super.act();
	}

	@Override
	protected boolean canAttack(Char enemy) {
		//return checkOtiluke();
		if (checkOtiluke()) {
			return false;
		}else return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = Random.IntRange(0, damage/3);
		if (dmg > 0 && checkOtiluke()) {
			enemy.damage(dmg, this,1);
		}

		return super.defenseProc(enemy, damage);
	}

	protected boolean heroNear (){
		boolean check=false;
		for (int i : Floor.NEIGHBOURS9){
			int cell=pos+i;
			if (Actor.findChar(cell) != null	
				&& (Actor.findChar(cell) instanceof Hero)
				){
				check=true;
			}			
		}		
		return check;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(weapon.MIN, weapon.MAX);
	}

	@Override
	public int hitSkill(Char target) {
		return (int) ((30 + Dungeon.dungeondepth *2) * weapon.ACU);
	}

	@Override
	protected float attackDelay() {
		return weapon.DLY;
	}

	@Override
	public int drRoll() {
		//return Dungeon.depth*3;
		return Random.NormalIntRange(3, 30);
	}

	@Override
	public void damage(int dmg, Object src, int type) {

		if (state == PASSIVE) {
			state = HUNTING;
		}

		super.damage(dmg, src,type);
	}
	

	@Override
	public int attackProc(Char enemy, int damage) {
		weapon.proc(this, enemy, damage);
		return damage;
	}

	@Override
	public void beckon(int cell) {
		// Do nothing
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Dungeon.depth.drop(weapon, pos).sprite.drop();
	}

	@Override
	public boolean reset() {
		state = PASSIVE;
		return true;
	}

	protected boolean checkOtiluke(){
		boolean check = false;

		for (Mob mob : Dungeon.depth.mobs) {
			if (mob instanceof Otiluke) {
				check=true;
			}
		}
		return check;
	}

	@Override
	public String description() {
		return Messages.get(this, "desc", weapon.name());
	}	
	
	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		//resistances.add(EnchantmentDark.class);
		
		//immunities.add(EnchantmentDark.class);
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
