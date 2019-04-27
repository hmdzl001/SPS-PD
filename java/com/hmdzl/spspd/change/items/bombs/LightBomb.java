/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.change.items.bombs;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.BlueWraith;
import com.hmdzl.spspd.change.actors.mobs.DwarfLich;
import com.hmdzl.spspd.change.actors.mobs.King;
import com.hmdzl.spspd.change.actors.mobs.King.Undead;
import com.hmdzl.spspd.change.actors.mobs.MossySkeleton;
import com.hmdzl.spspd.change.actors.mobs.RedWraith;
import com.hmdzl.spspd.change.actors.mobs.Skeleton;
import com.hmdzl.spspd.change.actors.mobs.Fiend;
import com.hmdzl.spspd.change.actors.mobs.Warlock;
import com.hmdzl.spspd.change.actors.mobs.Wraith;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.BlastParticle;
import com.hmdzl.spspd.change.effects.particles.SmokeParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class LightBomb extends Bomb {

	{
		//name = "holy hand grenade";
		image = ItemSpriteSheet.LIGHT_BOMB;
	}

	@Override
	public void explode(int cell) {
		super.explode(cell);
           for (int n: Level.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}
				
				Char ch = Actor.findChar(c);
				if (ch != null){
				Buff.affect(ch, Blindness.class,10f );
				if ( ch.properties().contains(Char.Property.UNDEAD)
					|| 	ch.properties().contains(Char.Property.UNKNOW)
					|| ch.properties().contains(Char.Property.MECH)
					||  ch.properties().contains(Char.Property.ELEMENT)
					||  ch.properties().contains(Char.Property.DEMONIC)
					|| ch.properties().contains(Char.Property.DRAGON)
					|| ch.properties().contains(Char.Property.BOSS)
					|| ch.properties().contains(Char.Property.MINIBOSS)
						) {
					int dmg = Random.NormalIntRange(200, 400);
					ch.damage(dmg, this);
                   } else {
                	int dmg = Random.NormalIntRange(50, 100);
 					ch.damage(dmg, this);
				}
			}
		}}
	     	
	    Dungeon.observe();
	}

	@Override
	public int price() {
		return 20 * quantity;
	}

	public LightBomb() {
		this(1);
	}

	public LightBomb(int value) {
		this.quantity = value;
	}
	
}
