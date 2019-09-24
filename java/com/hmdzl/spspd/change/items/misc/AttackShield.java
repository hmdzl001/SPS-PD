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
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Item;
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

public class AttackShield extends Item {

	{
		//name = "AttackShield";
		image = ItemSpriteSheet.ATTACKSHIELD;
	
		stackable = false;
		unique = true;
		defaultAction = AC_CAST;
		 
	}

	private static final String AC_CAST = "CAST";
    private static final String AC_BlAST = "BLAST";
	public final int fullCharge = 20;
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
		if (charge >= 10){
		actions.add(AC_CAST);
		}		
		if (charge >= 20){
		actions.add(AC_BlAST);
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
		} else if (action.equals(AC_BlAST)) {
			curUser = hero;
			if (charge < 20) {
				GLog.i(Messages.get(this, "rest"));
				return;
			} else {
				if (curUser.buff(LongBuff.class) == null)  Buff.affect(curUser,LongBuff.class);
			Buff.prolong(hero, ArmorBreak.class, 100f).level(50);
			Buff.prolong(hero, AttackUp.class, 50f).level(100);
			charge -= 20;
			}
		}

	}

	@Override
	public String desc() {
		String info = super.desc();
		info += "\n\n" + Messages.get(AttackShield.class, "damage",min(),max());
		info += "\n\n" + Messages.get(AttackShield.class, "charge",charge,fullCharge);
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
		return 1 + Dungeon.hero.lvl;
	}

	public int max() {
		return 3 + Dungeon.hero.lvl*2;
	}

	private CellSelector.Listener Cast = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){

				if (Actor.findChar( target ) != null ){
					Char mob = Actor.findChar(target);
					if (mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS)){
						charge -= 20;
						mob.damage((int)(0.25* mob.HP * (mob.HP/mob.HT)) + Random.IntRange(min()/2,max()/2),this);
						//Sample.INSTANCE.play(Assets.SND_BURNING);
						CellEmitter.get(mob.pos).start(Speck.factory(Speck.ROCK), 0.07f, 5);
						hero.spendAndNext(1f);
						updateQuickslot();
					} else if (mob instanceof NPC || mob == hero) {
						GLog.w(Messages.get(AttackShield.class,"not"));
						return;
					} else {
						charge -= 20;
						mob.damage((int)(0.5* mob.HP * (mob.HP/mob.HT)) + Random.IntRange(min(),max()),this);
						Buff.prolong(mob, Vertigo.class, 5f);
						CellEmitter.get(mob.pos).start(Speck.factory(Speck.ROCK), 0.07f, 5);
						hero.spendAndNext(1f);
						updateQuickslot();
					}
				} else {
					GLog.i( Messages.get(AttackShield.class, "not_mob") );
					return;
				}

			} else {
				GLog.i( Messages.get(AttackShield.class, "not_mob") );
				return;
			}

		}

		@Override
		public String prompt() {
			return Messages.get(AttackShield.class, "prompt");
		}
	};

	public static class LongBuff extends Buff {

		@Override
		public boolean act() {
			spend(TICK);
			return true;
		}

		public void dispel(){
			detach();
		}
	}

}

