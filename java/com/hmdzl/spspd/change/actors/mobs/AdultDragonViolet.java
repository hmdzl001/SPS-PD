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

import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.items.BossRush;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.AdultDragonVioletSprite;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.CrabKingSprite;
import com.hmdzl.spspd.change.sprites.VioletDragonSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class AdultDragonViolet extends Mob implements Callback{

	private static final int JUMP_DELAY = 20;
	private static final float TIME_TO_ZAP = 1f;

	{
		spriteClass = AdultDragonVioletSprite.class;
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
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((AdultDragonVioletSprite) sprite).zap(enemy.pos);
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
			enemy.damage(dmg, this);
			
			Buff.affect(enemy,Poison.class).set(Poison.durationFactor(enemy));
			
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

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(EnchantmentDark.class);
		
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
