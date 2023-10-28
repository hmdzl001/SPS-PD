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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HTimprove;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.GreatRune;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.medicine.Greaterpill;
import com.hmdzl.spspd.items.misc.GunOfSoldier;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class HolyMace extends MeleeWeapon {
	//public Buff passiveBuff;
	private int targetPos;
	{
		//name = "HolyMace";
		image = ItemSpriteSheet.HOLY_MACE;
		defaultAction = AC_CHOOSE;
		usesTargeting = true;
	}

	public HolyMace() {
		super(3, 1.2f, 1f, 2);
		MIN = 8;
		MAX = 20;
		unique = true;
		reinforced = true;
		cursed = true;
	}

	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}		
	
	@Override
	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 2;
		return super.upgrade(enchant);
	}

	//public final int fullCharge = 10;
	public int charge = 0;
	public int uptime1 = 1;
	public static int uptime2 = 1;
	public int uptime3 = 1;
	
	private static final String CHARGE = "charge";
	private static final String UPTIME1 = "uptime1";
	private static final String UPTIME2 = "uptime2";
	private static final String UPTIME3 = "uptime3";

	public static final String AC_CHOOSE = "CHOOSE";
	public static final String AC_ADD = "ADD";
	public static final String AC_LIGHT = "LIGHT";
	public static final String AC_TRIAL = "TRIAL";
	public static final String AC_HEAL = "HEAL";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
	
	    actions.add(AC_ADD);
		if (charge >4)actions.add(AC_LIGHT);
		if (charge > 9) {
			actions.add(AC_TRIAL);
			actions.add(AC_HEAL);
		}
	
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals( AC_CHOOSE )){
			GameScene.show(new WndItem(null, this, true));
		} else if (action.equals(AC_ADD)) {
			
			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.HOLY_MACE ,Messages.get(this, "prompt2"));
			
		} else if (action.equals(AC_LIGHT)) {
			curUser = hero;
			Buff.affect(hero,Light.class,10f+uptime1);
			curUser.spendAndNext(1f);
			charge-=5;
			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
				if (Level.fieldOfView[mob.pos]) {
					Buff.affect(mob, Terror.class, 10f+uptime1).object = curUser
							.id();
				}
			}
		} else if (action.equals(AC_TRIAL)) {
			curUser = hero;
			curItem = this;
			GameScene.selectCell( shooter );
		} else if (action.equals(AC_HEAL)) {
			curUser = hero;
			Buff.prolong(hero, HTimprove.class,100f);
			hero.updateHT(true);
			Buff.affect(hero, BerryRegeneration.class).level(10+uptime3);
			Buff.affect(hero, ShieldArmor.class).level(10+uptime3);
			curUser.sprite.operate(curUser.pos);
			curUser.spendAndNext(1f);
            charge = 0;			
		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
		bundle.put(UPTIME1,uptime1);
		bundle.put(UPTIME2,uptime2);
		bundle.put(UPTIME3,uptime3);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
		uptime1 = bundle.getInt(UPTIME1);
		uptime2 = bundle.getInt(UPTIME2);
		uptime3 = bundle.getInt(UPTIME3);
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {

		//int DMG = damage;
		if (charge < 10)
        charge++;

		if (defender.properties().contains(Char.Property.DEMONIC) || defender.properties().contains(Char.Property.UNKNOW)
		|| defender.properties().contains(Char.Property.UNDEAD) || defender.properties().contains(Char.Property.DRAGON))
			defender.damage(damage/2,this);
	
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	
	}

	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(this, "level",uptime1,uptime2,uptime3);
		info += "\n\n" + Messages.get(this, "charge",charge,10);
		return info;
	}


	public HolyHammer Ammo(){
		return new HolyHammer();
	}

	public class HolyHammer extends MissileWeapon {

		{
			image = ItemSpriteSheet.WAR_HAMMER;
			ACU = 1000;
		}

		public int damageRoll(Hero owner) {
			return 0;
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				Splash.at( cell, 0xCC99FFFF, 1 );
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
			}
		}

		@Override
		public void proc(Char attacker, Char defender, int damage) {

			defender.damage(Math.max(defender.HT / 20+ uptime2,1), this);
			if (defender.HP < defender.HT/3 && !(defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS))){
				defender.sprite.killAndErase();
				defender.die(null);
			}
			super.proc(attacker, defender, damage);
		}

		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			HolyMace.this.targetPos = cell;
			charge-=10;
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
			return Messages.get(GunOfSoldier.class, "prompt");
		}
	};

		private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( final Item item ) {
			if (item != null) {
				if (item instanceof Torch) {
					uptime1++;
				}else if(item instanceof GreatRune){
					uptime2++;
				}	else if(item instanceof Greaterpill){
					uptime3++;
				}

					Sample.INSTANCE.play(Assets.SND_EVOKE);
					item.detach(curUser.belongings.backpack);
					curUser.spendAndNext(2f);
					updateQuickslot();

			}
		}
	};	
}
