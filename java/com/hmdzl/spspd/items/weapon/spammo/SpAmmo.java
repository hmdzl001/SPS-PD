
package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.guns.GunA;
import com.hmdzl.spspd.items.weapon.guns.GunB;
import com.hmdzl.spspd.items.weapon.guns.GunC;
import com.hmdzl.spspd.items.weapon.guns.GunD;
import com.hmdzl.spspd.items.weapon.guns.GunE;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.guns.Sling;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public abstract class SpAmmo extends Item {

	{
		image = ItemSpriteSheet.SPAMMO;
		 
		stackable=false;
	}

	public abstract void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage);

	public abstract void onHit(Boomerang boomerang, Char attacker, Char defender, int damage);
	
	public abstract void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage);

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int price() {
		return 100 * quantity;
	}	
	
}	