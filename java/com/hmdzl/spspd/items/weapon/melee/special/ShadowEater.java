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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ShadowEater extends MeleeWeapon {

	{
		//name = "ShadowEater";
		image = ItemSpriteSheet.SHADOW_EATER;
		 reinforced = true;
		cursed = true;
	}

	public ShadowEater() {
		super(4, 1f, 1f, 1);
		STR = 15;
	}
	
	public static final String AC_AWAKE = "AWAKE";
	public static final String AC_UNCURSE = "UNCURSE";

    public static int charge = 0;
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

    @Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);

		if (charge >20) actions.add(AC_AWAKE);
		actions.add(AC_UNCURSE);
		
        return actions;
	}	

	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}	

	@Override
	public void execute(Hero hero, String action) {
        if (action == AC_AWAKE) {
			curUser = hero;
			this.cursed = true;
			charge = 0 ;
			GLog.i(Messages.get(this, "awake"));
            Buff.affect(hero, AttackUp.class,30f).level(300);
			Buff.affect(hero, Bleeding.class).set(hero.HT/2);
		} else if (action == AC_UNCURSE) {
            this.cursed = false;
		} else {

			super.execute(hero, action);

		}
	}	

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		if (Random.Int(100)> 70) {

		    damage*=1.5;
            if (Random.Int(4) == 0 ) {
                attacker.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.3f, 3);
                attacker.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(this, "bleeding"));
                Buff.affect(attacker, Bleeding.class).set(20);
            } else if (Random.Int(3) == 0 ) {
				attacker.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.3f, 3);
				attacker.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(this, "locked"));
				Buff.affect(attacker, Locked.class, 5f);
			} else if (Random.Int(2) == 0 ) {
				attacker.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.3f, 3);
				attacker.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(this, "stand"));
				Buff.affect(attacker, Cripple.class, 5f);
			} else {
				attacker.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.3f, 3);
				attacker.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(this, "silent"));
				Buff.affect(attacker, Silent.class, 5f);
			}
		}

        if ( defender.HP <= damage) {
            charge++;
        }

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	}
	
	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(this, "charge",charge);
		return info;
	}	
	
}
