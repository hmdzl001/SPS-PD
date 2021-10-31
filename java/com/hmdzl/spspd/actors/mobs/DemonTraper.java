/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.CurseWeb;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.LightShootAttack;
import com.hmdzl.spspd.actors.buffs.ShadowCurse;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class DemonTraper extends Mob {

	{
		spriteClass = ErrorSprite.class;

		HP = HT = 300;
		evadeSkill = 17;

		EXP = 20;
		maxLvl = 29;

		loot = Generator.random(Generator.Category.RANGEWEAPON);
		lootChance = 0.2f;

		properties.add(Property.ORC);
		properties.add(Property.DEMONIC);

		FLEEING = new Fleeing();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(30, 50);
	}

	@Override
	public int hitSkill(Char target) {
		return 50;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 6);
	}

	//private int webCoolDown = 0;
	private int lastEnemyPos = -1;

	private static final String WEB_COOLDOWN = "web_cooldown";
	//private static final String LAST_ENEMY_POS = "last_enemy_pos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		//bundle.put(WEB_COOLDOWN, webCoolDown);
		//bundle.put(LAST_ENEMY_POS, lastEnemyPos);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		//webCoolDown = bundle.getInt( WEB_COOLDOWN );
		//lastEnemyPos = bundle.getInt( LAST_ENEMY_POS );
	}
	
	@Override
	protected boolean act() {
		AiState lastState = state;
		boolean result = super.act();

		//if state changed from wandering to hunting, we haven't acted yet, don't update.
		if (!(lastState == WANDERING || state == HUNTING)) {
			//webCoolDown--;
			//if (shotWebVisually){
				//result = shotWebVisually = false;
			//} else {
				if (enemy != null && enemySeen) {
					lastEnemyPos = enemy.pos;
			//	} else {
				//	lastEnemyPos = Dungeon.hero.pos;
			//	}
			}
		}
		
		if (state == FLEEING && buff( Terror.class ) == null &&
				enemy != null && enemySeen && enemy.buff(LightShootAttack.class ) == null) {
			state = HUNTING;
		}

		return result;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, LightShootAttack.class).level(6);
			//webCoolDown = 0;
			state = FLEEING;
		}

		return damage;
	}
	
	//private boolean shotWebVisually = false;

@Override
	public void move(int step) {
		if (enemySeen ){
			if (state == FLEEING){
				GameScene.add(Blob.seed(pos, Random.Int(6, 8), CurseWeb.class));
				//webCoolDown = 5 ;
				}
		}
		super.move(step);
	}

	{
		resistances.add(LightShootAttack.class);
	}
	
	{
		immunities.add(CurseWeb.class);
		immunities.add(ShadowCurse.class);
	}

	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
}
