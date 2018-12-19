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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.FrostGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ShamanSprite;
import com.hmdzl.spspd.change.sprites.UGooSprite;
import com.hmdzl.spspd.change.utils.GLog;

import com.watabou.noosa.Camera;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.sprites.GooSprite;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.items.weapon.melee.special.Handcannon;


public class Virus extends Mob {
	protected static final float SPAWN_DELAY = 1f;
	{
		spriteClass = ErrorSprite.class;
		HP = HT = (int)Dungeon.hero.HT/5;
		EXP = 0;
		evadeSkill = Dungeon.hero.evadeSkill;

		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}
	
	int generation = 0;
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.hero.lvl/2, Dungeon.hero.lvl);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.hitSkill;
	}

	@Override
	public int drRoll() {
		return 0;
	}
	
	@Override
	public float speed() {
        return 1f;
	}

	@Override
	public void damage(int dmg, Object src) {
	
		if (dmg > HT/2){
			GameScene.add(Blob.seed(pos, 50, CorruptGas.class));
		}
		super.damage(dmg, src);
	}		
	
	@Override
	public void die(Object cause) {
		super.die(cause);
	}

	@Override
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}		
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {

		IMMUNITIES.add(EnchantmentDark.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(CorruptGas.class);
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}	
}	