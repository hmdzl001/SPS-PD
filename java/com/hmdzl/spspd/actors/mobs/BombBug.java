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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.actors.buffs.StoneIce;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.potions.PotionOfFrost;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BombBugSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BombBug extends IceBug {

	private int breaks=0;
	{
		spriteClass = BombBugSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 15+adj(0);
		baseSpeed = 1.5f;

		EXP = 9;
		maxLvl = 25;
		
		loot = new StoneOre();
		lootChance = 0.3f;		
		
		properties.add(Property.BEAST);
	}


	private static final String BREAKS	= "breaks";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( BREAKS, breaks );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		breaks = bundle.getInt( BREAKS );
	}


	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new PotionOfFrost(), new WandOfFreeze());
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10, 15+adj(0));
	}

	@Override
	public boolean act() {

		if( 1 - breaks > 2 * HP / HT ) {
			breaks++;
            IceBug.spawnAround(pos);
			return true;
		}

		return super.act();
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.affect(enemy, FrostIce.class).level(5);
		}

		return damage;
	}

	@Override
	public void damage(int dmg, Object src,int type) {
		if (dmg> HT/6) {
			dmg =(int)Math.max(HT/6,1);
		}

		if (Random.Int(8) == 0) {
			for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
				Char ch = findChar(pos + Floor.NEIGHBOURS8[i]);
				if (ch != null && ch.isAlive()) {
					Buff.affect(ch, StoneIce.class).level(10);
				}
			}
		}

		super.damage(dmg,src,type);

	}


	@Override
	public void die(Object cause) {

		super.die(cause);

		for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Floor.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				Buff.affect(ch,StoneIce.class).level(10);
			}
		}

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_BLAST);
		}


	}


	@Override
	public int hitSkill(Char target) {
		return 16+adj(0);
	}

	@Override
	public int drRoll() {
	    return Random.NormalIntRange(2, 5);
	}

	public static void spawnAround(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static BombBug spawnAt(int pos) {
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {
          
			BombBug w = new BombBug();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, 1f);
			return w;
  			
		} else {
			return null;
		}
	}			
}