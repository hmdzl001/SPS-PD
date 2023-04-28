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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.rockcode.RockCode;
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

public class MegaCannon extends Weapon {
	
    public Buff passiveBuff;

	private RockCode rockCode;

	protected WndBag.Mode mode = WndBag.Mode.ROCK_CODE;

	public static final String AC_SHOOT	 = "SHOOT";
	public static final String AC_ADD	 = "ADD";
	
    public static int charge = 0;

	{
		STR = 10;

		MIN = 1;
		MAX = 5;

		DLY = 0.75f;

		stackable = false;
		unique = true;
	}

	{
		image = ItemSpriteSheet.MEGA_CANNON;
		
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		reinforced = true;
		cursed = true;		
		
	}

	public MegaCannon() {
		super();
		rockCode = null;
	}
	
	
	private static final String CHARGE = "charge";
    private static final String ROCK_CODE =   "rockCode";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
		if (rockCode != null) bundle.put( ROCK_CODE, rockCode);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
		if (bundle.contains(ROCK_CODE)) rockCode = (RockCode) bundle.get( ROCK_CODE );
	}			

	public MegaCannon(RockCode code) {
		this.rockCode = code;
	}
		
	
	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}	

	protected MegaManBuff passiveBuff() {
		return new MegaManCharge();
	}
	
	public class MegaManBuff extends Buff {
		public int level() {
			return level;
		}
	}	
	
	@Override
	public boolean doEquip(Hero hero) {
		activate(hero);
		return super.doEquip(hero);
	}
	
	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {
			if (passiveBuff != null){
				passiveBuff.detach();
				passiveBuff = null;
			}
			hero.belongings.weapon = null;
			return true;
		} else {
			return false;
		}
	}	
	
	@Override
	public void activate(Hero hero) {
		passiveBuff = passiveBuff();
		passiveBuff.attachTo(hero);
	}	
	
	public class MegaManCharge extends MegaManBuff {
		@Override
		public boolean act() {
			if (charge < 3){
			   charge++;
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}
		@Override
		public String toString() {
			return "LinkCharge";
		}
		@Override
		public void detach() {
			charge = 0;
			super.detach();
		}
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
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);

		actions.add(AC_SHOOT);
        actions.add(AC_ADD);	
 
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SHOOT)) {
		    GameScene.selectCell( shooter );
		} 
	    if (action.equals(AC_ADD)) {
			curUser = hero;
	        GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
		} 
	}

	
	@Override
	public Item upgrade(boolean enchant) {
		
		MIN += 1;
		MAX += 3;
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
		damage = damage/2 * charge;
		return Math.round(damage);
	}	
	
	@Override
    public void proc(Char attacker, Char defender, int damage) {
		if (rockCode != null) {
			rockCode.onHit(MegaCannon.this, attacker, defender, damage);
		}

		charge = 0;
		updateQuickslot();

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
    }
	
	public int damageRoll2(Hero owner) {
		int damage = Random.Int(MIN, MAX);

		if (hero.buff(TargetShoot.class)!= null)
	        damage = (int)(damage*1.5f);
		if (hero.buff(MechArmor.class)!= null)
			damage = (int)(damage*1.5f);
		
		float bonus = 0;
		for (Buff buff : owner.buffs(RingOfSharpshooting.Aim.class)) {
			bonus += Math.min(((RingOfSharpshooting.Aim) buff).level,30);
		}	
		
		if (Random.Int(10) < 3 &&  bonus > 0) {
			damage = (int)(damage * ( 1.5 + 0.25 * bonus));
			hero.sprite.emitter().burst(Speck.factory(Speck.STAR),8);
		}
		return Math.round(damage);
	}

	@Override
	public String info() {
		String info = desc();

		if (rockCode != null){
			info += "\n" + Messages.get(GunWeapon.class, "ammo_add") + Messages.get(rockCode,"name") ;
		}

        if (reinforced) {
            info += "\n" + Messages.get(Item.class, "reinforced");
        }
		info += "\n\n" + Messages.get(this, "damage",MIN,MAX);

		return info;
	}

	@Override
	public String status() {
		return Messages.format("%d", charge );
	}

	private int targetPos;
	
	public MegamanAmmo Ammo(){
		return new MegamanAmmo();
	}
	
	public class MegamanAmmo extends MissileWeapon {
		
		{
			image = charge > 2 ? ItemSpriteSheet.ATTACKSHIELD : charge > 1 ? ItemSpriteSheet.VIOLETDEWDROP : ItemSpriteSheet.DEWDROP;
			ACU = 100f;
			//enchantment = MegaCannon.this.enchantment;
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
				charge = 0;
				updateQuickslot();
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
					charge = 0;
					updateQuickslot();
				}
			}
		}
		
		@Override
		public void proc(Char attacker, Char defender, int damage) {
			int dmg = MegaCannon.this.damageRoll2((Hero) attacker) * charge - defender.drRoll();
			defender.damage(dmg, MegaCannon.class);
			charge = 0;
			updateQuickslot();

			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			MegaCannon.this.targetPos = cell;
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