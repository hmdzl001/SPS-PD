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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.StenchGas;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.HighLight;
import com.hmdzl.spspd.actors.buffs.Hot;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PotionOfMage extends Item {

	public static final String AC_USE = "USE";
	public static final String AC_DRINK = "DRINK";
	public static final String AC_SHATTERED = "SHATTERED";
	private static final float TIME_TO_DIG = 1f;

	{
		image = ItemSpriteSheet.POTION_OF_MAGE;
		defaultAction = AC_USE;
		
		unique = true;
		 
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
		if (charge >= 70) {
			actions.add(AC_USE);
			actions.add(AC_SHATTERED);
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
		  curUser = hero;
		  if (charge < 70) {
			  GLog.i(Messages.get(PotionOfMage.class, "break"));
		  } else GameScene.selectCell(shooter);

	  } else if( action.equals( AC_SHATTERED ) ) {
		  curUser = hero;
		  if (charge < 70) {
			  GLog.i(Messages.get(PotionOfMage.class, "break"));
		  } else GameScene.selectCell(Shattered);

	  } else  if (action.equals( AC_DRINK )){
		  curUser = hero;
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
					  Buff.affect(hero, Arcane.class, 5f);
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
		return Messages.format("%d", charge /70);
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
	@Override
	public int price() {
		return 30 * quantity;
	}	
	private CellSelector.Listener Shattered = new CellSelector.Listener(){
		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.depth.visited[target] || Dungeon.depth.mapped[target])){
					GameScene.add(Blob.seed(target, 15, ToxicGas.class));
					GameScene.add(Blob.seed(target, 15, ConfusionGas.class));
					GameScene.add(Blob.seed(target, 15, ParalyticGas.class));
					GameScene.add(Blob.seed(target, 15, DarkGas.class));
					GameScene.add(Blob.seed(target, 15, TarGas.class));
					GameScene.add(Blob.seed(target, 15, StenchGas.class));
					charge-= 70;
					Dungeon.hero.spendAndNext(1f);
					updateQuickslot();
			}
		}
		@Override
		public String prompt() {
			return Messages.get(PotionOfMage.class, "prompt");
		}
	};

	private int targetPos;
	
	public MageAmmo Ammo(){
		return new MageAmmo();
	}
	
	public class MageAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.POTION_OF_MAGE;
			ACU = 1000;
		}

		public int damageRoll(Hero owner) {
			return 0;
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == curUser || enemy == null) {
				parent = null;
				Splash.at(cell, 0xCC99FFFF, 1);
			//} else if (enemy == null){
				//GameScene.add(Blob.seed(cell, 10, ToxicGas.class));
				//GameScene.add(Blob.seed(cell, 10, ConfusionGas.class));
				//GameScene.add(Blob.seed(cell, 15, ParalyticGas.class));
				//GameScene.add(Blob.seed(cell, 15, DarkGas.class));
				//GameScene.add(Blob.seed(cell, 10, TarGas.class));
				//GameScene.add(Blob.seed(cell, 10, StenchGas.class));
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
			}
		}

		@Override
		public void proc(Char attacker, Char defender, int damage) {

			Buff.affect(defender, AttackDown.class, 10f).level(35);
			Buff.affect(defender, ArmorBreak.class, 10f).level(35);
			Buff.affect(defender, Slow.class, 10f);
			Buff.affect(defender, Hot.class,10f);
			Buff.affect(defender, Wet.class, 10f);
			Buff.affect(defender, Shocked.class).level(10);
			Buff.affect(defender, Roots.class, 10f);
			
			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			PotionOfMage.this.targetPos = cell;
			charge-=70;
			super.cast(user, dst);
		}

	}
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				Ammo().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(PotionOfMage.class, "prompt");
		}
	};
}
