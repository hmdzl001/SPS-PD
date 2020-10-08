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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ButterflyPetSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.NewSnakeSprite;
import com.hmdzl.spspd.sprites.SnakeSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ButterflyPet extends PET {
	
	{
		//name = "Butterfly";
		spriteClass = ButterflyPetSprite.class;
        //flying=true;
		state = HUNTING;
		level = 1;
		type = 24;

		properties.add(Property.BEAST);
	}
	
	int bless = 5;
	private static final String BLESS	= "bless";
	
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( BLESS, bless );
    }	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		bless = bundle.getInt( BLESS );
		adjustStats(level);
	}

	@Override
	public void adjustStats(int level) {
		this.level = level;
		evadeSkill = 5 + level;
		HT = 90 + level*5;
	}
	



	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10, (10+level*2));
	}

	@Override
	protected boolean act() {		
			
		if (Level.adjacent(pos, Dungeon.hero.pos)){
			
			bless --;
			if (bless == 0){
			Dungeon.hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,	1);
			Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE,Integer.toString(5));
			Dungeon.hero.HP = Math.min(Dungeon.hero.HT, Dungeon.hero.HP+5);
			bless = 5;
			}

		}
		return super.act();
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (bless > 0){
			bless--;
		}
		if (Random.Int(5) == 0) {
			Buff.affect(enemy, Blindness.class,level*2);
		}

		return damage;
	}	
	
/*
	@Override
	protected Char chooseEnemy() {
		
		if(enemy != null && !enemy.isAlive()){
			kills++;
		}
		
		if (enemy == null || !enemy.isAlive()) {
			HashSet<Mob> enemies = new HashSet<Mob>();
			for (Mob mob : Dungeon.level.mobs) {
				if (!(mob instanceof PET) && mob.hostile && Level.fieldOfView[mob.pos]) {
					enemies.add(mob);
				}
			}

			enemy = enemies.size() > 0 ? Random.element(enemies) : null;
		}

		return enemy;
}
*/
}