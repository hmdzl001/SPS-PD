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
import com.hmdzl.spspd.actors.blobs.damageblobs.FireEffectDamage;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.FireRabbitSprite;
import com.watabou.utils.Random;

public class FireRabbit extends Mob {

	{
		spriteClass = FireRabbitSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(3, 5));
		evadeSkill = 8+adj(0);

		EXP = 5;
        maxLvl = 20;

		loot = new PotionOfLiquidFlame();
		lootChance = 0.1f;
		
		properties.add(Property.ORC);
	}

	private Ballistica beam;



	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.FOOD);
	}

	@Override
	public boolean attack(Char enemy) {

		for (int hitpos : beam.subPath(1, beam.dist)) {
			GameScene.add(Blob.seed(hitpos, 5, FireEffectDamage.class));
		}
		return super.attack(enemy);
	}


	@Override
	public int damageRoll() {
		return Random.NormalIntRange(6, 8+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 12;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Burning.class).set(4f);
			yell( Messages.get(this, "yell") );
		}
        if (Floor.distance( pos, enemy.pos ) == 3){
			damage = 0;
			Buff.affect(enemy, ArmorBreak.class,5f).level(20);
		}
		return damage;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		beam = new Ballistica( pos, enemy.pos, Ballistica.STOP_TARGET);
		return beam.subPath(1, beam.dist).contains(enemy.pos) && Floor.distance( pos, enemy.pos ) <= 3 ;
	}		


	{
		immunities.add(Locked.class);
		immunities.add(Burning.class);
		immunities.add(DamageType.FireDamage.class);
		immunities.add(Fire.class);
		immunities.add(WandOfFirebolt.class);
	}

}
