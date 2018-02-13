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

	public class GreatCrab extends Crab {
		{
			//name = "great crab";
			spriteClass = GreatCrabSprite.class;

			HP = HT = 100;
			defenseSkill = 0; // see damage()
			baseSpeed = 1f;

			EXP = 6;

			state = WANDERING;

			properties.add(Property.BEAST);
			properties.add(Property.MINIBOSS);
		}

		private int moving = 0;

		@Override
		protected boolean getCloser(int target) {
			// this is used so that the crab remains slower, but still detects
			// the player at the expected rate.
			moving++;
			if (moving < 3) {
				return super.getCloser(target);
			} else {
				moving = 0;
				return true;
			}

		}

		@Override
		public void damage(int dmg, Object src) {
			// crab blocks all attacks originating from the hero or enemy
			// characters or traps if it is alerted.
			// All direct damage from these sources is negated, no exceptions.
			// blob/debuff effects go through as normal.
			if ((enemySeen && state != SLEEPING && paralysed == 0)
					&& (src instanceof Wand || src instanceof LightningTrap.Electricity || src instanceof Char)){
				GLog.n( Messages.get(this, "noticed") );
				sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "blocked") );
			} else {
				super.damage(dmg, src);
			}
		}

		@Override
		public void die(Object cause) {
			super.die(cause);

			Ghost.Quest.process();

			Dungeon.level.drop(new MysteryMeat(), pos);
			Dungeon.level.drop(new MysteryMeat(), pos).sprite.drop();
		}

	}