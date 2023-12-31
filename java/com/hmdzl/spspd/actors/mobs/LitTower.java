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
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.SparkParticle;
import com.hmdzl.spspd.items.RedDewdrop;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.OtiluckStoneSprite;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

public class LitTower extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		spriteClass = OtiluckStoneSprite.class;

		HP = HT = 600;
		evadeSkill = 1000;

		EXP = 25;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new RedDewdrop();
		lootChance = 1f;

		properties.add(Property.MECH);
		properties.add(Property.BOSS);
		properties.add(Property.MAGICER);
	}
	
	@Override
	public void beckon(int cell) {
		// Do nothing
	}

	@Override
	public int damageRoll() {
		return 0;
	}

		
	@Override
	public int hitSkill(Char target) {
		return 100;
	}

	@Override
	public int drRoll() {
		return 1000;
	}
	

	@Override
	public void damage(int dmg, Object src) {
	}
	
	@Override
	protected boolean act() {
		if(Floor.distance(pos, Dungeon.hero.pos)<5 && Dungeon.hero.isAlive() && checkOtiluke()){
			zapAll(Dungeon.hero.pos);
		}
		return super.act();
	}
	
	@Override
	public void call() {
		next();
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
	

	protected boolean heroNear (){
		boolean check=false;
		for (int i : Floor.NEIGHBOURS9DIST2){
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
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		return false;
	}


	public void zapAll(int loc){
		
		yell(Messages.get(this,"zap"));
		
		Char hero=Dungeon.hero;
				
	    int mobDmg=Random.Int(100, 200);
		
		
		 boolean visible = Floor.fieldOfView[pos] || Floor.fieldOfView[loc];
			
			
			  if (visible) {
				sprite.zap(loc);
			  }
			
			  
			  hero.damage(mobDmg,ENERGY_DAMAGE);

			  hero.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
			  hero.sprite.flash();
			
			  Camera.main.shake(2, 0.3f);			
	}
	
	@Override
	public void add(Buff buff) {
	}
	

	{
		resistances.add(EnchantmentDark.class);
		
		resistances.add(LightningTrap.Electricity.class);

		immunities.add(ToxicGas.class);
		immunities.add(Terror.class);
		immunities.add(ConfusionGas.class);
	}

}
