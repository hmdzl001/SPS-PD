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
package com.hmdzl.spspd.change.items.weapon.melee.relic;

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.GasesImmunity;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.MagicImmunity;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Eye;
import com.hmdzl.spspd.change.actors.mobs.Warlock;
import com.hmdzl.spspd.change.actors.mobs.Yog;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.artifacts.Artifact.ArtifactBuff;
import com.hmdzl.spspd.change.items.artifacts.CloakOfShadows.cloakStealth;
import com.hmdzl.spspd.change.items.rings.Ring.RingBuff;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class LokisFlail extends RelicMeleeWeapon {

	public LokisFlail() {
		super(6, 0.8f, 1.2f, 2);
		// TODO Auto-generated constructor stub
	}

	
	{
		//name = "Loki's Flail";
		image = ItemSpriteSheet.LOKISFLAIL;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = 0;
		chargeCap = 1000;

		cooldown = 0;
		bones = false;
		
  }
		
	public static final String AC_STEALTH = "STEALTH";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_STEALTH);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_STEALTH)) {
			charge = 0;
			Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
			Sample.INSTANCE.play(Assets.SND_MELD);

		} else
			super.execute(hero, action);
	}

	
	public class StealthCounter extends WeaponBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap) {
				charge+=level;
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}
		
		@Override
		public String toString() {
			return "StealthCounter";
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.NONE;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}

	}
	
	
	
	
	@Override
	protected WeaponBuff passiveBuff() {
		return new StealthCounter();
	}
	
}


