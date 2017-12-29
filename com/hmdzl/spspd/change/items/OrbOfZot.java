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
package com.hmdzl.spspd.change.items;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.OrbOfZotMob;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.artifacts.DriedRose;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.journalpages.DragonCave;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.InterlevelScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Shieldblock;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.watabou.noosa.audio.Sample;

public class OrbOfZot extends Item {

	//private static final String AC_END = "END THE GAME";

	{
		//name = "Orb Of Zot";
		image = ItemSpriteSheet.ORBOFZOT;
		defaultAction = AC_ACTIVATETHROW;
		unique = true;
	}

	private static boolean activate = false;
	public final int fullCharge = 250;
	public int charge = 0;
	private static final String CHARGE = "charge";

	private static final String AC_ACTIVATETHROW = "ACTIVATETHROW";
	private static final String AC_BREAK = "BREAK";
	
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
		if (charge >= 500){
		actions.add(AC_ACTIVATETHROW);
		}
		actions.add(AC_BREAK);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_ACTIVATETHROW)) {
			if (charge < 500)
				GLog.i(Messages.get(this, "rest"));
            else {
			activate = true;
			action = AC_THROW;
			}
		} else {
			activate = false;
		}
		
		if (action.equals(AC_BREAK)){
			Dungeon.level.drop(new DragonCave(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			this.detachAll(Dungeon.hero.belongings.backpack);
			Sample.INSTANCE.play(Assets.SND_BLAST);
			hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
		}
		
		super.execute(hero, action);
	}

	@Override
	protected void onThrow(int cell) {
		
		if (Actor.findChar(cell) != null) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int i : Level.NEIGHBOURS8)
				if (Level.passable[cell + i])
					candidates.add(cell + i);
			int newCell = candidates.isEmpty() ? cell : Random
					.element(candidates);
			
			   if (!Level.pit[newCell] && activate) {
				   OrbOfZotMob.spawnAt(newCell);
			   } else {
			   Dungeon.level.drop(this, newCell).sprite.drop(cell);
			   }
			   
		} else if (!Level.pit[cell] && activate) {
			  OrbOfZotMob.spawnAt(cell);
		} else {
			
			super.onThrow(cell);
		}

	}
	
	@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {

			if (!Statistics.orbObtained) {
				Statistics.orbObtained = true;
				Badges.validateOrbObtained();
				//showAmuletScene(true);
			}

			return true;
		} else {
			return false;
		}
	}
/*
	private void showAmuletScene(boolean showText) {
		try {
			Dungeon.saveAll();
			AmuletScene.noText = !showText;
			Game.switchScene(AmuletScene.class);
		} catch (IOException e) {
		}
	}
*/

	
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(this,"charge",charge,fullCharge);
		return info;
	}	
}
