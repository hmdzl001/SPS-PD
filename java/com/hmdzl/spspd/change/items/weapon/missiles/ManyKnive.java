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
package com.hmdzl.spspd.change.items.weapon.missiles;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.MechArmor;
import com.hmdzl.spspd.change.actors.buffs.TargetShoot;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Splash;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.misc.MissileShield;
import com.hmdzl.spspd.change.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.change.items.wands.WandOfFlow;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.change.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.change.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.hmdzl.spspd.change.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.KindOfWeapon;
import com.hmdzl.spspd.change.messages.Messages;

import java.util.ArrayList;

public class ManyKnive extends Weapon {

	private SpAmmo spammo;
	public static final String AC_SHOOT	 = "SHOOT";
    public static final String AC_AMMO	= "AMMO";

	{
		//name = "ManyKnive";
		image = ItemSpriteSheet.MANY_KNIVE;

		STR = 10;

		MIN = 2;
		MAX = 4;
		
		stackable = false;
		unique = true;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		reinforced = true;
	}

	
	public ManyKnive() {
		spammo = null;
	}
	
	public ManyKnive(SpAmmo spammo) {
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
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_EQUIP);
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
		
		MIN += 1;
		MAX += 2;
		super.upgrade(enchant);

		updateQuickslot();

		return this;
	}	
	
 	@Override
	public Item degrade() {
		return super.degrade();
	}
	
	public int damageRoll(Hero owner) {
		int damage = Random.Int(MIN, MAX);

		float bonus = 0;
		for (Buff buff : owner.buffs(RingOfSharpshooting.Aim.class)) {
				bonus += ((RingOfSharpshooting.Aim) buff).level;
		}
		if (Dungeon.hero.buff(TargetShoot.class)!= null)
			bonus += 10;
		if (Dungeon.hero.buff(MechArmor.class)!= null)
			bonus += 10;
		damage = (int)(damage*(1 + 0.05*bonus));
		return Math.round(damage);
	}	
	
	
	@Override
	public String info() {
		String info = desc();

		if (spammo != null){
			info += "\n" + Messages.get(GunWeapon.class, "ammo_add") + Messages.get(spammo,"name") ;
		}

        if (reinforced) {
            info += "\n" + Messages.get(Item.class, "reinforced");
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
	
	public KniveAmmo Ammo(){
		return new KniveAmmo();
	}
	
	public class KniveAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.KNIVE;
			enchantment = ManyKnive.this.enchantment;
			DLY = 0.25f;
		}

		public int damageRoll(Hero owner) {
			return ManyKnive.this.damageRoll(owner);
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
				spammo.onHit(ManyKnive.this, attacker, defender, damage);
			}
			if (enchantment != null) {
				enchantment.proc(ManyKnive.this, attacker, defender, damage);
			}
			if (Random.Int(50)== 0){
				Dungeon.level.drop(new EscapeKnive(1), defender.pos).sprite.drop();
			}
			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			ManyKnive.this.targetPos = cell;
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