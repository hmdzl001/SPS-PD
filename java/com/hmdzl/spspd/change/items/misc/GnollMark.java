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
import com.hmdzl.spspd.change.actors.buffs.Disarm;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.HighLight;
import com.hmdzl.spspd.change.actors.buffs.Hot;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.Locked;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.buffs.Rhythm;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.change.actors.buffs.Shocked;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.buffs.WarGroove;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.buffs.Wet;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.Splash;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.artifacts.EtherealChains;
import com.hmdzl.spspd.change.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GnollMark extends Item {

	public static final String AC_LIGHT = "LIGHT";
	public static final String AC_DARK = "DARK";
	public static final String AC_EARTH = "EARTH";
	private static final String AC_CHOOSE = "CHOOSE";
	private static final float TIME_TO_DIG = 1f;

	{
		image = ItemSpriteSheet.GNOLL_MARK;
		defaultAction = AC_CHOOSE;
		
		unique = true;
		 
	}
	
	public final int fullCharge = 40;
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
		if (charge >= 20) {
			actions.add(AC_LIGHT);
			actions.add(AC_DARK);
			actions.add(AC_EARTH);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {
		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else if( action.equals( AC_LIGHT ) ) {
		    curUser = hero;
            Buff.affect(hero, HighLight.class, 40f);
		    Buff.affect(hero, AttackUp.class, 40f).level(80);
		    Buff.affect(hero, DefenceUp.class, 40f).level(80);
		    Buff.affect(hero, Silent.class, 40f);
		    Buff.affect(hero, Locked.class, 40f);
			hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			Sample.INSTANCE.play(Assets.SND_BURNING);
			hero.spendAndNext(1f);
			updateQuickslot();
			charge-=20;

	  } else if( action.equals( AC_DARK ) ) {
		    curUser = hero;
		  	Buff.affect(hero, Recharging.class, 40f);
			Buff.affect(hero, Arcane.class, 40f);
		    Buff.affect(hero, Weakness.class, 40f);
			Buff.affect(hero, Disarm.class, 40f);

			  hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			  Sample.INSTANCE.play(Assets.SND_BURNING);
			  hero.spendAndNext(1f);
			  updateQuickslot();
			  charge-=20;

	  } else  if (action.equals( AC_EARTH )){
		    curUser = hero;

			curUser.HP += curUser.HT/5;
			Buff.affect(hero, ShieldArmor.class).level(hero.HT/5);
			Buff.affect(hero, BerryRegeneration.class).level(hero.HT / 5);
			Buff.affect(hero, Rhythm.class, 40f);

			 
			  hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			  Sample.INSTANCE.play(Assets.SND_BURNING);
			  hero.spendAndNext(1f);
			  updateQuickslot();
			  charge-=20;

		} else super.execute(hero, action);


	}

	@Override
	public String status() {
		return Messages.format("%d", (int)charge/25);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(this, "charge",charge,fullCharge);
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
	

}
