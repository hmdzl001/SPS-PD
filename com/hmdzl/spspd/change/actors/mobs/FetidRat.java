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

import com.hmdzl.spspd.change.actors.mobs.npcs.Ghost;
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
	
	public class FetidRat extends Rat {

		{
			//name = "fetidrat";
			spriteClass = FetidRatSprite.class;

			HP = HT = 45;
			defenseSkill = 5;

			EXP = 4;

			state = WANDERING;

			properties.add(Property.BEAST);
			properties.add(Property.MINIBOSS);
		}

		@Override
		public int attackSkill(Char target) {
			return 12;
		}

		@Override
		public int dr() {
			return 2;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(3) == 0) {
				Buff.affect(enemy, Ooze.class);
			}

			return damage;
		}

		@Override
		public int defenseProc(Char enemy, int damage) {

			GameScene.add(Blob.seed(pos, 20, StenchGas.class));

			return super.defenseProc(enemy, damage);
		}

		@Override
		public void die(Object cause) {
			super.die(cause);

			Ghost.Quest.process();
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(StenchGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}