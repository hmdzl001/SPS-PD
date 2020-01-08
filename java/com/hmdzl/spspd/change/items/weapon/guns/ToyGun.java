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
package com.hmdzl.spspd.change.items.weapon.guns;

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Splash;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.wands.WandOfFlow;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.change.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.KindOfWeapon;
import com.hmdzl.spspd.change.messages.Messages;

import java.util.ArrayList;

public class ToyGun extends Weapon {

	/*{
		//name = "ToyGun";

	}

	public ToyGun() {
		super(1);
	}*/
	
	public static final String AC_SHOOT		= "SHOOT";
	public static final String AC_RELOAD		= "RELOAD";

	private int tier = 1;
	
    public int charge = 0;
	public int maxammo = 100;
	public int fullcharge = 10;
	
	public ToyGun() {
		super();

		this.tier = tier;

		//AMMO = ammo

		STR = typicalSTR();

		MIN = min();
		MAX = max();

		reinforced = true;
	}

	
	private int min() {
		return 1;
	}

	private int max() {
		return 10;
	}

	public int typicalSTR() {
		return 10;
	}

	public Item upgrade() {
		MIN += 1;
		MAX += 3;
		maxammo += 10;

		return super.upgrade();
	}
	
	@Override
	public int price() {
		int price = 100 ;
		if (enchantment != null) {
			price *= 1.5;
		}
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level > 0) {
				price *= (level + 1);
			} else if (level < 0) {
				price /= (1 - level);
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}	
	
	{
		image = ItemSpriteSheet.TOYGUN;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		
		 
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		//actions.remove(AC_EQUIP);
	if (isEquipped(hero)){
		actions.add(AC_SHOOT);
		actions.add(AC_RELOAD);
	}
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SHOOT)) {
			if (!isEquipped( hero ))             
				GLog.i( Messages.get(ToyGun.class, "need_to_equip") );
			else if (charge<1){
				if ( maxammo < 1 ){
					GLog.n(Messages.get(ToyGun.class, "empty"));
				} else {
					if (durable() && maxammo > 0){
						maxammo =  Math.max( maxammo - ( fullcharge - charge),0);
					}
					charge = Math.min(fullcharge,maxammo);
					hero.sprite.showStatus(CharSprite.DEFAULT,  Messages.get(this, "reloading"));
					hero.spendAndNext(3f);
				}
			} else GameScene.selectCell( shooter );
		} else if(action.equals(AC_RELOAD)){
			if (!isEquipped( hero ))             
				GLog.i( Messages.get(ToyGun.class, "need_to_equip") );
			else if (charge == fullcharge){
				GLog.n(Messages.get(ToyGun.class,"full"));
			} else if ( maxammo < 1 ){
				GLog.n(Messages.get(ToyGun.class, "empty"));
			} else {
				if (durable() && maxammo > 0){
					maxammo =  Math.max( maxammo - ( fullcharge - charge),0);
				}
				float reloadtime = (fullcharge - charge)/2;
				hero.spendAndNext(reloadtime*1f);
				hero.sprite.showStatus(CharSprite.DEFAULT,  Messages.get(this, "reloading"));
				charge = Math.min(fullcharge,maxammo);

			}
		}
	}

	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( CHARGE, charge );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt( CHARGE );
	}

	@Override
	public int damageRoll(Hero owner)	{
		return 0;
	}

	public int damageRoll2(Hero owner)	{
		return Random.Int(MAX,MIN);
	}
	
   @Override
    public void proc(Char attacker, Char defender, int damage) {

		int oppositeDefender = defender.pos + (defender.pos - attacker.pos);
		Ballistica trajectory = new Ballistica(defender.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
		WandOfFlow.throwChar(defender, trajectory, 1);
    }	
	
	@Override
	public String info() {
		String info = desc();


		info += "\n\n" + Messages.get(ToyGun.class, "stats_known", tier, MIN, MAX, STR);

        String stats_desc = Messages.get(this, "stats_desc");
        if (!stats_desc.equals("")) info+= "\n\n" + stats_desc;
        //Messages.get(MeleeWeapon.class, "stats_known", tier, MIN, MAX,STR,ACU,DLY,RCH )

        if (reinforced) {
            info += "\n" + Messages.get(Item.class, "reinforced");
        }

        info += "\n " + Messages.get(ToyGun.class, "charge", charge, fullcharge);

		return info;
	}
	
	@Override
	public String status() {
		if (levelKnown) {
			return charge + "/" + fullcharge;
		} else {
			return null;
		}
	}	

	private int targetPos;

	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	public ToyAmmo Ammo(){
		return new ToyAmmo();
	}
	
	public class ToyAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.AMMO;
		}

		public int damageRoll(Hero owner) {
			return ToyGun.this.damageRoll2(owner);
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
		
		int flurryCount = -1;
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			ToyGun.this.targetPos = cell;
			charge--;
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
			return Messages.get(ToyGun.class, "prompt");
		}
	};
}