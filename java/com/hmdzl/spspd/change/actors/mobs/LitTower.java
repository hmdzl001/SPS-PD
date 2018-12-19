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
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.actors.mobs.Yog.InfectingFist;
import com.hmdzl.spspd.change.actors.mobs.Yog.PinningFist;
import com.hmdzl.spspd.change.actors.mobs.Yog.RottingFist;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.OtiluckStoneSprite;
import com.hmdzl.spspd.change.sprites.ShellSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class LitTower extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		spriteClass = OtiluckStoneSprite.class;

		HP = HT = 600;
		evadeSkill = 750;

		EXP = 25;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new RedDewdrop();
		lootChance = 1f;

		properties.add(Property.MECH);
		properties.add(Property.BOSS);
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
		if(Level.distance(pos, Dungeon.hero.pos)<5 && Dungeon.hero.isAlive() && checkOtiluke()){
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
		
		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Otiluke) {
			check=true;	
			}
		}
		return check;
	}
	

	protected boolean heroNear (){
		boolean check=false;
		for (int i : Level.NEIGHBOURS9DIST2){
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
				
	    int mobDmg=Random.Int(200, 300);
		
		
		 boolean visible = Level.fieldOfView[pos] || Level.fieldOfView[loc];
			
			
			  if (visible) {
				((OtiluckStoneSprite) sprite).zap(loc);
			  }
			
			  
			  hero.damage(mobDmg, this);

			  hero.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
			  hero.sprite.flash();
			
			  Camera.main.shake(2, 0.3f);			
	}
	
	@Override
	public void add(Buff buff) {
	}
	

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(EnchantmentDark.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
		RESISTANCES.add(LightningTrap.Electricity.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(ConfusionGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	
}
