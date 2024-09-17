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
package com.hmdzl.spspd.items.weapon.melee.start;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.HTimprove;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.Muscle;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.SuperArcane;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.medicine.Greaterpill;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class BraveBook extends MeleeWeapon {
	
	private int targetPos;
	{
		//name = "HolyMace";
		image = ItemSpriteSheet.SKILL_BOOK;
		defaultAction = AC_CHOOSE;
		usesTargeting = true;
	}

	public BraveBook() {
		super(2, 1.2f, 0.5f, 1);
		MIN = 4;
		MAX = 14;
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
		MAX += 1;
		return super.upgrade(enchant);
	}

	//public final int fullCharge = 10;
	public int charge = 0;
	public int uptime1 = 1;
	public static int uptime2 = 1;
	
	private static final String CHARGE = "charge";
	private static final String UPTIME1 = "uptime1";
	private static final String UPTIME2 = "uptime2";

	public static final String AC_CHOOSE = "CHOOSE";
	public static final String AC_ADD = "ADD";
	public static final String AC_IMPROVE = "IMPROVE";
	public static final String AC_HEAL = "HEAL";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
	
	    actions.add(AC_ADD);
		if (charge >4)actions.add(AC_IMPROVE);
		if (charge > 9) {
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
			
		} else if (action.equals(AC_IMPROVE)) {
			curUser = hero;
			Buff.affect(hero, Muscle.class,10f+uptime1);
			Buff.affect(hero, SuperArcane.class,6f).level(5+ uptime1);
			curUser.spendAndNext(1f);
			charge-=5;
		} else if (action.equals(AC_HEAL)) {
			curUser = hero;
			Buff.prolong(hero, HTimprove.class,100f);
			hero.updateHT(true);
			Buff.affect(hero, BerryRegeneration.class).level(10+uptime2);
			Buff.affect(hero, ShieldArmor.class).level(10+uptime2);
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
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
		uptime1 = bundle.getInt(UPTIME1);
		uptime2 = bundle.getInt(UPTIME2);
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {

		//int DMG = damage;
		if (charge < 10)
        charge++;

		if(defender.buff(ShieldArmor.class) != null) {
			defender.damage(damage,attacker,1);
		} else if(defender.buff(MagicArmor.class) != null){
			defender.damage(damage,attacker,2);
		} else if (defender.buff(EnergyArmor.class) != null){
			defender.damage(damage,attacker,2);
		}
		if (defender.buff(Silent.class) != null) {
			defender.damage((int) (damage * 0.5),attacker,2);
		} else {
			Buff.affect(defender, Silent.class, 5f);
		}
	
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	
	}

	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(this, "level",uptime1,uptime2);
		info += "\n\n" + Messages.get(this, "charge",charge,10);
		return info;
	}

		private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( final Item item ) {
			if (item != null) {
				if (item instanceof PotionOfStrength) {
					uptime1++;
				}else if(item instanceof Greaterpill){
					uptime2++;
				}	else {
					GLog.n(Messages.get(HolyMace.class,"notthis"));
					return;
				}
					Sample.INSTANCE.play(Assets.SND_EVOKE);
					item.detach(curUser.belongings.backpack);
					curUser.spendAndNext(2f);
					updateQuickslot();

			}
		}
	};	
}
