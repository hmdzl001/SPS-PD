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
package com.hmdzl.spspd.items.weapon.missiles;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.GreenDewdrop;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class Boomerang extends Weapon {

	private SpAmmo spammo;
	
	public static final String AC_SHOOT	 = "SHOOT";
    public static final String AC_AMMO	= "AMMO";

	{
		//name = "boomerang";
		image = ItemSpriteSheet.BOOMERANG;

		STR = 10;

		MIN = 5;
		MAX = 10;
		
		stackable = false;
		unique = true;
		reinforced = true;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;


	}

	public Boomerang() {
		spammo = null;
	}
	
	public Boomerang(SpAmmo spammo) {
		this.spammo = spammo;
	}



	private static final String SPAMMO =  "spammo";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		if (spammo != null) bundle.put( SPAMMO, spammo );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(SPAMMO)) spammo = (SpAmmo) bundle.get( SPAMMO );
	}


	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		//actions.add(AC_AMMO);
		actions.remove(AC_EQUIP);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		actions.add(AC_SHOOT);
        actions.add(AC_AMMO);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		
		if (action.equals(AC_SHOOT)) {
		GameScene.selectCell( shooter );
		}	
		
		if (action.equals(AC_AMMO)) {
			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.AMMO ,Messages.get(this, "prompt"));
		}
	}

	@Override
	public Item upgrade(boolean enchant) {
		
		MIN += 2;
		MAX += 4;
		super.upgrade(enchant);

		updateQuickslot();

		return this;
	}
	
	public int damageRoll(Hero owner) {
		int damage = Random.Int(MIN, MAX);

		if (hero.buff(TargetShoot.class)!= null)
	        damage = (int)(damage*1.5f);
		if (hero.buff(MechArmor.class)!= null)
			damage = (int)(damage*1.5f);
		
		float bonus = 0;
		for (Buff buff : owner.buffs(RingOfSharpshooting.RingShoot.class)) {
			bonus += Math.min(((RingOfSharpshooting.RingShoot) buff).level,30);
		}	
		
		if (Random.Int(10) < 3  &&  bonus > 0 ) {
			damage = (int)(damage * ( 1.5 + 0.25 * bonus));
			hero.sprite.emitter().burst(Speck.factory(Speck.STAR),8);
		}
		return Math.round(damage);
	}	
	

	@Override
	public Item degrade() {
		return super.degrade();
	}

	//@Override
	//public void proc(Char attacker, Char defender, int damage) {
	//	if (spammo != null) {
	//		spammo.onHit(Boomerang.this, attacker, defender, damage);
	//	}
	//	super.proc(attacker, defender, damage);
	//	if (attacker instanceof Hero && ((Hero) attacker).rangedWeapon == this) {
	//		circleBack(defender.pos, (Hero) attacker);
	//	}
	//}

	//@Override
	//protected void miss(int cell) {
	//	circleBack(cell, curUser);
	//}

	//private void circleBack(int from, Hero owner) {

	//	((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class))
	//			.reset(from, curUser.pos, curItem, null);
				
	//	if (!collect(curUser.belongings.backpack)) {
	//		Dungeon.depth.drop(this, owner.pos).sprite.drop();
	//	}
	//}

	//@Override
	//public void cast(Hero user, int dst) {
	//	super.cast(user, dst);
	//}

	@Override
	public String desc() {
		String info = super.desc();

		if (spammo != null){
			info += "\n" + Messages.get(GunWeapon.class, "ammo_add") + Messages.get(spammo,"name") ;
		}

		if(reinforced){
			info += "\n\n" + Messages.get(Item.class, "reinforced");
        }
		
		info += "\n\n" + Messages.get(this, "damage",MIN,MAX);

		
		return info;
	}
	
	private int targetPos;

	public Item addSpAmmo(SpAmmo spammo, Char owner){

		this.spammo = null;

		//GLog.p( Messages.get(this, "imbue", spammo.name()));

		this.spammo= spammo;
		spammo.identify();
		spammo.cursed = false;
		//name = Messages.get(spammo, "spammo_name");

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
									item.detach(curUser.belongings.backpack);

									addSpAmmo((SpAmmo) item, curUser);

									curUser.spendAndNext(2f);

									updateQuickslot();
								}
							}
						}
				);
			}
		}
	};
	
	public BoomerangAmmo Ammo(){
		return new BoomerangAmmo();
	}
	
	public class BoomerangAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.BOOMERANG;
			enchantment = Boomerang.this.enchantment;
			//ACU = 1.5f;
		}

		public int damageRoll(Hero owner) {
			return Boomerang.this.damageRoll(owner);
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
			if (spammo != null) {
				spammo.onHit(Boomerang.this, attacker, defender, damage);
			}
			if (enchantment != null) {
				enchantment.proc(Boomerang.this, attacker, defender, damage);
			}
			if (Random.Int(50)== 0){
				Dungeon.depth.drop(new GreenDewdrop(), defender.pos).sprite.drop();
			}
			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			Boomerang.this.targetPos = cell;
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
