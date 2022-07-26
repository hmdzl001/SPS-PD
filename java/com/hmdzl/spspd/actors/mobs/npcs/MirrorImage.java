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
package com.hmdzl.spspd.actors.mobs.npcs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.sellitem.Mirror2;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.MirrorSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

public class MirrorImage extends NPC {

	{
		//name = "mirror image";
		spriteClass = MirrorSprite.class;

		state = HUNTING;

		properties.add(Property.UNKNOW);
	}

	public int skin;

	private int attack;
	private int damage;

	private static final String SKIN = "skin";
	private static final String ATTACK = "attack";
	private static final String DAMAGE = "damage";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SKIN, skin);
		bundle.put(ATTACK, attack);
		bundle.put(DAMAGE, damage);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		skin = bundle.getInt(SKIN);
		attack = bundle.getInt(ATTACK);
		damage = bundle.getInt(DAMAGE);
	}

	public void duplicate(Hero hero) {
		skin = hero.useskin();
		attack = hero.hitSkill(hero);
		damage = hero.damageRoll();
	}

	@Override
	public int hitSkill(Char target) {
		return attack;
	}

	@Override
	public int damageRoll() {
		return damage;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		int dmg = super.attackProc(enemy, damage);

		destroy();
		sprite.die();

		return dmg;
	}

	@Override
	protected Char chooseEnemy() {

		if (enemy == null || !enemy.isAlive()) {
			HashSet<Mob> enemies = new HashSet<Mob>();
			for (Mob mob : Dungeon.level.mobs) {
				if (mob.hostile && Level.fieldOfView[mob.pos]) {
					enemies.add(mob);
				}
			}

			enemy = enemies.size() > 0 ? Random.element(enemies) : null;
		}

		return enemy;
	}

	@Override
	public CharSprite sprite() {
		CharSprite s = super.sprite();
		((MirrorSprite) s).updateArmor(skin);
		return s;
	}


	@Override
	public boolean interact() {
		if (!Level.passable[pos]){
			return true;
		}
		if (this.buff(MagicalSleep.class) != null) {
			Buff.detach(this, MagicalSleep.class);
		}
		
		if (state == SLEEPING) {
			state = HUNTING;
		}
		if (buff(Paralysis.class) != null) {
			Buff.detach(this, Paralysis.class);
		}
		
		int curPos = pos;

		moveSprite(pos, Dungeon.hero.pos);
		move(Dungeon.hero.pos);

		Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
		Dungeon.hero.move(curPos);

		Dungeon.hero.spend(1 / Dungeon.hero.speed());
		Dungeon.hero.busy();
		return true;
	}

	@Override
	public Item SupercreateLoot(){
		return new Mirror2();
	}

	{
		immunities.add(ToxicGas.class);
		immunities.add(Burning.class);
	}

}