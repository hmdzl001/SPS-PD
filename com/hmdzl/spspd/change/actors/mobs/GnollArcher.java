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

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Journal;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.StenchGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.mobs.Crab;
import com.hmdzl.spspd.change.actors.mobs.Gnoll;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.Rat;
import com.hmdzl.spspd.change.actors.mobs.FetidRat;
import com.hmdzl.spspd.change.actors.mobs.GnollTrickster;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.SewersKey;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.food.MysteryMeat;
import com.hmdzl.spspd.change.items.TreasureMap;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.missiles.PoisonDart;
import com.hmdzl.spspd.change.items.weapon.missiles.ForestDart;
import com.hmdzl.spspd.change.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.SewerLevel;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.FetidRatSprite;
import com.hmdzl.spspd.change.sprites.GhostSprite;
import com.hmdzl.spspd.change.sprites.GnollArcherSprite;
import com.hmdzl.spspd.change.sprites.GnollTricksterSprite;
import com.hmdzl.spspd.change.sprites.GreatCrabSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.hmdzl.spspd.change.windows.WndQuest;
import com.hmdzl.spspd.change.windows.WndSadGhost;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GnollArcher extends Gnoll {

	private static final String TXT_KILLCOUNT = "Gnoll Archer Kill Count: %s";
	{
		//name = "gnollarcher";
		spriteClass = GnollArcherSprite.class;

		HP = HT = 25;
		defenseSkill = 5;

		EXP = 1;
			
		baseSpeed = (1.5f-(Dungeon.depth/27));

		state = WANDERING;

		loot = Gold.class;
		lootChance = 0.01f;
			
		lootOther = Gold.class;
		lootChanceOther = 0.01f; 
		
		properties.add(Property.GNOLL);

	}
	
	@Override
	public int attackSkill(Char target) {
		return 26;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Level.adjacent(pos, enemy.pos) && attack.collisionPos == enemy.pos;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1+Math.round(Statistics.archersKilled/10), 8+Math.round(Statistics.archersKilled/5));
	}
	
	@Override
	protected boolean getCloser(int target) {
		if (Level.adjacent(pos, enemy.pos)) {
			return getFurther(target);
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	public void die(Object cause) {	
	if(Dungeon.depth>25){Dungeon.level.drop(new ForestDart(3), pos).sprite.drop();}
	
	Statistics.archersKilled++;
	GLog.w(Messages.get(Mob.class,"killcount", Statistics.archersKilled));

	super.die(cause);
	if (!Dungeon.limitedDrops.sewerkey.dropped() && Dungeon.depth<27) {
		Dungeon.limitedDrops.sewerkey.drop();
		Dungeon.level.drop(new SewersKey(), pos).sprite.drop();
		explodeDew(pos);				
	} else {
		explodeDew(pos);
	}
	
	if(!Dungeon.limitedDrops.tengukey.dropped() && Dungeon.tengukilled && Statistics.archersKilled > 50 && Random.Int(10)==0) {
		Dungeon.limitedDrops.tengukey.drop();
		Dungeon.level.drop(new TenguKey(), pos).sprite.drop();
	}

	if(!Dungeon.limitedDrops.tengukey.dropped() && Dungeon.tengukilled && Statistics.archersKilled > 100) {
		Dungeon.limitedDrops.tengukey.drop();
		Dungeon.level.drop(new TenguKey(), pos).sprite.drop();
	}

	if(!Dungeon.limitedDrops.treasuremap.dropped() && Statistics.archersKilled > 20 && Random.Int(10)==0) {
		Dungeon.limitedDrops.treasuremap.drop();
		Dungeon.level.drop(new TreasureMap(), pos).sprite.drop();
	}

	if(!Dungeon.tengukilled && Statistics.archersKilled > 70 && Dungeon.depth>26) {
		GLog.w(Messages.get(this,"treasure"));
	    }
    }

}