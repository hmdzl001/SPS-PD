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
package com.hmdzl.spspd.change.items.misc;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.bags.Bag;
import com.hmdzl.spspd.change.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.MissileSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class MissileShield extends Item {

	{
		//name = "MissileShield";
		image = ItemSpriteSheet.WARRIORSHIELD;
	
		stackable = false;
		unique = true;
		defaultAction = AC_CAST;
	}

	private static final String AC_CAST = "CAST";
    private static final String AC_SHIELD = "SHIELD";
	public final int fullCharge = 10;
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
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		if (charge > 9){
		actions.add(AC_CAST);
		}
		if (charge > 5){
		actions.add(AC_SHIELD);
		}
		actions.remove( AC_THROW );
		actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_CAST)) {
			curUser = hero;
			if (charge < 10) {
				GLog.i(Messages.get(this, "rest"));
			} else GameScene.selectCell(Cast);
		} else if (action.equals(AC_SHIELD)) {
			curUser = hero;
			if (charge < 5) {
				GLog.i(Messages.get(this, "rest"));
				return;
			} else {
			Buff.prolong(hero, DefenceUp.class, 3f).level(50);
			Buff.affect(hero, ShieldArmor.class).level(hero.lvl);
			charge -= 5;
			}
		}

	}

	@Override
	public String desc() {
		String info = super.desc();
		info += "\n\n" + Messages.get(MissileShield.class, "damage",min(),max());
		info += "\n\n" + Messages.get(MissileShield.class, "charge",charge,fullCharge);
		return info;
	}

	public int level() {
		return Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5;
	}

	@Override
	public int visiblyUpgraded() {
		return level();
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public int min() {
		return 1 + Dungeon.hero.lvl/5;
	}

	public int max() {
		return 1 + Dungeon.hero.lvl/2;
	}

	private CellSelector.Listener Cast = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){

				if (Actor.findChar( target ) != null ){
					Char mob = Actor.findChar(target);
					if (mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS)){
						charge -= 10;
						mob.damage(Random.IntRange(min(),max())*2,this);
						//Sample.INSTANCE.play(Assets.SND_BURNING);
						CellEmitter.get(mob.pos).start(Speck.factory(Speck.ROCK), 0.07f, 5);
						hero.spendAndNext(1f);
						updateQuickslot();
					} else if (mob instanceof NPC || mob == hero) {
						GLog.w(Messages.get(MissileShield.class,"not"));
						return;
					} else {
						charge -= 10;
						mob.damage(Random.IntRange(min(),max()),this);
						Buff.prolong(mob, Paralysis.class, 2f);
						CellEmitter.get(mob.pos).start(Speck.factory(Speck.ROCK), 0.07f, 5);
						hero.spendAndNext(1f);
						updateQuickslot();
					}
				} else {
					GLog.i( Messages.get(MissileShield.class, "not_mob") );
					return;
				}

			} else {
				GLog.i( Messages.get(MissileShield.class, "not_mob") );
				return;
			}

		}

		@Override
		public String prompt() {
			return Messages.get(MissileShield.class, "prompt");
		}
	};
}

