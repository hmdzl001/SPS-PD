package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;

public class EmptyAmmo extends SpAmmo {

	//private static final Glowing BLACK = new Glowing(0x00000);

	//@Override
	//public Glowing glowing() {
		//return BLACK;
	//}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		if(defender.properties().contains(Char.Property.BOSS)){
			defender.damage(Math.min(defender.HT/20,3000),this,3);
		}
	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if(defender.properties().contains(Char.Property.BOSS)){
			defender.damage(Math.min(defender.HT/20,3000),this,3);
		}
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if(defender.properties().contains(Char.Property.BOSS)){
			defender.damage(Math.min(defender.HT/20,3000),this,3);
		}
	}
}