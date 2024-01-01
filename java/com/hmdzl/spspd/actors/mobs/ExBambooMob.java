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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.SwampGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.damageblobs.EarthEffectDamage;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Wound;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ExBambooSprite;
import com.watabou.utils.Random;

public class ExBambooMob extends BambooMob {

	{
		
		HP = HT = 80;
		spriteClass = ExBambooSprite.class;
		properties.add(Property.PLANT);
	}
	
	@Override
	public boolean act() {

		if (isAlive()) {
			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
				GameScene.add(Blob.seed(pos + Floor.NEIGHBOURS8[i], 3, EarthEffectDamage.class));
			}
		}
		if( 1 > 2 * HP / HT && !skilluse && isAlive() ) {
			skilluse = true;
			this.HP = this.HT;
			for (int i = 0; i < Floor.NEIGHBOURS8DIST2.length; i++) {
				Char ch = findChar(pos + Floor.NEIGHBOURS8DIST2[i]);
				if (ch != null && ch.isAlive()) {
					Buff.affect(ch,Roots.class,10f);
				}
			}
			this.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
		}

		return super.act();
	}	

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.ARMOR);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(6, 24);
	}

		@Override
		public int hitSkill( Char target ) {
			return 20;
		}

		@Override
		public int drRoll() {
			return Random.NormalIntRange(5, 10);
		}		
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(this,DefenceUp.class,3f).level(20);
		}
		return super.attackProc(enemy, damage);
	}
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = Random.IntRange(0, damage) - enemy.drRoll();
		if (dmg > 0) {
			enemy.damage(dmg, this);
			Wound.hit(enemy);
		}

		return super.defenseProc(enemy, damage);
	}

	@Override
	public void damage(int dmg, Object src) {
		 if(  1 > 2 * HP / HT && !skilluse ) {
			dmg = 0;
		} else if (dmg> HT/3) {
			dmg =(int)Math.max(HT/3,1);
		}
		super.damage(dmg,src);
	}

	
	//private static final HashSet<Class<?>> resistances = new HashSet<>();


	{
		weakness.add(ToxicGas.class);
		weakness.add(Ooze.class);
		weakness.add(SwampGas.class);

		resistances.add(Roots.class);

		weakness.add(Wand.class);
		weakness.add(Poison.class);

		immunities.add(DamageType.EarthDamage.class);

	}

}
