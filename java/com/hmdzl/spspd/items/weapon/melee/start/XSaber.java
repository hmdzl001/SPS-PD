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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.rockcode.RockCode;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class XSaber extends MeleeWeapon {
	{
		
		image = ItemSpriteSheet.RUNICBLADE;
		usesTargeting = true;
	}

	protected WndBag.Mode mode = WndBag.Mode.ROCK_CODE;

	private RockCode rockCode;

	public XSaber() {
		super(1, 1.2f, 0.5f, 1);
		MIN = 6;
		MAX = 10;
		unique = true;
		reinforced = true;
		cursed = true;
		rockCode = null;
	}
	
	public XSaber(RockCode rockcode) {
		super(1, 1.2f, 0.5f, 1);
		rockCode = rockcode;
	}

	public static final String AC_ADD	 = "ADD";
	
	private static final String ROCK_CODE =   "rockCode";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		if (rockCode != null) bundle.put( ROCK_CODE, rockCode);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(ROCK_CODE)) rockCode = (RockCode) bundle.get( ROCK_CODE );
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);

		actions.add(AC_ADD);

		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_ADD)) {
			curUser = hero;
			GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
		}
	}


	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}		
	
	@Override
	public Item upgrade(boolean enchant) {
		MIN += 2;
		MAX += 3;
		return super.upgrade(enchant);
	}

	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
        if (rockCode != null) {
			rockCode.onHit(XSaber.this, attacker, defender, damage);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	
	}

	@Override
	public String info() {
		String info = desc();
		if (rockCode != null){
			info += "\n" + Messages.get(GunWeapon.class, "ammo_add") + Messages.get(rockCode,"name") ;
		}
		return info;
	}

	public Item addRockCode(RockCode code, Char owner){
		this.rockCode = null;
		this.rockCode = code;
		code.identify();
		code.cursed = false;
		updateQuickslot();
		return this;
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( final Item item ) {
			if (item != null) {

				GameScene.show(
						new WndOptions("",
								Messages.get(GunWeapon.class, "warning"),
								Messages.get(GunWeapon.class, "yes"),
								Messages.get(GunWeapon.class, "no")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) {
									Sample.INSTANCE.play(Assets.SND_EVOKE);
									//item.detach(curUser.belongings.backpack);
									addRockCode((RockCode) item, curUser);

									curUser.spendAndNext(2f);

									updateQuickslot();
								}
							}
						}
				);
			}
		}
	};

}
