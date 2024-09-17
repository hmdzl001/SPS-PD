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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.items.BossRush;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.NewDragon01Sprite;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class AdultDragonViolet extends Mob implements Callback{

	private static final int JUMP_DELAY = 20;
	private static final float TIME_TO_ZAP = 1f;

	{
		spriteClass = NewDragon01Sprite.class;
		baseSpeed = 1.5f;

		HP = HT = 8000;
		EXP = 10;
		evadeSkill = 40;
		
		loot = new BossRush();
		lootChance = 1f;
		
		properties.add(Property.DRAGON);
		properties.add(Property.BOSS);
	}

	private int timeToJump = JUMP_DELAY;

	@Override
	public Item SupercreateLoot(){
		return new BossRush();
	}
	
	@Override
	public int damageRoll() {
		return Random.Int(60, 80);
	}

	@Override
	public int hitSkill(Char target) {
		return 50;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(20, 50);
	}

	
	@Override
	public void die(Object cause) {	
			
		super.die(cause);
		
		yell(Messages.get(this, "die"));
					
	}

	@Override
	protected boolean canAttack(Char enemy) {
		if (buff(Silent.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
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

		
		yell(Messages.get(this, "atk"));
		
		if (hit(this, enemy, true)) {			

			int dmg = damageRoll();
			enemy.damage(dmg, this,2);
			
			//Buff.affect(enemy,Poison.class).set(Random.Int(10, 20));
			Buff.affect(enemy,BeOld.class).set(20);
			
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
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

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		
	}
}
