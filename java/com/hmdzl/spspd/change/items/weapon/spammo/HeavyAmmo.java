package com.hmdzl.spspd.change.items.weapon.spammo;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.guns.GunA;
import com.hmdzl.spspd.change.items.weapon.guns.GunB;
import com.hmdzl.spspd.change.items.weapon.guns.GunC;
import com.hmdzl.spspd.change.items.weapon.guns.GunD;
import com.hmdzl.spspd.change.items.weapon.guns.GunE;
import com.hmdzl.spspd.change.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.items.weapon.guns.Sling;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

public class HeavyAmmo extends SpAmmo {

	private static final Glowing BLACK = new Glowing(0x00000);

	@Override
	public Glowing glowing() {
		return BLACK;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.75*damage), attacker);
	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.75*damage), attacker);
	}	
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.75*damage), attacker);
	}
}