/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
package com.hmdzl.spspd.change.items.misc;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.change.actors.blobs.DarkGas;
import com.hmdzl.spspd.change.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.change.actors.blobs.StenchGas;
import com.hmdzl.spspd.change.actors.blobs.TarGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Arcane;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Chill;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.HighLight;
import com.hmdzl.spspd.change.actors.buffs.Hot;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.buffs.Rhythm;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Shocked;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.buffs.WarGroove;
import com.hmdzl.spspd.change.actors.buffs.Wet;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.artifacts.EtherealChains;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PotionOfMage extends Item {

	public static final String AC_USE = "USE";
	public static final String AC_DRINK = "DRINK";
	private static final float TIME_TO_DIG = 1f;

	{
		image = ItemSpriteSheet.POTION_OF_MAGE;
		defaultAction = AC_USE;
		
		unique = true;
		bones = false;
	}
	
	public final int fullCharge = 100;
	public int charge = 0;
	private static final String CHARGE = "charge";

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
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (charge >= 100) {
			actions.add(AC_USE);
		}
		if (charge >= 50){
		actions.add(AC_DRINK);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {		
      if( action.equals( AC_USE ) ) {
		  if (charge < 100) {
			  GLog.i(Messages.get(PotionOfMage.class, "break"));
		  } else GameScene.selectCell(Shattered);

	  } else if (action.equals( AC_DRINK )){
		  if (charge < 50) {
			  GLog.i(Messages.get(PotionOfMage.class, "break"));
		  } else {
			  switch (Random.Int(7)) {
				  case 0:
					  Buff.affect(hero, HighLight.class, 10f);
					  break;
				  case 1:
					  Buff.affect(hero, AttackUp.class, 10f).level(35);
					  break;
				  case 2:
					  Buff.affect(hero, DefenceUp.class, 10f).level(35);
					  break;
				  case 3:
					  Buff.affect(hero, Recharging.class, 10f);
					  break;
				  case 4:
					  Buff.affect(hero, Arcane.class, 10f);
					  break;
				  case 5:
					  Buff.affect(hero, BerryRegeneration.class).level(hero.HP / 2);
					  break;
				  case 6:
					  Buff.affect(hero, Rhythm.class, 10f);
					  break;
				  default:
					  break;
			  }
			  hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			  Sample.INSTANCE.play(Assets.SND_BURNING);
			  hero.spendAndNext(1f);
			  updateQuickslot();
			  charge-=50;
		  }
		} else super.execute(hero, action);


	}

	@Override
	public String status() {
		return Messages.format("%d", (int)charge/75);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(PotionOfMage.class, "charge",charge,fullCharge);
		return info;	
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	
	private CellSelector.Listener Shattered = new CellSelector.Listener(){
		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){
				if (Actor.findChar( target ) != null){
					Char mob = Actor.findChar(target);
					Buff.affect(mob, AttackDown.class, 10f).level(35);
					Buff.affect(mob, ArmorBreak.class, 10f).level(35);
					Buff.affect(mob, Slow.class, 10f);
					Buff.affect(mob, Hot.class,10f);
					Buff.affect(mob, Wet.class, 10f);
					Buff.affect(mob, Shocked.class, 10f);
					Buff.affect(mob, Roots.class, 10f);
					charge-= 100;
					Dungeon.hero.spendAndNext(1f);
					updateQuickslot();
				} else {
					GameScene.add(Blob.seed(target, 15, ToxicGas.class));
					GameScene.add(Blob.seed(target, 15, ConfusionGas.class));
					GameScene.add(Blob.seed(target, 15, ParalyticGas.class));
					GameScene.add(Blob.seed(target, 15, DarkGas.class));
					GameScene.add(Blob.seed(target, 15, TarGas.class));
					GameScene.add(Blob.seed(target, 15, StenchGas.class));
				}
			} else GLog.i( Messages.get(PotionOfMage.class, "not_mob") );
		}
		@Override
		public String prompt() {
			return Messages.get(PotionOfMage.class, "prompt");
		}
	};
}
