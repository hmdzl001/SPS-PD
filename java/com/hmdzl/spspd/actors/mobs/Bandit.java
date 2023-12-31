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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BanditSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Bandit extends Thief {

	private int breaks=0;
	{
		spriteClass = BanditSprite.class;

		// 1 in 30 chance to be a crazy bandit, equates to overall 1/90 chance.
		//lootChance = 1f;
		//lootChanceOther = 1f;
		
		properties.add(Property.ELF);
	}

	private static final String BREAKS	= "breaks";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( BREAKS, breaks );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		breaks = bundle.getInt( BREAKS );
	}

	@Override
	public boolean act() {

		if( 2 - breaks > 3 * HP / HT ) {
			breaks++;
            skilluse = false;
			return true;
		}

		for (int i = 0; i < Floor.NEIGHBOURS9.length; i++) {
			GameScene.add(Blob.seed(pos + Floor.NEIGHBOURS9[i], 10, DarkGas.class));
		}

		return super.act();
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Floor.distance( pos, enemy.pos ) <= 2 ;
	}


	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.MELEEWEAPON);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		int golddrop = (int)(Dungeon.gold/20);
		if (!skilluse && enemy == Dungeon.hero) {
			skilluse = true;
			Buff.affect(this,EnergyArmor.class).level((int)(Dungeon.gold/40));
			Dungeon.gold -=golddrop;
			enemy.sprite.showStatus(CharSprite.NEUTRAL,"-" + golddrop);
		}

		if (skilluse && Random.Int(3) == 1) {
			Buff.affect(enemy, Poison.class).set(Random.Int(2, 3));
		}
		return damage;
	}

	@Override
	public void damage(int dmg, Object src) {
		if (dmg> HT/6) {
			dmg =(int)Math.max(HT/6,1);
		}

		super.damage(dmg,src);

	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Badges.validateRare(this);
	}

	{
		//immunities.add(Burning.class);
		immunities.add(Blindness.class);
		immunities.add(DarkGas.class);

	}
}
