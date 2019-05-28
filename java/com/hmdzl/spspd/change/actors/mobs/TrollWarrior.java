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

import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.melee.special.TekkoKagi;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.AssassinSprite;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.hmdzl.spspd.change.sprites.TrollWarriorSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class TrollWarrior extends Mob {

	private boolean endless = false;
	
	{
		spriteClass = TrollWarriorSprite.class;
		baseSpeed = 1.2f;

		HP = HT = 80+(5*Random.NormalIntRange(2, 5));
		EXP = 10;
		maxLvl = 20;
		evadeSkill = 15;
		
		loot = new StoneOre();
		lootChance = 0.2f;

		properties.add(Property.TROLL);
		
	}

	@Override
    public boolean act() {
        if( HP < HT && endless == false ) {
			Buff.affect(this,AttackUp.class,8f).level(20);
			Buff.affect(this,DefenceUp.class,8f).level(80);
            endless = true;
			yell(Messages.get(this,"angry"));}
        return super.act();
    }
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 30);
	}

	@Override
	public int hitSkill(Char target) {
		return 30;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 7);
	}
	@Override
	protected float attackDelay() {
		return 1.2f;
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
	
	private final String ENDLESS = "endless";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ENDLESS, endless);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		endless = bundle.getBoolean(ENDLESS);
	}	
	
}
