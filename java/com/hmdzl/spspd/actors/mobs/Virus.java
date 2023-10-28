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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.watabou.utils.Random;


public class Virus extends Mob {
	protected static final float SPAWN_DELAY = 1f;
	{
		spriteClass = ErrorSprite.class;
		HP = HT = Dungeon.hero.HT /5;
		EXP = 0;
		evadeSkill = Dungeon.hero.evadeSkill;

		sumcopy = true;
		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.hero.lvl/2, Dungeon.hero.lvl);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.hitSkill;
	}

	@Override
	protected boolean act() {
		damage(1, this);
		return super.act();
	}

	@Override
	public int drRoll() {
		return 0;
	}
	
	@Override
	public float speed() {
        return 1f;
	}

	//@Override
//	public void damage(int dmg, Object src) {
	
	//	if (dmg > HT/2){
	//
	//	}
	//	super.damage(dmg, src);
	//}
	
	@Override
	public void die(Object cause) {
		GameScene.add(Blob.seed(pos, 20, CorruptGas.class));
		super.die(cause);
	}

	@Override
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}		
	
    { 
		//immunities.add(EnchantmentDark.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(ScrollOfPsionicBlast.class);
		immunities.add(CorruptGas.class);
	}
	
}	