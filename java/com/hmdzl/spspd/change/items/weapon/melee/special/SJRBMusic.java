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

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.buffs.Shieldblock;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.FlameParticle;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.KindOfWeapon;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.CharSprite;
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

public class SJRBMusic extends MeleeWeapon {

	{
		//name = "SJRBMusic";
		image = ItemSpriteSheet.S_J_R_B_M;
		 
	}

	public SJRBMusic() {
		super(1, 1f, 1f, 2);
		MIN = 3;
		MAX = 6;
	}
	
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {

	      if (Random.Int(100) < 40) {
			Buff.affect(defender, Charm.class,5f).object = attacker.id();
		}		
	
		if (Random.Int(100)> 60) {
			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
				mob.beckon(attacker.pos);
			}
			attacker.sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.3f, 3);
			Sample.INSTANCE.play( Assets.SND_BEACON );
			attacker.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(this, "rap"));
		}	
	
			Ballistica route = new Ballistica(attacker.pos, defender.pos, Ballistica.PROJECTILE);
			int cell = route.collisionPos;
			int dist = Level.distance(attacker.pos, cell);
			if (dist == 2) {
				cell = route.path.get(route.dist - 1);
				Actor.addDelayed(new Pushing(attacker, attacker.pos, cell), -1);
				attacker.pos = cell;
				if (attacker instanceof Mob) {
					Dungeon.level.mobPress((Mob) attacker);
				} else {
					Dungeon.level.press(cell, attacker);
				}
				defender.damage(damage,this);
			}
	
		int p = defender.pos;
		for (int n : Level.NEIGHBOURS8) {
			Char ch = Actor.findChar(n+p);
			if (ch != null && ch != defender && ch != attacker && ch.isAlive()) {

				int dr = Random.IntRange( 0, 1 );
				int dmg = Random.Int( MIN, MAX );
				int effectiveDamage = Math.max( dmg - dr, 0 );

				ch.damage( effectiveDamage, this );
			}
		}
		
		
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
	}
    		
}
