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
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.WarGroove;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Item;
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

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class GunOfSoldier extends Item {

	public static final String AC_USE = "USE";

	private static final float TIME_TO_DIG = 1f;

	{
		image = ItemSpriteSheet.GUN_S;
		defaultAction = AC_USE;
		unique = true;
		 
	}
	
	public final int fullCharge = 225;
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
		if (charge >= 75){
		actions.add(AC_USE);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {		
      if( action.equals( AC_USE ) ){
		  curUser = hero;
	     if (charge < 75) {
			  GLog.i(Messages.get(GunOfSoldier.class, "break"));
		  } else GameScene.selectCell(Shoot);
		} else {
			super.execute(hero, action);
			
		}
	}

	@Override
	public String status() {
		return Messages.format("%d", (int)charge/75);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(GunOfSoldier.class, "charge",charge,fullCharge);
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
	
	private CellSelector.Listener Shoot = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){

				if (Actor.findChar( target ) != null ){
					Char mob = Actor.findChar(target);
					if (mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS)){
						charge -= 75;
						mob.damage(Math.min(mob.HT - mob.HP,mob.HT/6),this);
						//Sample.INSTANCE.play(Assets.SND_BURNING);
						mob.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
						hero.spendAndNext(1f);
						updateQuickslot();
					} else if (mob instanceof NPC || mob == hero) {
						GLog.w(Messages.get(GunOfSoldier.class,"not"));
						return;
					} else {
						charge -= 75;
						mob.damage(Math.min(mob.HT - mob.HP,mob.HT/3),this);
						//Sample.INSTANCE.play(Assets.SND_BURNING);
						mob.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
						hero.spendAndNext(1f);
						updateQuickslot();
					}
				} else {
					GLog.i( Messages.get(GunOfSoldier.class, "not_mob") );
					return;
				}

			} else {
				GLog.i( Messages.get(GunOfSoldier.class, "not_mob") );
				return;
			}

		}

		@Override
		public String prompt() {
			return Messages.get(GunOfSoldier.class, "prompt");
		}
	};	
	
}
