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
package com.hmdzl.spspd.change.items.weapon.melee.special;

import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.buffs.Shieldblock;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.FlameParticle;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.KindOfWeapon;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.messages.Messages;
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

public class Brick extends MeleeWeapon {

	{
		//name = "Brick";
		image = ItemSpriteSheet.W_BRICK;
		 
		usesTargeting = true;
	}

	public Brick() {
		super(1, 1f, 1f, 1);
		MIN = 8;
		MAX = 8;
	}
	
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {

        if (Random.Int(100) < 40) {
			Buff.prolong(defender, Shieldblock.class, 2);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	
		if (Random.Int(80) ==1 ){

			Dungeon.hero.belongings.weapon = null;
			GLog.n(Messages.get(KindOfWeapon.class,"destory"));
			Dungeon.level.drop(Generator.random(), defender.pos).sprite.drop();
			Dungeon.level.drop(Generator.random(), defender.pos).sprite.drop();
			Dungeon.level.drop(Generator.random(), defender.pos).sprite.drop();
		}		
	}
    		
}
