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

import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.GrowSeed;
import com.hmdzl.spspd.change.actors.buffs.Locked;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.change.items.wands.WandOfLight;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentLight;
import com.hmdzl.spspd.change.levels.PrisonBossLevel;
import com.hmdzl.spspd.change.levels.traps.PoisonTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.TomeOfMastery;
import com.hmdzl.spspd.change.items.journalpages.Sokoban2;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.DemonflowerSprite;
import com.hmdzl.spspd.change.sprites.TenguSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class DemonFlower extends Mob {

	private static final int DEBUFF_DELAY = 6;

	{
		spriteClass = DemonflowerSprite.class;

		HP = HT = 450;
		EXP = 12;
		evadeSkill = 5;

		properties.add(Property.PLANT);
		properties.add(Property.DEMONIC);

		loot = Generator.Category.SEED;
		lootChance = 0.5f;

	}

	private int addDebuff = DEBUFF_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(26, 37);
	}

	@Override
	public int hitSkill(Char target) {
		return 35+adj(1);
	}

	@Override
	protected float attackDelay() {
		return 0.33f;
	}	
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}

	@Override
	protected boolean doAttack(Char enemy) {
		addDebuff--;
		if (addDebuff <= 0 && Level.adjacent(pos, enemy.pos) && (buff(Silent.class) == null)) {
			debuffadd();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = damage;
		if (dmg < HT/9 && buff(DefenceUp.class) == null) {
			Buff.affect(this,DefenceUp.class,3f).level(dmg);
		}

		return super.defenseProc(enemy, damage);
	}	
	
	private void debuffadd() {
		addDebuff = DEBUFF_DELAY;

		int pos = enemy.pos;
		Char ch;
		if ((ch = Actor.findChar(pos)) != null) {
			Buff.prolong(ch, AttackDown.class,5f).level(30);
			Buff.prolong(ch, ArmorBreak.class,5f).level(30);
		}
		GLog.n(Messages.get(this,"debuff"));

		spend(1f);
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> WEAKNESS = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		WEAKNESS.add(Burning.class);
		
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(EnchantmentDark.class);
		
	    IMMUNITIES.add(GrowSeed.class);
		
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> weakness() {
		return WEAKNESS;
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}	
}
