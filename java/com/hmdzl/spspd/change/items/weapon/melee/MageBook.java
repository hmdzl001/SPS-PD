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
package com.hmdzl.spspd.change.items.weapon.melee;

import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.bags.Bag;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.items.wands.*;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.hmdzl.spspd.change.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.KindOfWeapon;
import com.hmdzl.spspd.change.messages.Messages;

public class MageBook extends MeleeWeapon {

	{
		//name = "Mage Book";
		image = ItemSpriteSheet.MAGEBOOK;
		 
		usesTargeting = true;
	}

	public MageBook() {
		super(1, 1f, 1f, 1);
		MIN = 1;
		MAX = 6;
	}
	

	@Override
	public Item upgrade(boolean enchant) {
		
		if (STR > 1) {
			STR --;
		}
		return super.upgrade(enchant);
    }
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {
		/*if (wand != null && Dungeon.hero.subClass == HeroSubClass.BATTLEMAGE) {
			if (wand.curCharges < wand.maxCharges) wand.curCharge += 0.33f;
			ScrollOfRecharging.charge((Hero)attacker);
			wand.onHit(this, attacker, defender, damage);
		}*/
		if (Random.Int(100) < 50) {
			Buff.prolong(defender, Paralysis.class, 2);
		}
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
		if (durable() && attacker == Dungeon.hero){
			durable --;
            if (durable == 10){
                GLog.n(Messages.get(KindOfWeapon.class,"almost_destory"));
            }
			if (durable == 0){
				Dungeon.hero.belongings.weapon = null;
				GLog.n(Messages.get(KindOfWeapon.class,"destory"));
			}
		}		
    }		
}
