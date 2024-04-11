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
package com.hmdzl.spspd.items.weapon.melee.start;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Shocked2;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Beam;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BeastKnive extends MeleeWeapon {

	public static final String AC_ZAP = "ZAP";
	private static final float TIME_TO_ZAP	= 1f;
	protected int collisionProperties =(Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN);

	{
		//name = "EleKatana";
		image = ItemSpriteSheet.DAGGER;
		defaultAction = AC_ZAP;
	}

	public int charge = 0;
	private static final String CHARGE = "charge";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}

	public BeastKnive() {
		super(1, 1f, 0.5f, 1);
		MIN = 4;
		MAX = 10;
		unique = true;
		reinforced = true;
		cursed = true;
	}	

	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}		
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (charge > 10)
		   actions.add(AC_ZAP);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals( AC_ZAP )) {
			if (charge < 10) {
			   GLog.w( Messages.get(EleKatana.class, "no") );
			} else {
				Buff.affect(hero, AttackUp.class, 10f).level(30);
				charge -= 10;
			}
		} else {
			
			super.execute( hero, action );
			
		}
        
		super.execute(hero, action);
	}

	public Item upgrade(boolean enchant) {
        MAX+=1;
		super.upgrade(enchant);
		return this;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
	    int DMG = damage;
		defender.damage(Random.Int(DMG / 4, DMG / 2), this);
		charge++;
		if (Random.Int(100) < 40) {
			Buff.affect(defender, Bleeding.class).set(Random.Int(5,DMG));
		}
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	}

	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(Weapon.class, "charge",charge,10);
		return info;
	}

}
